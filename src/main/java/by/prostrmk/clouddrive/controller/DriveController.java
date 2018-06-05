package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DriveController {

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    @ResponseBody
    public String getFiles(@PathVariable String username, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            return "redirect:/";
        }else if (!user.getUsername().equals(username)){
            return "redirect:/";
        }
        List<UploadedFile> filesByUsername = new FileDao().getFilesByUsername(username);
        StringBuilder sb = new StringBuilder();
        for (UploadedFile uploadedFile : filesByUsername) {
            sb.append(uploadedFile.toString()).append("\n");
        }
        return sb.toString();
    }

}
