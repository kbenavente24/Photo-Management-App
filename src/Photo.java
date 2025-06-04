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

    public void setFavorite(){
        if(this.isFavorite){
            this.isFavorite = false;
        } else {
            this.isFavorite = true;
        }
    }

    public Boolean getFavoriteStatus(){
        return this.isFavorite;
    }

    @Override
    public String toString() {
        return file.getName(); // or file.getAbsolutePath();
    }

    public File getFile(){
        return this.file;
    }
}
