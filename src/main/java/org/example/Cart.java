package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Cart {
    private final List<Product> items = new ArrayList<>();
    public void addItem(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Товар не может быть null");
        }
        items.add(product);
    }
    public boolean removeItem(String productId) {
        return items.removeIf(p -> p.getId().equals(productId));
    }
    public List<Product> getItems() {
        return Collections.unmodifiableList(items);
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }
    public void clear() {
        items.clear();
    }
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }
    @Override
    public String toString() {
        return String.format("Cart{items=%d, total=%.2f}", items.size(), getTotalPrice());
    }
}