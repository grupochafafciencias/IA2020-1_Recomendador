package com.example.recomendador.controls;

import com.example.recomendador.models.Series;
import com.example.recomendador.models.User;
import com.example.recomendador.models.UserSeries;
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

import java.util.ArrayList;
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
        if(error!=null)
            model.addAttribute("error", "Tu existencia es invalida >:(.");
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
        /*String title="3D Kanojo: Real Girl Sub";
        String description="Mono chafa.";
        String[] type={"Escolares","Shojo"};
        Integer year=2018;
        String productType="Anime";
        String image="/resources/images/imagen/3d-kanojo-real-girl.jpg";
        Boolean status=false;
        Series series=new Series(title,description,type,year,productType,image,status);
        seriesService.save(series);*/
        /*String userName="yair";
        String passwd="matame";
        ArrayList<UserSeries> userWatched=new ArrayList<UserSeries>();
        for(Series serie: ser){
            UserSeries userSeries=new UserSeries(serie,6.0);
            userWatched.add(userSeries);
        }
        User foo=new User(userName,passwd,userWatched.toArray(new UserSeries[0]));
        userService.save(foo);*/
        String logedName=securityService.findLoggedInUsername();
        User loged=userService.findByUsername(logedName);
        Set<User> predictions=SlopeOne.slopeOne(usr,ser);
        for(User user: predictions){
            if(user.getId()==loged.getId()){
                model.addAttribute("usuario",user);
                model.addAttribute("series",ser);
                return "welcome";
            }
        }
        model.addAttribute("usuario",usr.get(0)); //Igual no deberia pasar... otherwise god forbids you from being user 1
        model.addAttribute("series",ser);
        return "welcome";
    }
}
