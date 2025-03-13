A.  Create your subgroup and project by logging into GitLab using the web link provided and using the “GitLab How-To” web link, and do the following:

•  Commit with a message and push when you complete each of the tasks listed below (e.g., parts C to J).


Note: You may commit and push whenever you want to back up your changes, even if a task is not complete.


•  Submit a copy of the Git repository URL and a copy of the repository branch history retrieved from your repository, which must include the commit messages and dates.


Note: Wait until you have completed all the following prompts before you create your copy of the repository branch history.


B.  Create a README file that includes notes describing where in the code to find the changes you made for each of parts C to J. Each note should include the prompt, file name, line number, and change.


C.  Customize the HTML user interface for your customer’s application. The user interface should include the shop name, the product names, and the names of the parts.

mainscreen.html
Line: 16 <h1>Computer supply store</h1>
Line: 18 <h2>Parts</h2>
Line: 52 <h2>Products</h2>

Included as screenshot ![Screenshot 2025-03-10 125750.png](Screenshot%202025-03-10%20125750.png)

Note: Do not remove any elements that were included in the screen. You may add any additional elements you would like or any images, colors, and styles, although it is not required.


D.  Add an “About” page to the application to describe your chosen customer’s company to web viewers and include navigation to and from the “About” page and the main screen.

mainscreen.html Line: 93
<a th:href="@{about}" class="mt-3">About</a>

about.html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>About Us</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class = "container p-5">
    <p>
    Computer supply Store. We offer the best deals on computer/phone & electronic components. We sell directly from the manufacturing facility, no added cost through the supply chain.
    </p>

    <a href="http://localhost:8080/">Link to Main Screen</a>
</body>
</html>



E.  Add a sample inventory appropriate for your chosen store to the application. You should have five parts and five products in your sample inventory and should not overwrite existing data in the database.


Note: Make sure the sample inventory is added only when both the part and product lists are empty. When adding the sample inventory appropriate for the store, the inventory is stored in a set so duplicate items cannot be added to your products. When duplicate items are added, make a “multi-pack” part.

BootStrapData
    if (!partsExist) {
        // 3 in-house parts
        InhousePart ram = createInhousePart("RAM100", 9.99, 10, 2, 100);
        InhousePart ssd = createInhousePart("SSD256", 49.99, 15, 1, 50);
        InhousePart cpu = createInhousePart("CPU i7", 299.99, 5, 1, 10);

        partRepository.save(ram);
        partRepository.save(ssd);
        partRepository.save(cpu);

        // 2 outsourced parts
        OutsourcedPart flashDrive = createOutsourcedPart("Flash Drive", 9.99, 10, 2, 100, "Tek Corp");
        OutsourcedPart keyboard = createOutsourcedPart("Mechanical Keyboard", 59.99, 20, 5, 50, "KeyTech");

        outsourcedPartRepository.save(flashDrive);
        outsourcedPartRepository.save(keyboard);
    }

    // products
    if (!productsExist) {
        Product r710 = createProduct("Dell R710 Server", 99.99, 15);
        Product r720 = createProduct("Dell R720 Server", 199.99, 15);
        Product r730 = createProduct("Dell R730 Server", 299.99, 15);
        Product monitor = createProduct("27\" 4K Monitor", 299.99, 20);
        Product mouse = createProduct("Gaming Mouse", 49.99, 30);

        productRepository.save(r710);
        productRepository.save(r720);
        productRepository.save(r730);
        productRepository.save(monitor);
        productRepository.save(mouse);
    }

F.  Add a “Buy Now” button to your product list. Your “Buy Now” button must meet each of the following parameters:
•  The “Buy Now” button must be next to the buttons that update and delete products.
• The button should decrement the inventory of that product by one. It should not affect the inventory of any of the associated parts.
•  Display a message that indicates the success or failure of a purchase.

mainscreen.html Line: 88
 <td>
      <a th:href="@{/UpdateProductForm(productID=${product.id})}" class="btn btn-primary btn-sm mb-3">Update</a> 
      <a th:href="@{/purchase(productID=${product.id})}" class="btn btn-primary btn-sm mb-3">Purchase</a> <!--@{/purchase({productID=${tempProduct.Id}})} post request function-->
 </td>

ProductController.java Line: 150
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

G.  Modify the parts to track maximum and minimum inventory by doing the following:
•  Add additional fields to the part entity for maximum and minimum inventory.
•  Modify the sample inventory to include the maximum and minimum fields.
•  Add to the InhousePartForm and OutsourcedPartForm forms additional text inputs for the inventory so the user can set the maximum and minimum values.
•  Rename the file the persistent storage is saved to.
•  Modify the code to enforce that the inventory is between or at the minimum and maximum value.

InhousePartForm.html Line: 22
OutsourcedPartForm.html Line: 22
    <p> Min <input type="text" id = "min" th:field="*{minimum}" placeholder="Min" class="form-control mb-4 col-4" required/>
        <span class="text-danger" th:errors="*{minimum}"></span>
    </p>
    <p> Max <input type="text" id = "max" th:field="*{maximum}" placeholder="Max" class="form-control mb-4 col-4" required/>
        <span class="text-danger" th:errors="*{maximum}"></span>
    </p>

BoostrapData.java
//In-houseParts
InhousePart ram = createInhousePart("RAM100", 9.99, 10, 2, 100);
InhousePart ssd = createInhousePart("SSD256", 49.99, 15, 1, 50);
InhousePart cpu = createInhousePart("CPU i7", 299.99, 5, 1, 10);

//OutsourcedParts
OutsourcedPart flashDrive = createOutsourcedPart("Flash Drive", 9.99, 10, 2, 100, "Tek Corp");
OutsourcedPart keyboard = createOutsourcedPart("Mechanical Keyboard", 59.99, 20, 5, 50, "KeyTech");

//Products
Product r710 = createProduct("Dell R710 Server", 99.99, 15);
Product r720 = createProduct("Dell R720 Server", 199.99, 15);
Product r730 = createProduct("Dell R730 Server", 299.99, 15);
Product monitor = createProduct("27\" 4K Monitor", 299.99, 20);
Product mouse = createProduct("Gaming Mouse", 49.99, 30);

    private InhousePart createInhousePart(String name, double price, int inv, int min, int max) {
        InhousePart part = new InhousePart();
        part.setMinimum(min);
        part.setMaximum(max);
        return part;
    }

    private OutsourcedPart createOutsourcedPart(String name, double price, int inv, int min, int max, String company) {
        OutsourcedPart part = new OutsourcedPart();
        part.setMinimum(min);
        part.setMaximum(max);
        return part;
    }


Part.java Line: 20
 @Min(value = 0, message = "Inventory value must be positive")
    int inv;
    @Min(value = 0, message = "Minimum must be ≥ 0")
    int minimum;
    @Min(value = 1, message = "Maximum must be ≥ 1")
    int maximum;

Product.java Line: 15
@Min(value = 0, message = "Inventory value must be positive")
int inv;
@Min(value = 0, message = "Minimum must be ≥ 0")
int minimum;
@Min(value = 1, message = "Maximum must be ≥ 1")
int maximum;

application.properties: Line: 6
    spring.datasource.url=jdbc:h2:file:~/src/main/resources/springBoot-Database


InhousePartController.java Line 38
if (part.getInv() < part.getMinimum() || part.getInv() > part.getMaximum()) {
bindingResult.rejectValue("inv", "error.inv", "Inventory must be between min and max");
}

OutsourcedPartController.java Line 36
if (part.getInv() < part.getMinimum() || part.getInv() > part.getMaximum()) {
bindingResult.rejectValue("inv", "error.inv", "Inventory must be between min and max");
}


H.  Add validation for between or at the maximum and minimum fields. The validation must include the following:
•  Display error messages for low inventory when adding and updating parts if the inventory is less than the minimum number of parts.
•  Display error messages for low inventory when adding and updating products lowers the part inventory below the minimum.
•  Display error messages when adding and updating parts if the inventory is greater than the maximum.

InhousePartForm.html Line: 23
<p> Min <input type="text" id = "min" th:field="*{minimum}" placeholder="Min" class="form-control mb-4 col-4" required/>
    <span class="text-danger" th:errors="*{minimum}"></span>
</p>
<p> Max <input type="text" id = "max" th:field="*{maximum}" placeholder="Max" class="form-control mb-4 col-4" required/>
    <span class="text-danger" th:errors="*{maximum}"></span>
</p>

OutsourcedPartForm.html Line: 23
<p> Min <input type="text" id = "min" th:field="*{minimum}" placeholder="Min" class="form-control mb-4 col-4" required/>
    <span class="text-danger" th:errors="*{minimum}"></span>
</p>
<p> Max <input type="text" id = "max" th:field="*{maximum}" placeholder="Max" class="form-control mb-4 col-4" required/>
    <span class="text-danger" th:errors="*{maximum}"></span>
</p>

InhousePartController.java Line 38
if (part.getInv() < part.getMinimum() || part.getInv() > part.getMaximum()) {
bindingResult.rejectValue("inv", "error.inv", "Inventory must be between min and max");
}

OutsourcedPartController.java Line 36
if (part.getInv() < part.getMinimum() || part.getInv() > part.getMaximum()) {
bindingResult.rejectValue("inv", "error.inv", "Inventory must be between min and max");
}

I.  Add at least two unit tests for the maximum and minimum fields to the PartTest class in the test package.

PartTest.java: Line 26

    Product product;
    @BeforeEach
    void setUp() {
    partIn=new InhousePart();
    partOut=new OutsourcedPart();
    product = new Product();
    }

    @Test
    void ValidPartInventory() { //max = 100
        int max = 100;
        partIn.setMaximum(max);

        //inhouse
        assertEquals(max, partIn.getMaximum(), "maximum should be set to 100");
        partIn.setInv(max);
        assertFalse(partIn.getInv() > partIn.getMaximum(), "Part inventory should not exceed max value of 100");

        //outsourced
        partOut.setMaximum(max);
        assertEquals(max, partOut.getMaximum(), "maximum should be set to 100");
        partOut.setInv(max);
        assertFalse(partOut.getInv() > partOut.getMaximum(), "Outsourced part inventory should not exceed max value of 100");
    }

    @Test
    void ValidProductInventory() { //max = 100
        int max = 100;
        product.setInv(max);
        assertEquals(max, product.getInv());
        assertFalse(product.getInv() > 100, "Product inventory should not exceed maximum of 100");
    }
