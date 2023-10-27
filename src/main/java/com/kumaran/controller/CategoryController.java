package com.kumaran.controller;

import com.kumaran.controller.impl.I_CategoryController;
import com.kumaran.models.Category;
import com.kumaran.utils.AppException;
import com.kumaran.utils.StringUtils;
import com.kumaran.view.CategoryPage;

import java.util.ArrayList;

import static com.kumaran.utils.AppInput.enterInt;
import static com.kumaran.utils.LoadUtils.getCategories;
import static com.kumaran.utils.Utils.println;

public class CategoryController implements I_CategoryController {

    private final CategoryPage categoryPage;
    private final ProductController productController;
    private final HomeController homeController;

    public CategoryController(HomeController homeController) {
        categoryPage = new CategoryPage();
        productController = new ProductController(homeController);
        this.homeController = homeController;
    }

    @Override
    public void printMenu() {
        ArrayList<Category> categories = getCategories();
        categoryPage.printMenu(categories);

        try {
            int choice = enterInt(StringUtils.ENTER_CHOICE);

            if (choice == 99) {
                homeController.printMenu();
            } else {
                int validCategoryId = 0;

                for (Category category : categories) {
                    if (category.getId() == choice) {
                        validCategoryId = category.getId();
                        break;
                    }
                }

                if (validCategoryId != 0) {
                    productController.showProducts(validCategoryId);
                } else {
                    invalidChoice(new AppException(StringUtils.INVALID_CHOICE));
                }
            }
        } catch (AppException appException) {
            invalidChoice(appException);
        }
    }

    private void invalidChoice(AppException appException) {
        println(appException.getMessage());
        printMenu();
    }
}
