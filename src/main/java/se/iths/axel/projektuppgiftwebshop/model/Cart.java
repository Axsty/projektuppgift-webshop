package se.iths.axel.projektuppgiftwebshop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        for (OrderItem item : items) {
            if (item.getProductName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        OrderItem newItem = new OrderItem();
        newItem.setProductName(product.getName());
        newItem.setPrice(product.getPrice());
        newItem.setQuantity(quantity);

        items.add(newItem);
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void clearCart() {
        items.clear();
    }
}
