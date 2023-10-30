package com.kumaran.controller;

import com.kumaran.controller.impl.I_AdminController;
import com.kumaran.utils.AppException;
import com.kumaran.utils.StringUtils;
import com.kumaran.view.AdminPage;
import com.kumaran.view.CategoryPage;
import com.kumaran.view.ProductsPage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static com.kumaran.utils.AppInput.enterInt;
import static com.kumaran.utils.FileUtil.*;
import static com.kumaran.utils.Utils.println;


public class AdminController implements I_AdminController {

    private final AdminPage adminPage;
    private final ProductsPage productsPage;
    private final CategoryPage categoryPage;
    private final HomeController homeController;
    private final ProductController productController;
    private final CategoryController categoryController;


    public AdminController() {
        adminPage = new AdminPage();
        productsPage = new ProductsPage();
        categoryPage = new CategoryPage();
        homeController = new HomeController();
        productController = new ProductController();
        categoryController = new CategoryController(homeController);

    }

    @Override
    public void productsOperations() {
        productsPage.viewProductsOperaions();
        try {
            int choice = enterInt(StringUtils.ENTER_CHOICE);
            if (choice == 1) {
                productController.viewProducts();
                productsOperations();
            } else if (choice == 2) {
                productController.addProducts();
                productsOperations();
            } else if (choice == 3) {
                productController.editProducts();
                productsOperations();
            } else if (choice == 4) {
                productController.deleteProducts();
                productsOperations();
            } else if (choice == 5) {
                homeController.printAdminMenu();
            } else {
                invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
            }
        } catch (AppException appException) {
            invalidChoice(appException);
        }
    }


    @Override
    public void categoryOperations() {
        categoryPage.viewProductsCategoryOperations();
        try {
            int choice = enterInt(StringUtils.ENTER_CHOICE);
            if (choice == 1) {
                categoryController.viewCategories();
                categoryOperations();
            } else if (choice == 2) {
                categoryController.addCategories();
                categoryOperations();
            } else if (choice == 3) {
                categoryController.editCategories();
                categoryOperations();
            } else if (choice == 4) {
                categoryController.deleteCategories();
                categoryOperations();
            } else if (choice == 5) {
                homeController.printAdminMenu();
            } else {
                invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
            }
        } catch (AppException appException) {
            invalidChoice(appException);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void viewOrders() {
        try (BufferedReader in = new BufferedReader(new FileReader(getOrderPath()))) {
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
    public void viewUsers() {
        adminPage.printUsers();
        try (BufferedReader in = new BufferedReader(new FileReader(getCredentialsFile()))) {
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

    private void invalidChoice(AppException appException) {
        println(appException.getMessage());
        productsOperations();
    }
}
