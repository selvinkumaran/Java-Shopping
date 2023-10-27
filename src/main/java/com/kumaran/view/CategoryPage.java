package com.kumaran.view;

import com.kumaran.models.Category;
import com.kumaran.utils.StringUtils;

import java.util.ArrayList;

import static com.kumaran.utils.Utils.println;

public class CategoryPage {
    public void printMenu(ArrayList<Category> categories) {
        println(StringUtils.CATEGORY_MENU);
        for (Category category : categories) {
            println(category.getId() + ". " + category.getCategoryName());
        }
        println(StringUtils.BACK_OPTION);
    }
}
