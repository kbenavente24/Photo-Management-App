package View;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.util.List;

import javax.swing.*;

import Controller.LibraryController;
import Model.Album;
import Model.Photo;

public class MediaLibraryPanel extends JPanel{

    private final JScrollPane scrollPane;

    private LibraryController controller;

    private DefaultListModel<Object> listModel;

    private JList<Object> photoList;

    private final JSplitPane splitPane;

    private final JPanel previewPanel;
    
    private final JLabel previewLabel;

    private Object currentlySelectedObject;

    private MainWindow mainWindow;

    private JButton favoriteButton;

    public MediaLibraryPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;
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

                    changeFavoriteIcon();
                   
                    ImageIcon icon = new ImageIcon(photoSelected.getFilePath());

                    Image scaledImage = icon.getImage().getScaledInstance(previewPanel.getWidth(), previewPanel.getHeight(), Image.SCALE_SMOOTH);
                    previewLabel.setIcon(new ImageIcon(scaledImage));
                    } else {
                        Album currentlySelectedObjectToAlbum = (Album) selectedPhoto;
                        currentlySelectedObject = currentlySelectedObjectToAlbum;
                        changeFavoriteIcon();
                        controller.checkAndDisplayFromAlbum(currentlySelectedObjectToAlbum);

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
                    value = "📷 " + photo; 
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
        } else if(currentlySelectedObject instanceof Photo){
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
        } else {
            ImageIcon emptyHeartButtonImage = new ImageIcon("src/Resources/EmptyHeart.png");
            Image scaledImage = emptyHeartButtonImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            favoriteButton.setIcon(new ImageIcon(scaledImage));
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
                if(currentlySelectedObject instanceof Album){
                    Album objectToAlbum = (Album) currentlySelectedObject;
                    objectToAlbum.setFavorite();
                } else {
                    Photo objectToPhoto = (Photo) currentlySelectedObject;
                    objectToPhoto.setFavorite();
                }
            }
            changeFavoriteIcon();
        });
        buttonPanel.add(favoriteButton);

        JButton createAlbumButton = new JButton("Create an Album");
        createAlbumButton.addActionListener(e -> {
            showCreateAlbumPanel();
        });
        buttonPanel.add(createAlbumButton);

        JButton addToAlbumButton = new JButton("Add to Album");
        addToAlbumButton.addActionListener(e ->{
            if(currentlySelectedObject instanceof Photo){
                List<Album> albumCollection = controller.getAlbumCollection();
                if(albumCollection.isEmpty()){
                    JOptionPane.showMessageDialog(mainWindow, "No albums available. Create an album first.");
                    return;
                }

                DefaultListModel<Album> albumListModel = new DefaultListModel<>();
                for(Album album : albumCollection){
                    albumListModel.addElement(album);
                }

                JList<Album> albumJList = new JList<>(albumListModel);
                albumJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                JScrollPane albumScrollPane = new JScrollPane(albumJList);
                albumScrollPane.setPreferredSize(new java.awt.Dimension(250,150));

                int result = JOptionPane.showConfirmDialog(mainWindow, albumScrollPane, "Select Album"
                ,JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if(result == JOptionPane.OK_OPTION){
                    Album selectedAlbum = albumJList.getSelectedValue();
                    if (selectedAlbum != null){
                        controller.addPhotoToAlbum((Photo) currentlySelectedObject, selectedAlbum);
                        JOptionPane.showMessageDialog(mainWindow, "Photo added to album: " + selectedAlbum);
                        controller.saveAlbumLibraryToFile();
                    } else {
                        JOptionPane.showMessageDialog(mainWindow, "No album selected.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(mainWindow, "You must select a photo to add to an album.");
            }
        });
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

        int result = JOptionPane.showConfirmDialog(mainWindow, panel, 
        "Create Album",
        JOptionPane.OK_CANCEL_OPTION, 
        JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION){
            String albumName = albumNameField.getText();
            JOptionPane.showMessageDialog(mainWindow, "Album \"" + albumName + "\" created!");
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

    public void setController(LibraryController controller){
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

    public DefaultListModel<Object> getDefaultListModel(){
        return this.listModel;
    }

    public void clearFavorite(){
        this.currentlySelectedObject = null;
        changeFavoriteIcon();
        previewLabel.setIcon(null);
    }
}
