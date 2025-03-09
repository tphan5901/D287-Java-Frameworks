package com.example.demo.controllers;
import com.example.demo.domain.InhousePart;
import com.example.demo.service.InhousePartService;
import com.example.demo.service.InhousePartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class InhousePartController {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private InhousePartService inhousePartService;
    InhousePart inhousePart = new InhousePart();

    @GetMapping("/InhousePartForm")
    public String addInhousePart(Model model){
        Long nextId = inhousePartService.getNextId();
        inhousePart.setId(nextId);

        model.addAttribute("inhousepart",inhousePart);
        return "InhousePartForm";
    }

    @PostMapping("/InhousePartForm")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult bindingResult, Model model){
            InhousePartService repo=context.getBean(InhousePartServiceImpl.class); //context.getBean returns instance of class and lets us access function properties

            if (part.getInv() < part.getMinimum() || part.getInv() > part.getMaximum()) {
                bindingResult.rejectValue("inv", "error.inv", "Inventory must be between min and max");
            }
    
            if (bindingResult.hasErrors()) {
                return "InhousePartForm";
            }
          
            repo.save(part);
            //redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
            return "redirect:/mainscreen";
    }

}
