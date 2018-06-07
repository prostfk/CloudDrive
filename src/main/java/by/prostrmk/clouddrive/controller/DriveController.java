package by.prostrmk.clouddrive.controller;

import by.prostrmk.clouddrive.dao.FileDao;
import by.prostrmk.clouddrive.model.entity.UploadedFile;
import by.prostrmk.clouddrive.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class DriveController {

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    public ModelAndView getFiles(@PathVariable String username, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null){
            return new ModelAndView("redirect:/");
        }else if (!user.getUsername().equals(username)){
            return new ModelAndView("redirect:/");
        }
        if (session.getAttribute("user") == null){
            return new ModelAndView("index", "user", "Auth");
        }

        List<UploadedFile> filesByUsername = new FileDao().getFilesByUsername(username);
        ModelAndView modelAndView = new ModelAndView("files", "elements", filesByUsername);
        modelAndView.addObject("user", user.getUsername());
        return modelAndView;
    }

    @RequestMapping(value = "/{username}/download/{filename}",method = RequestMethod.GET)
    public void downloadFile(@PathVariable String username, @PathVariable String filename, HttpServletRequest request, HttpServletResponse response){
        String filepath = "/resources/userFiles/"+ username  +"/";
        String dataDirectory = request.getServletContext().getRealPath(filepath);
        Path file = Paths.get(dataDirectory, filename);
        if (Files.exists(file)) {
            response.setContentType(URLConnection.guessContentTypeFromName(filename));
            response.addHeader("Content-Disposition", "attachment; filename=" + filename);
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


}
