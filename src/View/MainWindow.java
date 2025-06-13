package View;
import javax.swing.*;

import Controller.LibraryController;

public class MainWindow extends JFrame {

    private final JFrame frame;
    private MediaLibraryPanel photoListPanel;
    private MenuBar mainMenuBar;
    private LibraryController controller;

    public MainWindow() {
        frame = new JFrame("Photo Manager");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        mainMenuBar = new MenuBar(this, frame);
        frame.setJMenuBar(mainMenuBar);

        initializeComponents();
        frame.setVisible(true);
    }

    public void setController(LibraryController controller){
        this.controller = controller;
    }

    public void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        photoListPanel = new MediaLibraryPanel(this);
        mainPanel.add(photoListPanel.getSplitPane());
        frame.add(mainPanel);
    }

    public MediaLibraryPanel getPhotoListPanel(){
        return this.photoListPanel;
    }

    public MenuBar getMainMenuBar(){
        return this.mainMenuBar;
    }
}


