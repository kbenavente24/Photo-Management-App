package App;
import java.io.File;

import Controller.PhotoController;
import Model.AlbumLibrary;
import Model.ExportImportUserInfo;
import Model.PhotoLibrary;
import View.MainWindow;

public class PhotoApp {

    public void start() {
        ExportImportUserInfo.load();
        PhotoLibrary photoModel = loadPhotoLibrary();
        AlbumLibrary albumModel = loadAlbumLibrary();
        MainWindow view = new MainWindow();
        PhotoController controller = new PhotoController(photoModel, albumModel, view);

        view.setController(controller);
        view.getMainMenuBar().setController(controller);
        view.getPhotoListPanel().setController(controller);
    }

    private PhotoLibrary loadPhotoLibrary() {
        //Creating a file object that points to the file "photos.ser"
        //Note: this does not create the file on disk, it just creates a Java File object to check for existence.
        File saveFile = new File("photos.ser");
        //Check if the file actually exists on disk
        if (saveFile.exists()) {
            PhotoLibrary loaded = PhotoLibrary.loadFromFile("photos.ser");
            if (loaded != null) {
                return loaded;
            }
            System.out.println("Failed to load photo library. Creating new one.");
        }
        return new PhotoLibrary();
    }

    private AlbumLibrary loadAlbumLibrary(){
        File saveFile = new File("albums.ser");
        if (saveFile.exists()) {
            AlbumLibrary loaded = AlbumLibrary.loadFromFile("albums.ser");
            if (loaded != null) {
                return loaded;
            }
            System.out.println("Failed to load photo library. Creating new one.");
        }
        return new AlbumLibrary();
    }
}
