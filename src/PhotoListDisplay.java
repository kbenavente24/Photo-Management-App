import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.*;

public class PhotoListDisplay extends JPanel{
    // Scroll bar panel which will carry the container of photos and albums
    private final JScrollPane scrollPane;

    private PhotoController controller;

    private DefaultListModel<Photo> listModel;

    private JList<Photo> photoList;

    private final JSplitPane splitPane;

    private final JPanel previewPanel;
    
    private final JLabel previewLabel;

    private Photo currentlySelectedPhoto;
    
    private JButton favoriteButton;

    public PhotoListDisplay(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        listModel = new DefaultListModel<>();
        photoList = new JList<>(listModel);
        photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        photoList.setVisibleRowCount(10);

        scrollPane = new JScrollPane(photoList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Your Photos"));
        JPanel leftSidePhotoList = leftSide();
        leftSidePhotoList.add(scrollPane);

        previewPanel = new JPanel(new BorderLayout());
        previewLabel = new JLabel();
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewPanel.add(previewLabel, BorderLayout.CENTER);


        JScrollPane previewScrollPane = new JScrollPane(previewPanel);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSidePhotoList, previewScrollPane);
        splitPane.setDividerLocation(350);

        photoList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Photo selectedPhoto = photoList.getSelectedValue();
                if (selectedPhoto != null) {
                    // File selectedFile = selectedPhoto.getFile();
                    System.out.println("Selected: " + selectedPhoto.getFilePath());
                    System.out.println("Currently selected photo: ");
                    currentlySelectedPhoto = selectedPhoto;
                    changeFavoriteIcon();
                    System.out.println(currentlySelectedPhoto);
                    // You could call controller.displayPhotoDetails(selectedFile), etc.
                    ImageIcon icon = new ImageIcon(selectedPhoto.getFilePath());

                    Image scaledImage = icon.getImage().getScaledInstance(previewPanel.getWidth(), previewPanel.getHeight(), Image.SCALE_SMOOTH);
                    previewLabel.setIcon(new ImageIcon(scaledImage));
                }
            }
        });

        photoList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                if (value instanceof File file) {
                    value = file.getName(); // Only display the file name
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
    }

    public void changeFavoriteIcon(){
        if(currentlySelectedPhoto.getFavoriteStatus() == false){
            ImageIcon emptyHeartButtonImage = new ImageIcon("src/Resources/EmptyHeart.png");
            Image scaledImage = emptyHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            favoriteButton.setIcon(new ImageIcon(scaledImage));
        } else {
            ImageIcon fullHeartButtonImage = new ImageIcon("src/Resources/FullHeart.png");
            Image scaledImage = fullHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            favoriteButton.setIcon(new ImageIcon(scaledImage));
        }
        controller.saveLibraryToFile();
    }

    public JPanel leftSide(){
        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();

        ImageIcon emptyHeartButtonImage = new ImageIcon("src/Resources/EmptyHeart.png");
        Image scaledImage = emptyHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        favoriteButton = new JButton(new ImageIcon(scaledImage));

        favoriteButton.addActionListener(e -> {
            currentlySelectedPhoto.setFavorite();
            changeFavoriteIcon();
            System.out.println("saved to favorites!");
        });
        buttonPanel.add(favoriteButton);

        JButton createAlbumButton = new JButton("Create an Album");
        buttonPanel.add(createAlbumButton);

        JButton addToAlbumButton = new JButton("Add to Album");
        buttonPanel.add(addToAlbumButton);

        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        leftSidePanel.add(buttonPanel);


        return leftSidePanel;
    }

    public void generateImageList(List<Photo> photos){
        for (Photo photo : photos){
            listModel.addElement(photo);
        }
    }

    public void addPhotoToList(Photo photo){
        listModel.addElement(photo);
    }

    public void setController(PhotoController controller){
        this.controller = controller;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JSplitPane getSplitPane(){
        return splitPane;
    }

    public void clearImageList(){
        listModel.clear();
    }
}
