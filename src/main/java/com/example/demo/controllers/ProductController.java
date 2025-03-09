package com.example.demo.controllers;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.imageio.metadata.IIOMetadataNode;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@SessionAttributes("currentProduct")
@Controller
public class ProductController {
    @Autowired
    private ApplicationContext context;
    private final PartService partService;
    private final ProductService productService;
    private Product currentProduct;
    private Product product;
    private List<Part> getAvailableParts() {
        return new ArrayList<>(partService.getAll());
    }

    @GetMapping("/addProductForm")
    public String addProductForm(Model model, HttpSession session) {
        session.removeAttribute("currentProduct");
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("parts", partService.getAll());
        model.addAttribute("availparts", getAvailableParts());
        model.addAttribute("assparts", product.getParts());
        return "productForm";
    }

    @PostMapping("/addProductForm")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("parts", partService.getAll());
            model.addAttribute("availparts", getAvailableParts());
            return "productForm";
        }

        //price validation
        double totalPartsPrice = product.getParts().stream().mapToDouble(Part::getPrice).sum();
        if (product.getPrice() < totalPartsPrice) {
            bindingResult.rejectValue("price", "error.price", "Product price must be ≥ sum of parts");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("parts", partService.getAll());
            return "productForm";
        }

        productService.save(product);

        // Calculate delta and adjust parts inventory
        int delta;
        if (product.getId() == 0) {
            delta = product.getInv();
        } else {
            Product originalProduct = productService.findById(product.getId());
            int originalInv = originalProduct.getInv();
            delta = product.getInv() - originalInv;
        }

        if (delta > 0) {
            for (Part part : product.getParts()) {
                part.setInv(part.getInv() - delta);
                partService.save(part);
            }
        }

        session.removeAttribute("currentProduct");
        currentProduct = null;
        return "redirect:/mainscreen";
    }

    @GetMapping("/UpdateProductForm")
    public String UpdateProductForm(@RequestParam("productID") int Id, Model model) {
        model.addAttribute("parts", partService.getAll());
        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product newProduct = repo.findById(Id);
        currentProduct=newProduct;
        //set model attribute to populate the form
        model.addAttribute("product", newProduct);
        model.addAttribute("assparts",newProduct.getParts());
        model.addAttribute("availparts",getAvailableParts());
        //send to form
        return "productForm";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") int Id) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product productToDelete = productService.findById(Id);
        // Disassociate parts from the product
        for (Part part : productToDelete.getParts()) { //for part of product
            part.getProducts().remove(productToDelete);
            partService.save(part);
        }
        productToDelete.getParts().clear();
        productService.save(productToDelete); // Save updated product without parts
        productService.deleteById(Id);
        return "redirect:/mainscreen";
    }

    public ProductController(PartService partService, ProductService productService) {
        this.partService = partService;
        this.productService = productService;
    }

    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int Id, Model model){
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Part partToAdd = partService.findById(Id);

        if (currentProduct == null || currentProduct.getId() == 0) { // Check if currentProduct is null (meaning it hasn't been saved yet)
            currentProduct = new Product();
        }


        // Check if part is already associated
        boolean partExists = currentProduct.getParts().stream()
                .anyMatch(p -> p.getId() == Id);
        if (partExists) {
            model.addAttribute("error", "This part is already associated with the product.");
            model.addAttribute("product", currentProduct);
            model.addAttribute("assparts", currentProduct.getParts());
            model.addAttribute("availparts", getAvailableParts());
            return "productForm";
        }

        currentProduct.getParts().add(partToAdd);
        partToAdd.getProducts().add(currentProduct);

        productService.save(currentProduct);
        partService.save(partToAdd);

        model.addAttribute("product", currentProduct);
        model.addAttribute("assparts",currentProduct.getParts());
        model.addAttribute("availparts", getAvailableParts());
        return "productForm";
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int Id, Model model){
        Part part = partService.findById(Id);
        currentProduct.getParts().remove(part);
        part.getProducts().remove(currentProduct);

        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(currentProduct);
        partService.save(part);

        model.addAttribute("product", currentProduct);
        model.addAttribute("assparts", currentProduct.getParts());
        model.addAttribute("availparts", getAvailableParts());
        return "productForm";
    }

    @GetMapping("/purchase")
    public String purchaseProduct(@RequestParam("productID") int Id) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product = productService.findById(Id);

        if (product.getInv() > 0) {
            product.setInv(product.getInv() - 1);
            productService.save(product);
            return "confirmationpurchase";
        } else {
            return "outofstock";
        }

    }

}
