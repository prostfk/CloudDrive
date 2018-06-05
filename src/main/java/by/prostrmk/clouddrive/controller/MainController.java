package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.util.DataBaseWork;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public @ResponseBody String submit(@RequestParam MultipartFile file, HttpSession session){
        new FileDao().savePic(file, "roman");
        session.setAttribute("username", "roman");
        UploadedFile uploadedFile = new UploadedFile("roman","/resources/userFiles/" + "roman/" + file.getOriginalFilename(),"/users/roman");

        DataBaseWork.addToDataBase(uploadedFile);
        return "success";
    }




}
