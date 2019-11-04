package com.example.recomendador.controls;

import com.example.recomendador.models.Series;
import com.example.recomendador.models.User;
import com.example.recomendador.service.SecurityService;
import com.example.recomendador.service.SeriesService;
import com.example.recomendador.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
public class WebController {
    @Autowired
    UserService userService;
    @Autowired
    SeriesService seriesService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult){
        userValidator.validate(userForm,bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getName(),userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }
    @GetMapping("/login")
    public String login(Model model,String error, String logout){
        if(error!=null) {
            model.addAttribute("error", "Tu existencia es invalida >:(.");
        }
        if(logout!=null)
            model.addAttribute("message","Ya, largate si eso es lo que quieres");
        return "login";
    }

    @GetMapping("/watch")
    public String watch(Model model){
        return "watch";
    }

    @GetMapping({"/","/welcome"})
    public String welcome(Model model){
        List<Series> ser=seriesService.findAll();
        List<User> usr= userService.findAll();
        Set<User> predictions=SlopeOne.slopeOne(usr,ser);
        User loged=userValidator.getUser();
        for(User user: predictions){
            userService.save(user);
            if(user.getId()==loged.getId()) {
                model.addAttribute("usuario",user);
                model.addAttribute("series",ser);
                return "welcome";
            }
        }
        model.addAttribute("usuario",loged);
        model.addAttribute("series",ser);
        return "welcome";
    }
}
