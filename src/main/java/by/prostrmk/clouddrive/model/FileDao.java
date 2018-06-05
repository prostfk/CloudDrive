package by.prostrmk.clouddrive.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FileDao {


    public boolean savePic(MultipartFile file){
        if (!file.isEmpty()){
            try{
                byte []bytes = file.getBytes();
                String name = file.getOriginalFilename();
                String rootPath = System.getProperty("catalina.home");
                File directory = new File("src/main/resources/userFiles/");
                if (!directory.exists()){
                    directory.mkdirs();
                }
                String pathName = directory.getAbsolutePath() + File.separator + name;
                File uploadedFile = new File(pathName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                pathName = "resources/pics/" + name;
//                userFile.setServerPath(pathName);
                stream.flush();
                stream.close();
                return true;
            }catch (Exception e){
                System.err.println(e);
            }
        }
        return false;
    }

}
