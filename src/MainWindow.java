import javax.swing.*;

public class MainWindow extends JFrame {

    private final JFrame frame;
    private PhotoListDisplay photoListPanel;
    private MainMenuBar mainMenuBar;
    private PhotoController controller;

    public MainWindow() {
        frame = new JFrame("Photo Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        mainMenuBar = new MainMenuBar(this, frame);
        frame.setJMenuBar(mainMenuBar);

        initializeComponents();
        frame.setVisible(true);
    }

    public void setController(PhotoController controller){
        this.controller = controller;
    }

    public void initializeComponents() {
 
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        photoListPanel = new PhotoListDisplay();
        mainPanel.add(photoListPanel.getSplitPane());

        frame.add(mainPanel);
    }

    public PhotoListDisplay getPhotoListPanel(){
        return this.photoListPanel;
    }

    public MainMenuBar getMainMenuBar(){
        return this.mainMenuBar;
    }
}


