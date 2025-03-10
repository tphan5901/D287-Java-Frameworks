package com.example.demo.controllers;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.PartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("currentProduct")
public class ProductController {
    @Autowired
    private ApplicationContext context;
    private final PartService partService;
    private static Product currentProduct;
    private Product product;

    @GetMapping("/addProductForm")
    public String addProductForm(Model theModel) {
        theModel.addAttribute("parts", partService.getAll());
        product = new Product();
        currentProduct=product;
        theModel.addAttribute("product", product);

        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.getAll()){
            if(!product.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        theModel.addAttribute("assparts",product.getParts());
        return "productForm";
    }

    @PostMapping("/addProductForm")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model theModel) {
        theModel.addAttribute("product", product);
        if(bindingResult.hasErrors()){
            ProductService productService = context.getBean(ProductServiceImpl.class);
            Product product2=productService.findById((int)product.getId());
            theModel.addAttribute("parts", partService.getAll());
            List<Part>availParts=new ArrayList<>();
            for(Part p: partService.getAll()){
                if(!product2.getParts().contains(p))availParts.add(p);
            }
            theModel.addAttribute("availparts",availParts);
            theModel.addAttribute("assparts",product2.getParts());
            return "productForm";
        }
        else {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            if(product.getId()!=0) {
                Product product2 = repo.findById((int) product.getId());
                PartService partService1 = context.getBean(PartServiceImpl.class);
                if(product.getInv()- product2.getInv()>0) {
                    for (Part p : product2.getParts()) {
                        int inv = p.getInv();
                        p.setInv(inv - (product.getInv() - product2.getInv()));
                        partService1.save(p);
                    }
                }
            }
            else{
                product.setInv(0);
            }
            repo.save(product);
            return "confirmationaddproduct";
        }
    }

    @GetMapping("/UpdateProductForm")
    public String UpdateProductForm(@RequestParam("productID") int Id, Model theModel) {
            theModel.addAttribute("parts", partService.getAll());
            ProductService repo = context.getBean(ProductServiceImpl.class);
            Product theProduct = repo.findById(Id);
            currentProduct=theProduct;
            theModel.addAttribute("product", theProduct);
            theModel.addAttribute("assparts",theProduct.getParts());
            List<Part>availParts=new ArrayList<>();
            for(Part p: partService.getAll()){
                if(!theProduct.getParts().contains(p))availParts.add(p);
            }
            theModel.addAttribute("availparts",availParts);
            //send over to our form
            return "productForm";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") int Id, Model theModel) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product product=productService.findById(Id);
        for(Part part:product.getParts()){
            part.getProducts().remove(product);
            partService.save(part);
        }
        product.getParts().removeAll(product.getParts());
        productService.save(product);
        productService.deleteById(Id);

        return "confirmationdeleteproduct";
    }

    public ProductController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int Id, Model theModel){
        currentProduct.getParts().add(partService.findById(Id));
        partService.findById(Id).getProducts().add(currentProduct);
        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(currentProduct);
        partService.save(partService.findById(Id));
        theModel.addAttribute("product", currentProduct);
        theModel.addAttribute("assparts",currentProduct.getParts());
        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.getAll()){
            if(!currentProduct.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
        return "productForm";
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int Id, Model theModel){
        theModel.addAttribute("product", product);
        currentProduct.getParts().remove(partService.findById(Id));
        partService.findById(Id).getProducts().remove(currentProduct);
        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(currentProduct);
        partService.save(partService.findById(Id));
        theModel.addAttribute("product", currentProduct);
        theModel.addAttribute("assparts",currentProduct.getParts());
        List<Part>availParts=new ArrayList<>();
        for(Part p: partService.getAll()){
            if(!currentProduct.getParts().contains(p))availParts.add(p);
        }
        theModel.addAttribute("availparts",availParts);
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
