package com.kumaran.controller;

import com.kumaran.controller.impl.I_ProductController;
import com.kumaran.models.Product;
import com.kumaran.utils.AppException;
import com.kumaran.utils.StringUtils;
import com.kumaran.view.ProductsPage;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.kumaran.utils.AppInput.enterInt;
import static com.kumaran.utils.AppInput.enterString;
import static com.kumaran.utils.FileUtil.getCategoryProducts;
import static com.kumaran.utils.FileUtil.getProductPath;
import static com.kumaran.utils.LoadUtils.getProducts;
import static com.kumaran.utils.Utils.println;

public class ProductController implements I_ProductController {

    private int categoryId = 0;
    private ProductsPage productsPage;
    private CartController cartController;
    private HomeController homeController;

    public ProductController(HomeController homeController) {
        productsPage = new ProductsPage();
        this.homeController = homeController;
        cartController = new CartController(homeController);
    }

    public ProductController() {
        productsPage = new ProductsPage();
    }

    @Override
    public void viewProducts() {
        productsPage.AdminProducts();
        try (BufferedReader in = new BufferedReader(new FileReader(getProductPath()))) {
            String str;
            while ((str = in.readLine()) != null) {
                println("   " + str);
            }
        } catch (FileNotFoundException e) {
            println("File Not Found");
        } catch (IOException e) {
            println("File Read Error");
        }
    }

    @Override
    public void addProducts() {
        String title, description, category;
        int price, stocks;
        title = enterString(StringUtils.ENTER_TITLE);
        description = enterString(StringUtils.ENTER_DESCRIPTION);
        try {
            price = enterInt(StringUtils.ENTER_PRICE);
            stocks = enterInt(StringUtils.ENTER_STOCKS);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        category = enterString(StringUtils.ENTER_CATEGORY);

        try {
            FileWriter csvWriter = new FileWriter(getProductPath(), true);
            int id = (int) (Math.random() * 100);
            csvWriter.append("\n");
            csvWriter.append(id + "," + title + "," + description + "," + price + "," + stocks + "," + category);
            csvWriter.flush();
            csvWriter.close();
            productsPage.printProductAddedSuccessfully();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showProducts(int categoryId) {
        this.categoryId = categoryId;
        ArrayList<Product> products = getProducts();
        if (categoryId != 0) {
            ArrayList<Product> categoryProducts = new ArrayList<>();
            for (Product product : products) {
                if (product.getCategory().getId() == categoryId) {
                    categoryProducts.add(product);
                }
            }
            products = categoryProducts;
        }

        productsPage.printProducts(products);

        try {
            int choice = enterInt(StringUtils.ENTER_CHOICE);
            int validProductId = 0;

            if (choice == 99) {
                homeController.printMenu();
            } else {
                for (Product product : products) {
                    if (product.getId() == choice) {
                        validProductId = product.getId();
                        break;
                    }
                }

                if (validProductId != 0) {
                    cartController.addToCart(validProductId);
                    productsPage.printSuccess();
                    showProducts(categoryId);
                } else {
                    invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
                }
            }
        } catch (AppException appException) {
            invalidChoice(appException);
        }
    }


    public void editProducts() throws AppException {
        int id = enterInt(StringUtils.ENTER_CATEGORY_ID);
        String title, description, category;
        int price, stocks;
        title = enterString(StringUtils.ENTER_TITLE);
        description = enterString(StringUtils.ENTER_DESCRIPTION);
        try {
            price = enterInt(StringUtils.ENTER_PRICE);
            stocks = enterInt(StringUtils.ENTER_STOCKS);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        category = enterString(StringUtils.ENTER_CATEGORY);


        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(getProductPath()))) {
            records = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        for (String[] record : records) {
            if (!record[0].isEmpty()) {
                try {
                    int categoryIdFromRecord = Integer.parseInt(record[0].trim());
                    if (categoryIdFromRecord == id) {
                        record[1] = title;
                        record[2] = description;
                        record[3] = String.valueOf(price);
                        record[4] = String.valueOf(stocks);
                        record[5] = category;


                    }
                } catch (NumberFormatException ex) {
                }
            }
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(getProductPath(), false))) {
            writer.writeAll(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productsPage.printProductEditedSuccessfully();
    }


    @Override
    public void deleteProducts() {
        int id = 0;
        try {
            id = enterInt(StringUtils.ENTER_ID);
        } catch (AppException e) {
            throw new RuntimeException(e);
        }
        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(getProductPath()))) {
            records = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        List<String[]> recordsToDelete = new ArrayList<>();

        for (String[] record : records) {
            try {
                int categoryIdFromRecord = Integer.parseInt(record[0].trim());
                if (categoryIdFromRecord != id) {
                    recordsToDelete.add(record);
                }
            } catch (NumberFormatException ex) {
            }
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(getProductPath(), false))) {
            writer.writeAll(recordsToDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productsPage.printProductDeletedSuccessfully();

    }

    private void invalidChoice(AppException appException) {
        println(appException.getMessage());
        showProducts(categoryId);
    }
}
