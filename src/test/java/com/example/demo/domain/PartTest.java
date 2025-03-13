package com.example.demo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project: demoDarbyFrameworks2-master
 * Package: com.example.demo.domain
 * <p>
 * User: carolyn.sher
 * Date: 6/24/2022
 * Time: 3:44 PM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
class PartTest {
    Part partIn;
    Part partOut;
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


    @Test
    void getId() {
        Long idValue=4L;
        partIn.setId(idValue);
        assertEquals(partIn.getId(), idValue);
        partOut.setId(idValue);
        assertEquals(partOut.getId(), idValue);
    }

    @Test
    void setId() {
        Long idValue=4L;
        partIn.setId(idValue);
        assertEquals(partIn.getId(), idValue);
        partOut.setId(idValue);
        assertEquals(partOut.getId(), idValue);
    }

    @Test
    void getName() {
        String name="test inhouse part";
        partIn.setName(name);
        assertEquals(name,partIn.getName());
        name="test outsourced part";
        partOut.setName(name);
        assertEquals(name,partOut.getName());
    }

    @Test
    void setName() {
        String name="test inhouse part";
        partIn.setName(name);
        assertEquals(name,partIn.getName());
        name="test outsourced part";
        partOut.setName(name);
        assertEquals(name,partOut.getName());
    }

    @Test
    void getPrice() {
        double price=1.0;
        partIn.setPrice(price);
        assertEquals(price,partIn.getPrice());
        partOut.setPrice(price);
        assertEquals(price,partOut.getPrice());
    }

    @Test
    void setPrice() {
        double price=1.0;
        partIn.setPrice(price);
        assertEquals(price,partIn.getPrice());
        partOut.setPrice(price);
        assertEquals(price,partOut.getPrice());
    }

    @Test
    void getInv() {
        int inv=5;
        partIn.setInv(inv);
        assertEquals(inv,partIn.getInv());
        partOut.setInv(inv);
        assertEquals(inv,partOut.getInv());
    }

    @Test
    void setInv() {
        int inv=5;
        partIn.setInv(inv);
        assertEquals(inv,partIn.getInv());
        partOut.setInv(inv);
        assertEquals(inv,partOut.getInv());
    }

    @Test
    void getProducts() {
        Product product1= new Product();
        Product product2= new Product();
        Set<Product> myProducts= new HashSet<>();
        myProducts.add(product1);
        myProducts.add(product2);
        partIn.setProducts(myProducts);
        assertEquals(myProducts,partIn.getProducts());
        partOut.setProducts(myProducts);
        assertEquals(myProducts,partOut.getProducts());
    }

    @Test
    void setProducts() {
        Product product1= new Product();
        Product product2= new Product();
        Set<Product> myProducts= new HashSet<>();
        myProducts.add(product1);
        myProducts.add(product2);
        partIn.setProducts(myProducts);
        assertEquals(myProducts,partIn.getProducts());
        partOut.setProducts(myProducts);
        assertEquals(myProducts,partOut.getProducts());
    }

    @Test
    void testToString() {
        String name="test inhouse part";
        partIn.setName(name);
        assertEquals(name,partIn.toString());
        name="test outsourced part";
        partOut.setName(name);
        assertEquals(name,partOut.toString());
    }

    @Test
    void testEquals() {
        partIn.setId(1l);
        Part newPartIn=new InhousePart();
        newPartIn.setId(1l);
        assertEquals(partIn,newPartIn);
        partOut.setId(1l);
        Part newPartOut=new OutsourcedPart();
        newPartOut.setId(1l);
        assertEquals(partOut,newPartOut);

    }

    @Test
    void testHashCode() {
        partIn.setId(1l);
        partOut.setId(1l);
        assertEquals(partIn.hashCode(),partOut.hashCode());
    }


}