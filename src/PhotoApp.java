import java.io.File;

public class PhotoApp {

    public void start() {
        ExportImportUserInfo.load();
        PhotoLibrary model = loadPhotoLibrary();
        AlbumLibrary albumModel = loadAlbumLibrary();
        MainWindow view = new MainWindow();
        PhotoController controller = new PhotoController(model, albumModel, view);

        view.setController(controller);
        view.getMainMenuBar().setController(controller);
        view.getPhotoListPanel().setController(controller);
    }

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

    private AlbumLibrary loadAlbumLibrary(){
        File saveFile = new File("albums.ser");
        if (saveFile.exists()) {
            AlbumLibrary loaded = AlbumLibrary.loadFromFile("albums.ser");
            if (loaded != null) {
                return loaded;
            }
            System.out.println("Failed to load photo library. Creating new one.");
        }
        System.out.println("works");
        return new AlbumLibrary();
    }
}
