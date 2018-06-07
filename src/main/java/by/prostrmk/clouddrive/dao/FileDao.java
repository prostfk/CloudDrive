package by.prostrmk.clouddrive.dao;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FileDao extends AbstractDao implements Dao {

    private static final Logger LOGGER = Logger.getLogger(FileDao.class);

    public void savePic(MultipartFile file, String username){
        if (!file.isEmpty()){
            try{
                byte []bytes = file.getBytes();
                String name = file.getOriginalFilename();
                File directory = new File("src/main/webapp/resources/userFiles/" + username);
                if (!directory.exists()){
                    directory.mkdirs();
                }
                String pathName = directory.getAbsolutePath() + File.separator + name;
                File uploadedFile = new File(pathName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
            }catch (Exception e){
                LOGGER.error("File error: " + e);
            }
        }
    }

}
