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
    public ModelAndView getIndex(){
        return new ModelAndView("index", "user", user);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView getUpload(HttpSession session){
        if (session.getAttribute("user")==null){
            return new ModelAndView("redirect:/auth");
        }
        return new ModelAndView("upload");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String submit(@RequestParam MultipartFile file, HttpSession session){
        User user = (User) session.getAttribute("user");
        new FileDao().saveFile(file, user.getUsername());
        UploadedFile uploadedFile = new UploadedFile(user.getUsername(),"/resources/userFiles/" + user.getUsername() +  "/" + file.getOriginalFilename(),"/users/" + user.getUsername(), new Date().toString());
        DataBaseWork.addToDataBase(uploadedFile);
        return "success";
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView gettestpage(){
        return new ModelAndView("TEST", "user", "Roman");
    }


}
