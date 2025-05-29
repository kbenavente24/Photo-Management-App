import java.io.File;

public class Photo {

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
