package com.example.demo.controllers;
import com.example.demo.DataObjects.Part;
import com.example.demo.DataObjects.Product;
import com.example.demo.Service.PartService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String addProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("parts", partService.getAll());
        model.addAttribute("availparts", getAvailableParts());
        model.addAttribute("assparts", product.getParts());
        return "productForm";
    }

    @PostMapping("/addProductForm")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("parts", partService.getAll());
            model.addAttribute("availparts", getAvailableParts());
            return "productForm";
        }
        productService.save(product);
        currentProduct = null;
        return "redirect:/home";
    }

    @GetMapping("/UpdateProductForm")
    public String UpdateProductForm(@RequestParam("productID") int Id, Model model) {
        model.addAttribute("parts", partService.getAll());
        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product newProduct = repo.searchById(Id);
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
        Product productToDelete = productService.searchById(Id);
        // Disassociate parts from the product
        for (Part part : productToDelete.getParts()) { //for part of product
            part.getProducts().remove(productToDelete);
            partService.save(part);
        }
        productToDelete.getParts().clear();
        productService.save(productToDelete); // Save updated product without parts
        productService.deleteById(Id);
        return "redirect:/home";
    }

    public ProductController(PartService partService, ProductService productService) {
        this.partService = partService;
        this.productService = productService;
    }

    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int Id, Model model){
        ProductService productService = context.getBean(ProductServiceImpl.class);

        if (currentProduct == null || currentProduct.getId() == 0) { // Check if currentProduct is null (meaning it hasn't been saved yet)
            currentProduct = new Product();
        }

        currentProduct.getParts().add(partService.searchById(Id));
        partService.searchById(Id).getProducts().add(currentProduct);
        productService.save(currentProduct);
        partService.save(partService.searchById(Id));

        model.addAttribute("product", currentProduct);
        model.addAttribute("assparts",currentProduct.getParts());
        model.addAttribute("availparts", getAvailableParts());
        return "productForm";
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int Id, Model model){
        Part part = partService.searchById(Id);
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
        Product newProduct = productService.searchById(Id);

        newProduct.purchase();
        productService.save(newProduct);
        return "redirect:/home";
    }

}
