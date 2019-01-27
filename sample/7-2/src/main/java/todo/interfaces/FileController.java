package todo.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import todo.application.FileService;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;

    @GetMapping("/download/{id}")
    public ModelAndView download(@PathVariable("id") int id) {
        String url = fileService.downloadFile(id);
        return new ModelAndView("redirect:"+url);
    }
}
