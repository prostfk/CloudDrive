package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.dao.NewsDao;
import by.prostrmk.clouddrive.model.entity.News;
import by.prostrmk.clouddrive.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class NewsController {


//    @Autowired
//    NewsDao newsDao;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ModelAndView getNews(){
        ModelAndView modelAndView = new ModelAndView("news");
        List<News> news = new NewsDao().getLatest("News", 5);
        List<String> pics = new ArrayList<>();
        for (News news1 : news) {
            pics.addAll(Arrays.asList(news1.getArrayOfLinksToPics().split(",")));
        }
        modelAndView.addObject("news", news);
        modelAndView.addObject("pics",pics);
        return modelAndView;
    }

    @RequestMapping(value = "/createNews",method = RequestMethod.GET)
    public ModelAndView newsCreationPage(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("createNews", "news", new News());
        if (session.getAttribute("user") == null){
            modelAndView.addObject("user", "Auth");
        }else{
            User user = (User)session.getAttribute("user");
            modelAndView.addObject("user", user.getUsername());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/createNews",method = RequestMethod.POST)
    public String SubmitNewsCreation(@RequestParam(name = "file") MultipartFile[] files,  News news){
        news.setArrayOfLinksToPics(new FileDao().saveFile(files));
        news.setDateOfCreation(new Date().toString());
        new NewsDao().saveEntity(news);
        return "redirect:/";
    }

}
