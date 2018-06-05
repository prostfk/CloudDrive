package by.prostrmk.clouddrive.model;

import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class FileDao {

    public List<UploadedFile> getFilesByUsername(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(UploadedFile.class);
        criteria.add(Restrictions.eq("username", username));
        return criteria.list();
    }

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
                System.err.println(e);
            }
        }
    }

}
