import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PhotoLibrary implements Serializable{
    List<Photo> photoCollection = new ArrayList<Photo>();

    public void addPhotoToLibrary(Photo photo){
        photoCollection.add(photo);
    }

    public void printAllPhotoPaths(){
        for (Photo photo : photoCollection){
            System.out.println(photo.getFilePath());
        }
    }

    public List<Photo> getAllPhotos(){
        return photoCollection;
    }

    public static PhotoLibrary loadFromFile(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (PhotoLibrary) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Return empty if loading fails
            e.printStackTrace();
            return null;
        }
    }

}
