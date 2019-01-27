package todo.interfaces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/helloworld")
public class HelloWorldController {

	@GetMapping
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("helloworld");
		mav.addObject("message", "Welcome to AWS World!");
		return mav;
	}

}
