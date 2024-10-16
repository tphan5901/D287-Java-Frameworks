package com.example.demo.controllers;
import com.example.demo.DataObjects.OutsourcedPart;
import com.example.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OutsourcedPartController {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private OutsourcedPartService outsourcedPartService;
    private final OutsourcedPart outsourcedPart = new OutsourcedPart();

    @GetMapping("/OutsourcedPartForm")
    public String addOutsourcedPart(Model model){
        // fetch available ID
        Long nextId = outsourcedPartService.getNextId();
        outsourcedPart.setId(nextId);
        model.addAttribute("outsourcedpart",outsourcedPart);
        return "OutsourcedPartForm";
    }

    @PostMapping("/OutsourcedPartForm")
    public String submitForm(@ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model model){
        OutsourcedPartService repo=context.getBean(OutsourcedPartServiceImpl.class);
        repo.save(part);
        return "redirect:/home";
    }

}
