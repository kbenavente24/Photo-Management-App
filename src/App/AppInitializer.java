package App;

import java.io.File;

import Controller.LibraryController;
import Model.AlbumLibrary;
import Model.UserInfoPersistence;
import Model.PhotoLibrary;
import View.MainWindow;

/**
 * Initializes the application by loading saved data (if available),
 * setting up the model, view, and controller components, and wiring
 * everything together using MVC architecture.
 * 
 * @author Kobe Benavente
 * @version 1.0
 */
public class AppInitializer {

    /**
     * Starts the application.
     * Loads user information and saved photo/album libraries,
     * initializes the main window, and connects it with the controller.
     */    
    public void start() {
        UserInfoPersistence.load();
        PhotoLibrary photoModel = loadPhotoLibrary();
        AlbumLibrary albumModel = loadAlbumLibrary();
        MainWindow view = new MainWindow();
        LibraryController controller = new LibraryController(photoModel, albumModel, view);

        view.setController(controller);
        view.getMainMenuBar().setController(controller);
        view.getPhotoListPanel().setController(controller);
    }

    /**
     * Attempts to load the photo library from disk.
     * 
     * @return A loaded {@link PhotoLibrary} if successful, or a new instance if loading fails.
     */    
    private PhotoLibrary loadPhotoLibrary() {
        File saveFile = new File("photos.ser");
        if (saveFile.exists()) {
            PhotoLibrary loaded = PhotoLibrary.loadFromFile("photos.ser");
            if (loaded != null) {
                return loaded;
            }
            System.out.println("Failed to load photo library. Creating new one.");
        }
        return new PhotoLibrary();
    }
    
    /**
     * Attempts to load the album library from disk.
     * 
     * @return A loaded {@link AlbumLibrary} if successful, or a new instance if loading fails.
     */
    private AlbumLibrary loadAlbumLibrary() {
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
