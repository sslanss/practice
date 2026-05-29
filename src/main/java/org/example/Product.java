package org.example;

public class Product {
    private final String id;
    private final String name;
    private final double price;
    public Product(String id, String name, double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Цена товара не может быть отрицательной");
        }
        if (name.isEmpty() || id.isEmpty()) {
            throw new IllegalArgumentException("У товара не может не быть табельного номера");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public String getId()    { return id; }
    public String getName()  { return name; }
    public double getPrice() { return price; }
    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=%.2f}", id, name, price);
    }
}
