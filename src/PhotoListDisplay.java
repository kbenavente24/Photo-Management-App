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

    private DefaultListModel<Object> listModel;

    private JList<Object> photoList;

    private final JSplitPane splitPane;

    private final JPanel previewPanel;
    
    private final JLabel previewLabel;

    private Object currentlySelectedObject;

    
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
                Object selectedPhoto = photoList.getSelectedValue();
                if (selectedPhoto != null) {
                    if(selectedPhoto instanceof Photo){
                    Photo photoSelected = (Photo) selectedPhoto;
                    currentlySelectedObject = photoSelected;
    
                    // File selectedFile = selectedPhoto.getFile();
                    System.out.println("Selected: " + photoSelected.getFilePath());
                    System.out.println("Currently selected photo: ");
                    changeFavoriteIcon();
                    // You could call controller.displayPhotoDetails(selectedFile), etc.
                    ImageIcon icon = new ImageIcon(photoSelected.getFilePath());

                    Image scaledImage = icon.getImage().getScaledInstance(previewPanel.getWidth(), previewPanel.getHeight(), Image.SCALE_SMOOTH);
                    previewLabel.setIcon(new ImageIcon(scaledImage));
                    } else {
                        currentlySelectedObject = (Album) selectedPhoto;
                        changeFavoriteIcon();
                        previewLabel.setIcon(null);
                    }
                }
            }
        });

        photoList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Photo photo) {
                    value = "📷 " + photo; // Only display the file name
                } else if (value instanceof Album album){
                    value = "📁 " + album;
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
    }

    public void changeFavoriteIcon(){
        if(currentlySelectedObject instanceof Album){
            Album objectToAlbum = (Album) currentlySelectedObject;
            if(objectToAlbum.getFavoriteStatus() == false){
                ImageIcon emptyHeartButtonImage = new ImageIcon("src/Resources/EmptyHeart.png");
                Image scaledImage = emptyHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                favoriteButton.setIcon(new ImageIcon(scaledImage));
            }   else {
                ImageIcon fullHeartButtonImage = new ImageIcon("src/Resources/FullHeart.png");
                Image scaledImage = fullHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                favoriteButton.setIcon(new ImageIcon(scaledImage));
            }
            controller.saveAlbumLibraryToFile();
        } else {
            Photo objectToPhoto = (Photo) currentlySelectedObject;
            if(objectToPhoto.getFavoriteStatus() == false){
                ImageIcon emptyHeartButtonImage = new ImageIcon("src/Resources/EmptyHeart.png");
                Image scaledImage = emptyHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                favoriteButton.setIcon(new ImageIcon(scaledImage));
            }   else {
                ImageIcon fullHeartButtonImage = new ImageIcon("src/Resources/FullHeart.png");
                Image scaledImage = fullHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                favoriteButton.setIcon(new ImageIcon(scaledImage));
            }
            controller.saveLibraryToFile();
        }
    }

    public JPanel leftSide(){
        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setLayout(new BoxLayout(leftSidePanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();

        ImageIcon emptyHeartButtonImage = new ImageIcon("src/Resources/EmptyHeart.png");
        Image scaledImage = emptyHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        favoriteButton = new JButton(new ImageIcon(scaledImage));

        favoriteButton.addActionListener(e -> {
            if(currentlySelectedObject != null){
                System.out.println("TRIGGERED FAVORITE BUTTON");
                System.out.println("");
                if(currentlySelectedObject instanceof Album){
                    System.out.println("TRIGGERED ALBUM FAVORITE ");
                    System.out.println("");
                    Album objectToAlbum = (Album) currentlySelectedObject;
                    System.out.println("CURRENT FAVORITE STATUS OF ALBUM: ");
                    System.out.println(objectToAlbum.getFavoriteStatus());
                    objectToAlbum.setFavorite();
                    System.out.println("NEW STATUS: ");
                    System.out.println(objectToAlbum.getFavoriteStatus());
                } else {
                    Photo objectToPhoto = (Photo) currentlySelectedObject;
                    objectToPhoto.setFavorite();
                }
            }
            changeFavoriteIcon();
            System.out.println("saved to favorites!");
        });
        buttonPanel.add(favoriteButton);

        JButton createAlbumButton = new JButton("Create an Album");
        createAlbumButton.addActionListener(e -> {
            showCreateAlbumPanel();
        });
        buttonPanel.add(createAlbumButton);

        JButton addToAlbumButton = new JButton("Add to Album");
        buttonPanel.add(addToAlbumButton);

        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        leftSidePanel.add(buttonPanel);


        return leftSidePanel;
    }

    public void showCreateAlbumPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField albumNameField = new JTextField(15);

        panel.add(new JLabel("Enter Album Name:"));
        panel.add(albumNameField);

        int result = JOptionPane.showConfirmDialog(this.splitPane, panel, 
        "Create Album",
        JOptionPane.OK_CANCEL_OPTION, 
        JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION){
            String albumName = albumNameField.getText();
            JOptionPane.showMessageDialog(this.splitPane, "Album \"" + albumName + "\" created!");
            controller.addAlbum(albumName);
        }
    }

    public void generateImageList(List<Photo> photos){
        for (Photo photo : photos){
            listModel.addElement(photo);
        }
    }

    public void generateAlbumList(List<Album> albums){
        for(Album album : albums){
            listModel.addElement(album);
        }
    }

    public void addPhotoToList(Photo photo){
        listModel.addElement(photo);
    }

    public void addAlbumToList(Album album){
        listModel.addElement(album);
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
