package com.kumaran.view;

import com.kumaran.models.Product;
import com.kumaran.utils.StringUtils;

import java.util.ArrayList;

import static com.kumaran.utils.Utils.println;

public class ProductsPage {
    public void printProducts(ArrayList<Product> products) {
        println(StringUtils.PRODUCT_MENU);
        for (Product product : products) {
            println(product.getId() + ". " + product.getTitle() + " - Rs." + product.getPrice());
        }
        println(StringUtils.BACK_OPTION);
    }

    public void printSuccess() {
        println(StringUtils.CART_SUCCESS);
    }
}
