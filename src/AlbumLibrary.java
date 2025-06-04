import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlbumLibrary implements Serializable {
    List<Album> albumCollection = new ArrayList<Album>();

    
    public void addAlbumToLibrary(Album album){
        albumCollection.add(album);
        System.out.println("Album " + album + " added.");
    }

    public List<Album> getAllAlbums(){
        return this.albumCollection;
    }

    public static AlbumLibrary loadFromFile(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (AlbumLibrary) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Return empty if loading fails
            e.printStackTrace();
            return null;
        }
    }    

}
