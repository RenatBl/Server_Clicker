package ru.itis.clicker.services;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names  = {"--quantity","--q"})
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
