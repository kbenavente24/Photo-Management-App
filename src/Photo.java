import java.io.File;
import java.io.Serializable;

public class Photo implements Serializable{

    private String filePath;
    private Boolean isFavorite;

    public Photo(String filePath){
        this.filePath = filePath;
        this.isFavorite = false;
    }

    public String getFilePath(){
        return this.filePath;
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
        File photoFile = new File(filePath);
        return photoFile.getName(); // or file.getAbsolutePath();
    }

}
