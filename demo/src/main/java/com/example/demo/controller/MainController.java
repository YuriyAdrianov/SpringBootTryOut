package com.example.demo.controller;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import com.example.demo.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World")
                                       String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        if(filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        }
        else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model) {
        Message message = new Message(text, tag, user);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {

        Iterable<Message> messages;
        if(filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        }
        else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);
        return "main";
    }

}