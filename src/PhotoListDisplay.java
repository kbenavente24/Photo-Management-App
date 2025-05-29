import java.io.File;
import java.util.List;

import javax.swing.*;

public class PhotoListDisplay extends JPanel{
    // Scroll bar panel which will carry the container of photos and albums
    private final JScrollPane scrollPane;

    private PhotoController controller;

    private DefaultListModel<File> listModel;

    private JList<File> photoList;

    private final JSplitPane splitPane;

    public PhotoListDisplay(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        listModel = new DefaultListModel<>();
        photoList = new JList<>(listModel);
        photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        photoList.setVisibleRowCount(10);

        scrollPane = new JScrollPane(photoList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your Photos"));

        JPanel previewPanel = new JPanel();

        JScrollPane previewScrollPane = new JScrollPane(previewPanel);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, previewScrollPane);

        photoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                File selectedFile = photoList.getSelectedValue();
                if (selectedFile != null) {
                    System.out.println("Selected: " + selectedFile.getAbsolutePath());
                    // You could call controller.displayPhotoDetails(selectedFile), etc.
                }
            }
        });
    }

    public void generateImageList(List<Photo> photos){
        for (Photo photo : photos){
            listModel.addElement(photo.getFile());
            System.out.println("found!");
        }
    }

    public void setController(PhotoController controller){
        this.controller = controller;
    }

    //Returns scroll bar panel
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JSplitPane getSplitPane(){
        return splitPane;
    }


}
