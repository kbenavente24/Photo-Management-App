import java.util.ArrayList;
import java.util.List;

public class PhotoLibrary {
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

}
