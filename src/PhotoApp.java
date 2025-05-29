import java.io.File;

public class PhotoApp {

    public void start() {
        ExportImportUserInfo.load();
        PhotoLibrary model = loadPhotoLibrary();
        MainWindow view = new MainWindow();
        PhotoController controller = new PhotoController(model, view);

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
}
