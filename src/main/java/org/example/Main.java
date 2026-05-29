package org.example;

import java.util.List;
import java.util.Optional;
public class Main {
    public static void main(String[] args) {
        ProductRepository repository = new InMemoryProductRepository();
        CartService service = new CartService(repository);
        Cart cart = new Cart();
        System.out.println("=== Интернет-магазин ===\n");
        service.addToCart("1", cart);
        service.addToCart("2", cart);
        service.addToCart("3", cart);
        System.out.println("Корзина: " + cart);
        System.out.println("Товары:");
        cart.getItems().forEach(p -> System.out.println("  - " + p));
        double discounted = service.applyDiscount(cart, 10);
        System.out.printf("%nСумма без скидки: %.2f руб.%n", cart.getTotalPrice());
        System.out.printf("Сумма со скидкой 10%%: %.2f руб.%n", discounted);
        double total = service.checkout(cart);
        System.out.printf("%nЗаказ оформлен! К оплате: %.2f руб.%n", total);
        System.out.println("Корзина после оформления: " + cart);
    }
    static class InMemoryProductRepository implements ProductRepository {
        private final List<Product> products = List.of(
                new Product("1", "Ноутбук",    75000.00),
                new Product("2", "Мышь",         990.00),
                new Product("3", "Клавиатура",   2500.00)
        );
        @Override
        public Optional<Product> findById(String id) {
            return products.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst();
        }
        @Override
        public List<Product> findAll() {
            return products;
        }
    }
}