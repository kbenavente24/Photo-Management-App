import java.io.File;
import java.io.Serializable;

public class Photo implements Serializable{

    private File file;
    private Boolean isFavorite;

    public Photo(File file){
        this.file = file;
        this.isFavorite = false;
    }

    public String getFilePath(){
        return this.file.getAbsolutePath();
    }

    public void setFavorite(Boolean favoriteStatus){
        this.isFavorite = favoriteStatus;
    }

    public File getFile(){
        return this.file;
    }
}
