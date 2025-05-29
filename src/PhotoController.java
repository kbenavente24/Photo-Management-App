import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PhotoController implements Serializable{

    private final PhotoLibrary photoLibrary;
    private final MainWindow view;

    public PhotoController(PhotoLibrary photoLibrary, MainWindow view){
        this.photoLibrary = photoLibrary;
        this.view = view;
        view.getPhotoListPanel().generateImageList(photoLibrary.getAllPhotos());
    }

    public void addPhoto(File photoFile){
        Photo photo = new Photo(photoFile);
        photoLibrary.addPhotoToLibrary(photo);
        view.getPhotoListPanel().addPhotoToList(photo);


        photoLibrary.printAllPhotoPaths();
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
                        addPhoto(file);
                    }
                }
            }
        }
        this.saveLibraryToFile();
    }

    public boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                name.endsWith(".png") || name.endsWith(".gif");
    }

    public void saveLibraryToFile(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("photos.ser"))){
            out.writeObject(photoLibrary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
