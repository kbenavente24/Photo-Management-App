package Controller;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import Model.Album;
import Model.AlbumLibrary;
import Model.ListItem;
import Model.Photo;
import Model.PhotoLibrary;
import View.MainWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls the logic and interaction between the photo and album models
 * and the main application view.
 * Manages adding, importing, saving, and displaying photo and album data.
 * 
 * @author Kobe Benavente
 * @version 1.0
 */
public class LibraryController implements Serializable{

    private final PhotoLibrary photoLibrary;
    private final AlbumLibrary albumLibrary;
    private final MainWindow view;

    public LibraryController(PhotoLibrary photoLibrary, AlbumLibrary albumLibrary,
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

    public void sortListModel(DefaultListModel<Object> model) {
        List<Object> items = new ArrayList<>();
    
        for (int i = 0; i < model.size(); i++) {
            items.add(model.get(i));
        }
    
        items.sort((o1, o2) -> {
            String name1;
            String name2;
    
            if (o1 instanceof ListItem) {
                name1 = ((ListItem) o1).getName();
            } else {
                name1 = o1.toString();
            }
    
            if (o2 instanceof ListItem) {
                name2 = ((ListItem) o2).getName();
            } else {
                name2 = o2.toString();
            }
    
            return name1.compareToIgnoreCase(name2);
        });
    
        model.clear();
        for (Object item : items) {
            model.addElement(item);
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
