import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
    List<Photo> photoCollection = new ArrayList<Photo>();
    private String title;
    private Boolean isFavorite;

    public Album(String title){
        this.title = title;
        this.isFavorite = false;
    }

    public void addPhotoToAlbum(Photo photo){
        photoCollection.add(photo);
    }

    public void setFavorite(){
        if(this.isFavorite == false){
            this.isFavorite = true;
        } else {
            this.isFavorite = false;
        }
    }

    public List<Photo> getListOfPhotosFromAlbum(){
        return photoCollection;
    }

    public Boolean getFavoriteStatus(){
        return this.isFavorite;
    }

    @Override
    public String toString(){
        return this.title;
    }

}
