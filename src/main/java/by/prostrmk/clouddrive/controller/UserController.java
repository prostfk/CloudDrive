package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.UserDao;
import by.prostrmk.clouddrive.model.entity.User;
import by.prostrmk.clouddrive.model.util.DataBaseWork;
import by.prostrmk.clouddrive.model.util.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private User user;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView returnRegistrationPage(HttpSession session){
        if (session.getAttribute("user") != null){
            return new ModelAndView("redirect:/logout");
        }
        return new ModelAndView("registration","user", new User());
    }

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public ModelAndView processData(User user, HttpSession session){
        if (new UserDao().checkUser(user)){
            return new ModelAndView("message", "message", "you can't using this username");
        }
        if (user.getPassword().equals(user.getConfirmPassword())){
            user.setPassword(HibernateUtil.hashString(user.getPassword()));
            DataBaseWork.addToDataBase(user);
            session.setAttribute("user", user);
            return new ModelAndView("message", "message", "Thank you for registration!");
        }else{
            return new ModelAndView("message", "message", "passwords does not match");
        }

    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ModelAndView returnAuthPage(HttpSession session){
        if (session.getAttribute("user") != null){
            return new ModelAndView("redirect:/logout");
        }
        return new ModelAndView("auth", "user", new User());
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ModelAndView checkAuthUser(User user, HttpSession session){
        user.setPassword(HibernateUtil.hashString(user.getPassword()));
        if (new UserDao().checkUser(user)){
            session.setAttribute("user", user);
            return new ModelAndView("redirect:/");
        }else{
            return new ModelAndView("message", "message", "check you data");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session){
        if (session.getAttribute("user")!=null){
            user = (User) session.getAttribute("user");
            return new ModelAndView("logout", "user", user);
        }else{
            return new ModelAndView("redirect:/");
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPost(HttpSession session){
        if (session.getAttribute("user")!=null){
            session.removeAttribute("user");
            return "redirect:/";
        }
        return "redirect:/auth";
    }



    ///////////////////////////////////////////////////AJAX REQUESTS///////////////////////////////////////////////

    @RequestMapping(value = "/checkPassword", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String getAnswerForPassword(@RequestParam String password){
        if (password.length() > 0 && password.length() < 5){
            return "Password is weak";
        }else if(password.length() >= 5 && password.length() <= 7){
            return "Password is okay";
        }else if (password.length() > 7){
            return "Password id safe";
        }else{
            return "";
        }
    }

    @RequestMapping(value = "/checkUsername", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String getAnswerForUsername(@RequestParam String username){
        if(new UserDao().checkUser(username)){
            return "Such username already exists";
        }else{
            return "This username is available";
        }
    }

    @RequestMapping(value = "/checkMatching", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String matchPasswords(@RequestParam String password, @RequestParam String againPassword){
        if (password.equals(againPassword)){
            return "";
        }else{
            return "Passwords does not match";
        }
    }


}
