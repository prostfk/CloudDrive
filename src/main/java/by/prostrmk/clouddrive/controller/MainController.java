package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.model.FileDao;
import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.util.DataBaseWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
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

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    @ResponseBody
    public String getFiles(@PathVariable String username){
        List<UploadedFile> filesByUsername = new FileDao().getFilesByUsername(username);
        StringBuilder sb = new StringBuilder();
        for (UploadedFile uploadedFile : filesByUsername) {
            sb.append(uploadedFile.toString()).append("\n");
        }
        return sb.toString();
    }


}
