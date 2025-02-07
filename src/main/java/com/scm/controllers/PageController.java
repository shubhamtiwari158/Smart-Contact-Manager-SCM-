package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn Code With Durgesh");
        model.addAttribute("githubRepo", "https://github.com/learncodewithdurgesh/");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(
            @Valid @ModelAttribute UserForm userForm,
            BindingResult result,
            HttpSession session,
            Model model) {

        if (result.hasErrors()) {
            // Validation errors
            return "register";
        }

        try {
            // Convert UserForm to User entity
            User user = new User();
            user.setName(userForm.getName());
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPassword());
            user.setAbout(userForm.getAbout());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setEnabled(false); // Account not enabled until email verification
            user.setProfilePic("https://example.com/default-profile-pic.png"); // Default profile picture

            // Save the user
            User savedUser = userService.saveUser(user);

            // Success message
            Message message = Message.builder()
                    .content("Registration successful! Please check your email for verification.")
                    .type(MessageType.green)
                    .build();
            session.setAttribute("message", message);

            return "redirect:/login";

        } catch (IllegalArgumentException e) {
            // Handle email already in use
            model.addAttribute("userForm", userForm);
            model.addAttribute("error", e.getMessage());
            return "register";

        } catch (Exception e) {
            // Handle unexpected errors
            model.addAttribute("userForm", userForm);
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "register";
        }
    }
}
