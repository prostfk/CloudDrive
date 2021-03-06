package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.dao.NewsDao;
import by.prostrmk.clouddrive.model.entity.IEntity;
import by.prostrmk.clouddrive.model.entity.News;
import by.prostrmk.clouddrive.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    private NewsDao newsDao = new NewsDao();
    private User user;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ModelAndView getNews(HttpSession session){
        if (session.getAttribute("user")!=null){
            user = (User) session.getAttribute("user");
        }else{
            user = new User("Anon");
        }
        ModelAndView modelAndView = new ModelAndView("news");
        List<News> newsList = newsDao.getAll("id", News.class);
        for (int i = 0; i < newsList.size(); i++) {
            if (newsList.get(i).getContent().length() > 100){
                newsList.get(i).setContent(newsList.get(i).getContent().substring(0,140) + "...");
            }
        }
        modelAndView.addObject("newsList", newsList);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
    public ModelAndView getNewsById(@PathVariable Long id){

        ModelAndView modelAndView = new ModelAndView("singleNews");
        modelAndView.addObject("news", newsDao.getById(id, News.class));
        modelAndView.addObject("user", user);

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
    public String SubmitNewsCreation(@RequestParam(name = "file") MultipartFile file,  News news){
        news.setPathToPic(new FileDao().saveFile(file));
        news.setDateOfCreation(new Date().toString());
        newsDao.saveEntity(news);
        return "redirect:/";
    }

    @RequestMapping(value = "/editNews/{id}", method = RequestMethod.GET)
    public ModelAndView getEdit(@PathVariable Long id, HttpSession session){
        ModelAndView modelAndView = new ModelAndView("edit");
        News news = (News) newsDao.getById(id, News.class);
        if (session.getAttribute("user")!=null){
            user = (User) session.getAttribute("user");
        }else{
            user = new User("anon");
        }
        modelAndView.addObject("user", user);
        modelAndView.addObject("news", news);
        return modelAndView;
    }


    @RequestMapping(value = "/editNews/{id}", method = RequestMethod.POST)
    public String editNews(@PathVariable Long id, News news, @RequestParam(name = "file") MultipartFile file){
        FileDao fileDao = new FileDao();
        if (!file.isEmpty()){
            fileDao.deleteFile(news.getPathToPic());
            news.setPathToPic(fileDao.saveFile(file));
        }else{
            News byId = (News) fileDao.getById(id, News.class);
            news.setPathToPic(byId.getPathToPic());
        }
        newsDao.updateEntity(news);
        return "redirect:/news";
    }

}
