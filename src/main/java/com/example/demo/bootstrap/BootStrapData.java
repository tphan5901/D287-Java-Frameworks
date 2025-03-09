package com.example.demo.bootstrap;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    private final PartRepository partRepository;
    private final ProductRepository productRepository;
    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository,
                         OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository = outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        outsourcedPartRepository.findAll().forEach(part ->
                System.out.println(part.getName() + " " + part.getCompanyName()));

        // 3 inhouse parts
        if (partRepository.count() == 0) {
            InhousePart ram = createInhousePart("RAM100", 9.99, 10, 2, 100);
            InhousePart ssd = createInhousePart("SSD256", 49.99, 15, 1, 50);
            InhousePart cpu = createInhousePart("CPU i7", 299.99, 5, 1, 10);

            partRepository.save(ram);
            partRepository.save(ssd);
            partRepository.save(cpu);
        }

        // 2 outsourced parts
        if (outsourcedPartRepository.count() == 0) {
            OutsourcedPart flashDrive = createOutsourcedPart("Flash Drive", 9.99, 10, 2, 100, "Tek Corp");
            OutsourcedPart keyboard = createOutsourcedPart("Mechanical Keyboard", 59.99, 20, 5, 50, "KeyTech");

            outsourcedPartRepository.save(flashDrive);
            outsourcedPartRepository.save(keyboard);
        }

        // 5 products
        if (productRepository.count() == 0) {
            Product r710 = createProduct("Dell R710 Server", 99.99, 15);
            Product r720 = createProduct("Dell R720 Server", 199.99, 15);
            Product r730 = createProduct("Dell R730 Server", 299.99, 15);
            Product monitor = createProduct("27\" 4K Monitor", 299.99, 20);
            Product mouse = createProduct("Gaming Mouse", 49.99, 1);

            productRepository.save(r710);
            productRepository.save(r720);
            productRepository.save(r730);
            productRepository.save(monitor);
            productRepository.save(mouse);
        }
    }

    private InhousePart createInhousePart(String name, double price, int inv, int min, int max) {
        InhousePart part = new InhousePart();
        part.setName(name);
        part.setPrice(price);
        part.setInv(inv);
        part.setMinimum(min);
        part.setMaximum(max);
        return part;
    }

    private OutsourcedPart createOutsourcedPart(String name, double price, int inv, int min, int max, String company) {
        OutsourcedPart part = new OutsourcedPart();
        part.setName(name);
        part.setPrice(price);
        part.setInv(inv);
        part.setMinimum(min);
        part.setMaximum(max);
        part.setCompanyName(company);
        return part;
    }

    private Product createProduct(String name, double price, int inv) {
        return new Product(name, price, inv);
    }

}