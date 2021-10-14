package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    final String filePath = "/Users/yangdi/Downloads/expediaDemo/";

    public Product createProduct(Product product) throws IOException {
        String id = String.valueOf(product.getId());
        String category = product.getCategory();

        if (fileExist(filePath, id)) {
            throw new BadRequestException();
        }

        try {
            String fileName = id + "_" + category;
            createFile(fileName);
            writeProductToFile(fileName, product);
        } catch (IOException e) {
            throw e;
        }

        return product;
    }

    public Product updateProduct(long id, Product product) throws IOException {
        if (!fileExist(filePath, String.valueOf(id))) {
            throw new BadRequestException();
        }

        Product newProduct;
        String fileName = getFileNameById(filePath, String.valueOf(id));
        try {
            newProduct = readProductFromFile(filePath, fileName);
            newProduct.setRetail_price(product.getRetail_price());
            newProduct.setDiscounted_price(product.getDiscounted_price());
            newProduct.setAvailability(product.isAvailability());

            writeProductToFile(fileName, newProduct);
        } catch (IOException e) {
            throw e;
        }

        return newProduct;
    }

    public Product getProductById(long id) throws IOException {
        if (!fileExist(filePath, String.valueOf(id))) {
            throw new ProductNotFoundException();
        }

        Product pro;
        try {
            String fileName = getFileNameById(filePath, String.valueOf(id));
            pro = readProductFromFile(filePath, fileName);
        } catch (IOException e) {
            throw e;
        }

        return pro;
    }

    //sorted by ID in ascending order
    public List<Product> getAllProducts() throws IOException {
        List<Product> list = new ArrayList<>();

        for (String fileName : getFileNames(filePath)) {
            if(!fileName.startsWith(".")) {
                list.add(readProductFromFile(filePath, fileName));
            }
        }

        list.sort(Comparator.comparingLong(Product::getId));

        return list;
    }

    // sorted by availability, category, id
    public List<Product> getProductByCategory(String category) throws IOException {
        List<Product> list = new ArrayList<>();

        for (String fileName : getFileNamesByCategory(filePath, category)) {
            if(!fileName.startsWith(".")) {
                list.add(readProductFromFile(filePath, fileName));
            }
        }

        list.sort((p1, p2) -> {
            if (p1.isAvailability() != p2.isAvailability()) {
                return p1.isAvailability() ? -1 : 1;
            }

            if(p1.getDiscounted_price() != p2.getDiscounted_price()) {
                return p1.getDiscounted_price() > p2.getDiscounted_price() ? 1 : -1;
            }

            return p1.getId() < p2.getId() ? 1 : -1;
        });

        return list;
    }

    boolean fileExist(String filePath, String productId) {
        File folder = new File(filePath);

        for (File file : folder.listFiles()) {
            String fileName = file.getName();
            String id = fileName.split("_")[0];

            if (id.equals(productId)) {
                return true;
            }
        }

        return false;
    }

    List<String> getFileNames(String filePath) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(filePath);

        for (File file : folder.listFiles()) {
            fileNames.add(file.getName());
        }

        return fileNames;
    }

    String getFileNameById(String filePath, String productId) {
        String fileName = "";

        File folder = new File(filePath);
        for (File file : folder.listFiles()) {
            fileName = file.getName();
            String id = fileName.split("_")[0];
            if (id.equals(productId)) {
                break;
            }
        }

        return fileName;
    }

    List<String> getFileNamesByCategory(String filePath, String category) {
        List<String> fileNames = new ArrayList<>();

        File folder = new File(filePath);
        for (File file : folder.listFiles()) {
            String fileName = file.getName();
            String ca = fileName.split("_")[1];
            if (ca.equals(category)) {
                fileNames.add(fileName);
            }
        }

        return fileNames;
    }


    void createFile(String fileName) throws IOException {
        File file = new File(filePath + fileName);
        file.createNewFile();
    }

    void writeProductToFile(String fileName, Product product) throws IOException {
        File file = new File(filePath + fileName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("id:" + String.valueOf(product.getId()) + "\n");
        bw.write("name:" + product.getName() + "\n");
        bw.write("category:" + product.getCategory() + "\n");
        bw.write("retail_price:" + String.valueOf(product.getRetail_price()) + "\n");
        bw.write("discounted_price:" + String.valueOf(product.getDiscounted_price()) + "\n");
        bw.write("availability:" + String.valueOf(product.isAvailability()));

        bw.flush();
        bw.close();
    }

    Product readProductFromFile(String filePath, String fileName) throws IOException {
        File file = new File(filePath + fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String value;
        Product pro = new Product();

        while ((value = br.readLine()) != null) {
            String[] content = value.split(":");
            String prefix = content[0];
            String suffix = content[1];

            if ("id".equals(prefix)) {
                pro.setId(Long.valueOf(suffix));
            } else if ("name".equals(prefix)) {
                pro.setName(suffix);
            } else if ("category".equals(prefix)) {
                pro.setCategory(suffix);
            } else if ("retail_price".equals(prefix)) {
                pro.setRetail_price(Double.valueOf(suffix));
            } else if ("discounted_price".equals(prefix)) {
                pro.setDiscounted_price(Double.valueOf(suffix));
            } else if ("availability".equals(prefix)) {
                pro.setAvailability(Boolean.valueOf(suffix));
            }
        }

        br.close();

        return pro;
    }
}
