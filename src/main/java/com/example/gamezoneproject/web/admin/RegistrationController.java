package com.example.gamezoneproject.web.admin;

import com.example.gamezoneproject.domain.user.UserService;
import com.example.gamezoneproject.domain.user.dto.UserRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    public final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rejestracja")
    public String registrationForm(Model model) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute("user", userRegistrationDto);
        return "registration-form";
    }

    @PostMapping("/rejestracja")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto userRegistrationDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration-form";
        } else {
            userService.registerUserWithDefaultRole(userRegistrationDto);
            redirectAttributes.addFlashAttribute(LoginController.NOTIFICATION_ATTRIBUTE,
                    "Na podany email (%s) został wysłany link aktywacyjny".formatted(userRegistrationDto.getEmail()));
            return "redirect:/zaloguj";
        }
    }
}
