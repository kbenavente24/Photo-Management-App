import javax.swing.*;

public class MainMenuBar extends JMenuBar {
    private final JFrame parentFrame;
    private PhotoController controller;
    private MainWindow mainWindow;

    public MainMenuBar(MainWindow mainWindow, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.mainWindow = mainWindow;

        add(createHelpMenu());
        add(createFileMenu());
        add(createFilterMenu());
        add(createSortMenu());
        add(Box.createHorizontalGlue());
        add(createSignInButton());
    }

    public void setController(PhotoController controller){
        this.controller = controller;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem importPhotosItem = new JMenuItem("Import Photos");
        importPhotosItem.addActionListener(e -> controller.importPhotos(parentFrame));
        fileMenu.add(importPhotosItem);
        return fileMenu;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());

        helpMenu.add(aboutItem);
        return helpMenu;
    }
    private JMenu createSortMenu() {
        JMenu sortMenu = new JMenu("Sort By");
        JMenuItem sortItem = new JMenuItem("File Name (A-Z)");
        sortMenu.add(sortItem);
        return sortMenu;
    }

    private JButton createSignInButton() {
        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> showSignInDialog());
        return signInButton;
    }

    private JMenuItem createFilterMenu(){
        JMenu filterMenu = new JMenu("Filter By");
        JMenuItem allItem = new JMenuItem("All");
        allItem.addActionListener(e -> {
            controller.reloadAllImages();
            controller.clearFavorite();
        });

        filterMenu.add(allItem);

        JMenuItem favoritesItem = new JMenuItem("Favorites");
        favoritesItem.addActionListener(e -> {
            controller.checkAndDisplayFavorites();
            controller.clearFavorite();
        });
        filterMenu.add(favoritesItem);
        JMenuItem albumsItem = new JMenuItem("Albums");
        albumsItem.addActionListener(e -> {
            controller.displayOnlyAlbums();
            controller.clearFavorite();
        });
        filterMenu.add(albumsItem);

        return filterMenu;
    }



    private void showAboutDialog() {
        String message = String.format(
                "<html><h2>Photo Manager</h2>"
                        + "<p><strong>Version:</strong> %s</p>"
                        + "<p><strong>User:</strong> %s</p>"
                        + "<p><strong>Email:</strong> %s</p></html>",
                AboutInfo.VERSION,
                AboutInfo.USER_NAME,
                AboutInfo.USER_EMAIL
        );

        JOptionPane.showMessageDialog(this.mainWindow, message, "About", JOptionPane.PLAIN_MESSAGE);
    }

    public void showSignInDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField usernameField = new JTextField(15);
        JTextField emailField = new JTextField(15);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(
                parentFrame,
                panel,
                "Sign In",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String email = emailField.getText();

            JOptionPane.showMessageDialog(this.mainWindow,
                    "Signed in as:\nUsername: " + username + "\nEmail: " + email);

            AboutInfo.USER_NAME = username;
            AboutInfo.USER_EMAIL = email;
            ExportImportUserInfo.save();
        }
    }
}
