package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.entity.User;
import by.prostrmk.clouddrive.model.util.DataBaseWork;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class MainController {

    private User user;

    public MainController() {
        user = new User("anon");
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView getIndex(HttpSession session){
        if (session.getAttribute("user")==null){
            return new ModelAndView("index", "user", user);
        }
        user = (User) session.getAttribute("user");
        return new ModelAndView("index", "user", user);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView getUpload(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("upload");
        if (session.getAttribute("user")==null){
            return new ModelAndView("redirect:/auth");
        }
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String submit(@RequestParam(name = "file") MultipartFile[] files, HttpSession session){
        if (files.length == 0){
            System.out.println("LENGTH EQUALS 0");
            return "redirect:/personalDisk/" + user.getUsername();
        }
        FileDao fileDao = new FileDao();
        User user = (User) session.getAttribute("user");
        for (MultipartFile file : files) {
            fileDao.saveFile(file, user.getUsername());
            UploadedFile uploadedFile = new UploadedFile(user.getUsername(),"/resources/userFiles/" + user.getUsername() +  "/" + file.getOriginalFilename(),"/users/" + user.getUsername(), new Date().toString());
            DataBaseWork.addToDataBase(uploadedFile);
        }
        return "redirect:/personalDisk/" + user.getUsername();
    }



}
