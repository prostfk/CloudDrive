package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.model.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getIndex(){
        return "index";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String getUpload(){
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String submit(@RequestParam MultipartFile file){
        new FileDao().savePic(file);
        return "success";
    }

}
