package by.prostrmk.clouddrive.dao;

import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.entity.User;
import by.prostrmk.clouddrive.model.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

public class FileDao extends AbstractDao implements Dao {

    private static final Logger LOGGER = Logger.getLogger(FileDao.class);

    public List getFilesByUserName(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(UploadedFile.class);
        criteria.add(Restrictions.eq("username", username));
        return criteria.list();
    }

    public String saveFile(MultipartFile file){
        String path = null;
            if (!file.isEmpty()){
                try{
                    byte []bytes = file.getBytes();
                    String name = file.getOriginalFilename();
                    File directory = new File("src/main/webapp/resources/static/newsData");
                    if (!directory.exists()){
                        directory.mkdirs();
                    }
                    String pathName = directory.getAbsolutePath() + File.separator + name;
                    File uploadedFile = new File(pathName);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();
//                    path = pathName;
                    path = "/resources/static/newsData/" +  name;
                } catch (IOException e) {
                    LOGGER.error(e);
                }

        }

        return path;


    }



    public void saveFile(MultipartFile file, String username){
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
