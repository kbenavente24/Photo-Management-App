import javax.swing.SwingUtilities;
public class Main {
    public static void main(String[] args) {
        ExportImportUserInfo.load();
        SwingUtilities.invokeLater(() -> {
            PhotoLibrary model = new PhotoLibrary();
            MainWindow view = new MainWindow();
            PhotoController controller = new PhotoController(model, view);

            view.setController(controller);
            view.getMainMenuBar().setController(controller);
            view.getPhotoListPanel().setController(controller);
        });
    }
}