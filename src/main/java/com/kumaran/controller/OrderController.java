package com.kumaran.controller;

import com.kumaran.controller.impl.I_OrderController;
import com.kumaran.models.CartProduct;
import com.kumaran.models.User;

import java.io.FileWriter;
import java.io.IOException;

import static com.kumaran.utils.FileUtil.getFilePath;
import static com.kumaran.utils.UserUtils.getLoggedInUser;

public class OrderController implements I_OrderController {

    private final HomeController homeController;

    public OrderController(HomeController homeController) {
        this.homeController = homeController;
    }

    @Override
    public void checkout() {
        User loggedInUser = getLoggedInUser();

        try {
            FileWriter fileWriter = new FileWriter(getFilePath() + System.currentTimeMillis() + ".txt");
            fileWriter.write("Your Order are:");
            fileWriter.write("\n");

            double total = 0;
            for (CartProduct cartProduct : loggedInUser.getUserCart().getCartProducts()) {
                total += cartProduct.getCount() * cartProduct.getProduct().getPrice();
                fileWriter.write(cartProduct.getProduct().getTitle() + " x " + cartProduct.getCount() + " = Rs. " + cartProduct.getProduct().getPrice() * cartProduct.getCount());
                fileWriter.write("\n");
            }
            fileWriter.write("Total - Rs. " + total);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLoggedInUser().setUserCart(null);
        homeController.printMenu();
    }
}
