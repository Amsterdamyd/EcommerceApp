package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {
    @Mock
    private ProductService productService;

    private ProductController controller;

    @Before
    public void setup() {
        controller = new ProductController(productService);
    }

    private Product getTestProduct() {
        Product pro = new Product();

        pro.setId(1);
        pro.setName("admin");
        pro.setCategory("fdagadoh");
        pro.setRetail_price(1323.34);
        pro.setDiscounted_price(854.41);
        pro.setAvailability(true);

        return pro;
    }

    @Test
    public void createProductTestHappyCase() throws Exception {
        final Product product = getTestProduct();
        Mockito.when(productService.createProduct(product)).thenReturn(product);

        final ResponseEntity<Product> response = controller.createProduct(product);

        Mockito.verify(productService).createProduct(product);
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.CREATED.value());
        Assert.assertTrue(response.getBody().equals(product));
    }

    @Test
    public void createProductTestFailureCase() throws Exception {
        final Product product = getTestProduct();
        Mockito.when(productService.createProduct(product)).thenThrow(new RuntimeException());

        final ResponseEntity<Product> response = controller.createProduct(product);

        Mockito.verify(productService).createProduct(product);
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateProductTestHappyCase() throws Exception {
        final long id = 1;
        final Product product = getTestProduct();
        Mockito.when(productService.updateProduct(id, product)).thenReturn(product);

        final ResponseEntity<Product> response = controller.updateProduct(id, product);

        Mockito.verify(productService).updateProduct(id, product);
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.OK.value());
        Assert.assertTrue(response.getBody().equals(product));
    }

    @Test
    public void updateProductTestFailureCase() throws Exception {
        final long id = 1;
        final Product product = getTestProduct();
        Mockito.when(productService.updateProduct(id, product)).thenThrow(new RuntimeException());

        final ResponseEntity<Product> response = controller.updateProduct(id, product);

        Mockito.verify(productService).updateProduct(id, product);
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void getProductByIdTestHappyCase() throws Exception {
        final long id = 1;
        Product product = new Product();
        Mockito.when(productService.getProductById(id)).thenReturn(product);

        final ResponseEntity<Product> response = controller.getProductById(id);

        Mockito.verify(productService).getProductById(id);
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.OK.value());
        Assert.assertTrue(response.getBody().equals(product));
    }

    @Test
    public void getProductByIdTestFailureCase() throws Exception {
        final long id = 1;
        final Product product = new Product();
        Mockito.when(productService.getProductById(id)).thenThrow(new RuntimeException());

        final ResponseEntity<Product> response = controller.getProductById(id);

        Mockito.verify(productService).getProductById(id);
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getProductByCategoryTestHappyCase() throws Exception {
        final String category = "Full Body Outfits";
        List<Product> result = new ArrayList<>();
        Mockito.when(productService.getProductByCategory(category)).thenReturn(result);

        final ResponseEntity<List<Product>> response = controller.getProducts(category);

        Mockito.verify(productService).getProductByCategory(category);
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.OK.value());
        Assert.assertTrue(response.getBody().equals(result));
    }

    @Test
    public void getProductByCategoryTestFailureCase() throws Exception {
        final String category = "Full Body Outfits";
        List<Product> result = new ArrayList<>();
        Mockito.when(productService.getProductByCategory(category)).thenThrow(new RuntimeException());

        final ResponseEntity<List<Product>> response = controller.getProducts(category);

        Mockito.verify(productService).getProductByCategory(category);
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void getAllProductsTestHappyCase() throws Exception {
        List<Product> result = new ArrayList<>();
        Mockito.when(productService.getAllProducts()).thenReturn(result);

        final ResponseEntity<List<Product>> response = controller.getProducts(null);

        Mockito.verify(productService).getAllProducts();
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.OK.value());
        Assert.assertTrue(response.getBody().equals(result));
    }

    @Test
    public void getAllProductsTestFailureCase() throws Exception {
        List<Product> result = new ArrayList<>();
        Mockito.when(productService.getAllProducts()).thenThrow(new RuntimeException());

        final ResponseEntity<List<Product>> response = controller.getProducts(null);

        Mockito.verify(productService).getAllProducts();
        Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
    }

}
