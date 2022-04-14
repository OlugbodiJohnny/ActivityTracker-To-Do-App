package com.example.stevenclassproject.controllers;

import com.example.stevenclassproject.dto.LoginDto;
import com.example.stevenclassproject.dto.TaskDto;
import com.example.stevenclassproject.models.Member;
import com.example.stevenclassproject.services.MemberServices;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

public class LoginController {

    private final MemberServices memberServices;
    private HttpSession session;
    public LoginController (MemberServices memberServices) {
        this.memberServices = memberServices;
    }

    @GetMapping("/")
    public String login (Model model) {
        model.addAttribute("loginDto", new LoginDto());
        model.addAttribute("taskDto", new TaskDto());
        return "";
    }

    @PostMapping("/")
//    public String login (@ModelAttribute("loginDto") LoginDto loginDto, Model model, HttpSession session) {
    public String login (@ModelAttribute("loginDto") LoginDto loginDto, Model model) {
        //refactor this
        Member member = memberServices.login(loginDto, session);
        if (member != null) {
//            session.setAttribute("user", member);
            return "redirect:/home";
        }
        return "redirect:/";

    }
}
