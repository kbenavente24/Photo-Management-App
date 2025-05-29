import java.io.*;

public class ExportImportUserInfo implements Serializable {

    public static void save() {
        String filename = "UserInfo.bin";
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(AboutInfo.USER_NAME);
            oos.writeObject(AboutInfo.USER_EMAIL);
            oos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void load() {
        String filename = "UserInfo.bin";
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }
        try {
            FileInputStream fin = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fin);
            String userName = (String) ois.readObject();
            String userEmail = (String) ois.readObject();
            AboutInfo.USER_NAME = userName;
            AboutInfo.USER_EMAIL = userEmail;

            ois.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}