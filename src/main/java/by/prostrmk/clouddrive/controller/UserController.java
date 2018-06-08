package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.UserDao;
import by.prostrmk.clouddrive.model.entity.User;
import by.prostrmk.clouddrive.model.util.DataBaseWork;
import by.prostrmk.clouddrive.model.util.HibernateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

}
