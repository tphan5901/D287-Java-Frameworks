package com.example.demo.controllers;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model model){
        OutsourcedPartService repo=context.getBean(OutsourcedPartServiceImpl.class);
        if (part.getInv() < part.getMinimum() || part.getInv() > part.getMaximum()) {
            bindingResult.rejectValue("inv", "error.inv", "Inventory must be between min and max");
        }

        if (bindingResult.hasErrors()) {
            return "OutsourcedPartForm";
        }

        repo.save(part);
        return "redirect:/mainscreen";
    }

}
