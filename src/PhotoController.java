import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class PhotoController implements Serializable{

    private final PhotoLibrary photoLibrary;
    private final AlbumLibrary albumLibrary;
    private final MainWindow view;

    public PhotoController(PhotoLibrary photoLibrary, AlbumLibrary albumLibrary,
    MainWindow view){
        this.photoLibrary = photoLibrary;
        this.albumLibrary = albumLibrary;
        this.view = view;
        view.getPhotoListPanel().generateImageList(photoLibrary.getAllPhotos());
        view.getPhotoListPanel().generateAlbumList(albumLibrary.getAllAlbums());
    }

    public void addPhoto(String absoluteFilePath){
        Photo photo = new Photo(absoluteFilePath);
        photoLibrary.addPhotoToLibrary(photo);
        view.getPhotoListPanel().addPhotoToList(photo);
    }
    
    public void addAlbum(String albumName){
        Album album = new Album(albumName);
        albumLibrary.addAlbumToLibrary(album);
        view.getPhotoListPanel().addAlbumToList(album);
        saveAlbumLibraryToFile();
    }

    public List<Album> getAlbumCollection(){
        return this.albumLibrary.getAllAlbums();
    }

    public void addPhotoToAlbum(Photo photo, Album album){
        Photo photoFromLibrary = photoLibrary.getPhotoByPath(photo.getFilePath());
        album.addPhotoToAlbum(photoFromLibrary);
    }

    public void importPhotos(JFrame parent){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = chooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File folder = chooser.getSelectedFile();
            File[] files = folder.listFiles();

            if(files != null){
                for(File file : files){
                    if(file.isFile() && isImageFile(file)){
                        addPhoto(file.getAbsolutePath());
                    }
                }
            }
        }
        this.saveLibraryToFile();
    }

    public boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                name.endsWith(".png");
    }

    public void saveLibraryToFile(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("photos.ser"))){
            out.writeObject(photoLibrary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAlbumLibraryToFile(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("albums.ser"))){
            out.writeObject(albumLibrary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkAndDisplayFavorites(){
        
        view.getPhotoListPanel().clearImageList();
        for(Album album : albumLibrary.getAllAlbums()){
            if(album.getFavoriteStatus()){
                view.getPhotoListPanel().addAlbumToList(album);
            }
        }
        for(Photo photo : photoLibrary.getAllPhotos()){
            if(photo.getFavoriteStatus()){
                view.getPhotoListPanel().addPhotoToList(photo);
            }
        }
    }   

    public void checkAndDisplayFromAlbum(Album album){
        view.getPhotoListPanel().clearImageList();
        List<Photo> listOfPhotosFromAlbum = album.getListOfPhotosFromAlbum();
        for(Photo photo : listOfPhotosFromAlbum){
            view.getPhotoListPanel().addPhotoToList(photo);
        }
    }

    public void displayOnlyAlbums(){
        view.getPhotoListPanel().clearImageList();
        List<Album> albums = albumLibrary.getAllAlbums();
        for(Album album : albums){
            view.getPhotoListPanel().addAlbumToList(album);
        }
    }

    public void reloadAllImages(){
        view.getPhotoListPanel().clearImageList();
        view.getPhotoListPanel().generateImageList(photoLibrary.getAllPhotos());
        view.getPhotoListPanel().generateAlbumList(albumLibrary.getAllAlbums());
    }

    public void clearFavorite(){
        view.getPhotoListPanel().clearFavorite();
    }

}
