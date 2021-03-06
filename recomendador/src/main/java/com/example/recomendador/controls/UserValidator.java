package com.example.recomendador.controls;

import com.example.recomendador.models.User;
import com.example.recomendador.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aC){
        return User.class.equals(aC);
    }

    private User user;

    public User getUser() {
        return user;
    }

    @Override
    public void validate(Object o, Errors errors) {
        user=(User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","NotEmpty");
        if(user.getName().length()<5 || user.getName().length()>19){
            errors.rejectValue("username","Size.userForm.username");
        }
        if(userService.findByUsername(user.getName())!=null){
            errors.rejectValue("username","Duplicate.userForm.username");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","NotEmpty");
        if(!user.getPasswordConfirm().equals(user.getPassword())){
            errors.rejectValue("passwordConfirm","Diff.userForm.passwordConfirm");
        }
    }

}
