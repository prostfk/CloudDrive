package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FileController {

    private User user;

    public FileController() {
        this.user = new User("anon");
    }


    @RequestMapping(value = "/personalDisk/{username}")
    public ModelAndView personalDisk(@PathVariable String username, HttpSession session){

        if (session.getAttribute("user")!=null){
            user = (User) session.getAttribute("user");
            if (!user.getUsername().equals(username)){
                return new ModelAndView("redirect:/");
            }
        }else{
            session.setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }
        List filesByUsername = new FileDao().getByStringParamList("username",username, UploadedFile.class);
        ModelAndView modelAndView = new ModelAndView("userFiles", "files", filesByUsername);
        modelAndView.addObject("user", user);
        return modelAndView;
    }



}
