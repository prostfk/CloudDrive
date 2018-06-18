package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.model.entity.IEntity;
import by.prostrmk.clouddrive.model.entity.SharedFile;
import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.entity.User;
import by.prostrmk.clouddrive.model.util.DataBaseWork;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    public ModelAndView personalDisk(@PathVariable String username, HttpSession session) {
        User user = (User) session.getAttribute("user") != null ? (User) session.getAttribute("user") : new User("anon");
        if (user.getUsername().equals("anon")) {
            return new ModelAndView("redirect:/auth");
        }
        if (!user.getUsername().equals(username)) {
            return new ModelAndView("redirect:/");
        }
        List filesByUsername = new FileDao().getByStringParamList("username", username, UploadedFile.class);
        ModelAndView modelAndView = new ModelAndView("userFiles", "files", filesByUsername);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/personalDisk/{username}/share/{id}", method = RequestMethod.POST)
    public String setPublicFile(@PathVariable String username, @PathVariable Long id) {
        FileDao fileDao = new FileDao();
        UploadedFile file = (UploadedFile) fileDao.getById(id, UploadedFile.class);
        SharedFile sharedFile = new SharedFile(username, file.getServerPath());
        fileDao.saveEntity(sharedFile);
        return "redirect:/personalDisk/" + username;
    }


    @RequestMapping(value = "/sharedFiles/{username}", method = RequestMethod.GET)
    public ModelAndView sharedUserFiles(@PathVariable String username, HttpSession session) {
        FileDao fileDao = new FileDao();
        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : new User("anon");
        List<SharedFile> sharedFiles = (List<SharedFile>) fileDao.getByStringParamList("username", username, SharedFile.class);
        ModelAndView modelAndView = new ModelAndView("sharedFiles");
        modelAndView.addObject("files", sharedFiles);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/sharedFiles", method = RequestMethod.GET)
    public ModelAndView sharedFiles(HttpSession session) {
        ModelAndView mav = new ModelAndView("sharedFilesIndex");
        FileDao fileDao = new FileDao();
        List<SharedFile> allUsers = fileDao.getAll("username", SharedFile.class);
        List<String> users = new ArrayList<>();
        for (SharedFile allUser : allUsers) {
            if (!users.contains(allUser.getUsername())){
                users.add(allUser.getUsername());
            }
        }
        User user = (User) session.getAttribute("user") != null ? (User) session.getAttribute("user") : new User("anon");
        mav.addObject("users", users);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/searchSharedUser", method = RequestMethod.GET)
    public ModelAndView searchUser(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("sharedFilesIndex");
        String username = request.getParameter("person");
        List<SharedFile> users = (List<SharedFile>) DataBaseWork.search("SharedFile", "username", username);
        List<String> usernames = new ArrayList<>();
        for (SharedFile file : users) {
            usernames.add(file.getUsername());
        }
        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : new User("anon");
        modelAndView.addObject("user", user);
        modelAndView.addObject("users", usernames);
        return modelAndView;
    }

    @RequestMapping(value = "/searchSharedFiles",method = RequestMethod.GET)
    public ModelAndView searchFile(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("sharedFiles");
        String filename = request.getParameter("filename");
        List<SharedFile> files = (List<SharedFile>) DataBaseWork.search("SharedFile", "path", filename);
        modelAndView.addObject("files", files);
        return modelAndView;

    }


}
