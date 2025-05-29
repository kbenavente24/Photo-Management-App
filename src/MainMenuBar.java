import javax.swing.*;

public class MainMenuBar extends JMenuBar {
    private final JFrame parentFrame;
    private PhotoController controller;
    public MainMenuBar(MainWindow mainWindow, JFrame parentFrame) {
        this.parentFrame = parentFrame;

        add(createHelpMenu());
        add(createFileMenu());
        add(Box.createHorizontalGlue());
        add(createSignInButton());
    }

    public void setController(PhotoController controller){
        this.controller = controller;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenuItem createAlbumItem = new JMenuItem("Create Album");
        //createAlbumItem.addActionListener(e -> controller.createAlbum());

        JMenuItem importPhotosItem = new JMenuItem("Import Photos");
        importPhotosItem.addActionListener(e -> controller.importPhotos(parentFrame));

        JMenuItem exportPhotosItem = new JMenuItem("Export Photos");

        fileMenu.add(createAlbumItem);
        fileMenu.add(importPhotosItem);
        fileMenu.add(exportPhotosItem);
        return fileMenu;
    }

    private JMenu createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());

        helpMenu.add(aboutItem);
        return helpMenu;
    }

    private JButton createSignInButton() {
        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(e -> showSignInDialog());
        return signInButton;
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

        JOptionPane.showMessageDialog(this, message, "About", JOptionPane.PLAIN_MESSAGE);
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

            JOptionPane.showMessageDialog(this,
                    "Signed in as:\nUsername: " + username + "\nEmail: " + email);

            AboutInfo.USER_NAME = username;
            AboutInfo.USER_EMAIL = email;
            ExportImportUserInfo.save();
        }
    }
}
