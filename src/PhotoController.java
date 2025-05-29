import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.io.File;

public class PhotoController {

    private final PhotoLibrary photoLibrary;
    private final MainWindow view;

    public PhotoController(PhotoLibrary photoLibrary, MainWindow view){
        this.photoLibrary = photoLibrary;
        this.view = view;
    }

    public void addPhoto(File photoFile){
        Photo photo = new Photo(photoFile);
        photoLibrary.addPhotoToLibrary(photo);

        photoLibrary.printAllPhotoPaths();
    }

    public void updatePhotoList(){
        List<Photo> photos = photoLibrary.getAllPhotos();
        view.getPhotoListPanel().generateImageList(photos);
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
        updatePhotoList();
    }

    public boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                name.endsWith(".png") || name.endsWith(".gif");
    }

}
