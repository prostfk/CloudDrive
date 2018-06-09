package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.model.entity.IEntity;
import by.prostrmk.clouddrive.model.entity.SharedFile;
import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {

    private User user;

    public FileController() {
        this.user = new User("anon");
    }


    @RequestMapping(value = "/personalDisk/{username}")
    public ModelAndView personalDisk(@PathVariable String username, HttpSession session){
        User user = (User) session.getAttribute("user") != null ? (User) session.getAttribute("user") : new User("anon");
        if (user.getUsername().equals("anon")){ return new ModelAndView("redirect:/auth");}
        if (!user.getUsername().equals(username)){return new ModelAndView("redirect:/");}
        List filesByUsername = new FileDao().getByStringParamList("username",username, UploadedFile.class);
        ModelAndView modelAndView = new ModelAndView("userFiles", "files", filesByUsername);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/personalDisk/{username}/share/{id}", method = RequestMethod.POST)
    public String setPublicFile(@PathVariable String username, @PathVariable Long id){
        FileDao fileDao = new FileDao();
        UploadedFile file = (UploadedFile) fileDao.getById(id, UploadedFile.class);
        SharedFile sharedFile;
        if (fileDao.getByStringParamUnique("username", username, SharedFile.class) == null){
            sharedFile = new SharedFile(username, file.getServerPath());
            fileDao.saveEntity(sharedFile);
        }else{
            sharedFile = (SharedFile) fileDao.getByStringParamUnique("username", username, SharedFile.class);
            UploadedFile uplfile = (UploadedFile) fileDao.getById(id, UploadedFile.class);
            if (!sharedFile.getFiles().contains(uplfile.getServerPath())){
                sharedFile.setFiles(sharedFile.getFiles() + "," + uplfile.getServerPath());
                fileDao.updateEntity(sharedFile);
            }
        }
        return "redirect:/personalDisk/" + username;
    }


    @RequestMapping(value = "/sharedFiles/{username}", method = RequestMethod.GET)
    public ModelAndView sharedUserFiles(@PathVariable String username, HttpSession session){
        FileDao fileDao = new FileDao();
        User user = session.getAttribute("user") != null ? (User)session.getAttribute("user") : new User("anon");
        SharedFile sharedFile = (SharedFile)fileDao.getByStringParamUnique("username", username, SharedFile.class);
        if (sharedFile == null || sharedFile.getFiles() == null){
            ModelAndView output = new ModelAndView("message", "message", "this user has no shared files");
            output.addObject("user", user);
            return output;
        }
        String []files = sharedFile.getFiles().split(",");
        UploadedFile []uploadedFiles = new UploadedFile[files.length];
        for (int i = 0; i < files.length; i++) {
            uploadedFiles[i] = new UploadedFile(username, files[i], "", "");
        }
        ModelAndView modelAndView = new ModelAndView("sharedFiles");
        modelAndView.addObject("files", uploadedFiles);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping(value = "/sharedFiles", method = RequestMethod.GET)
    public ModelAndView sharedFiles(HttpSession session){
        ModelAndView mav = new ModelAndView("sharedFilesIndex");
        FileDao fileDao = new FileDao();
        List<SharedFile> allUsers = fileDao.getAll("username", SharedFile.class);
        List<String> users = new ArrayList<>();
        for (SharedFile allUser : allUsers) {
            users.add(allUser.getUsername());
        }
        mav.addObject("users", users);
        User user = (User) session.getAttribute("user") != null ? (User) session.getAttribute("user") : new User("anon");
        mav.addObject("user", user);
        return mav;
    }

}
