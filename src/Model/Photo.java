package Model;
import java.io.File;
import java.io.Serializable;

public class Photo implements Serializable, ListItem{

    private String filePath;
    private Boolean isFavorite;

    public Photo(String filePath){
        this.filePath = filePath;
        this.isFavorite = false;
    }

    public String getFilePath(){
        return this.filePath;
    }

    @Override
    public void setFavorite(){
        if(this.isFavorite){
            this.isFavorite = false;
        } else {
            this.isFavorite = true;
        }
    }

    @Override
    public String getName(){
        File photoFile = new File(filePath);
        String fileName = photoFile.getName(); // or file.getAbsolutePath();

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(0, dotIndex); // "vacation"
        } else {
            return fileName; // return as-is if no extension found
        }
    }


    @Override
    public Boolean getFavoriteStatus(){
        return this.isFavorite;
    }

    @Override
    public String toString() {
        File photoFile = new File(filePath);
        return photoFile.getName(); // or file.getAbsolutePath();
    }

}
