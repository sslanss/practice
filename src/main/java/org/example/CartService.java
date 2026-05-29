package org.example;

public class CartService {
    private final ProductRepository repository;
    public CartService(ProductRepository repository) {
        this.repository = repository;
    }
    public void addToCart(String productId, Cart cart) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Товар с ID '" + productId + "' не найден"));
        cart.addItem(product);
    }
    public double applyDiscount(Cart cart, double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Скидка должна быть от 0 до 100%");
        }
        double total = cart.getTotalPrice();
        return total * (1 - discountPercent / 100.0);
    }
    public double checkout(Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Нельзя оформить заказ с пустой корзиной");
        }
        double total = cart.getTotalPrice();
        cart.clear();
        return total;
    }
}
