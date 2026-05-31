package org.example;


import java.util.List;
import java.util.Optional;
public interface ProductRepository {
    Optional<Product> findById(String id);
    Optional <Product> findByName(String name);
    List<Product> findAll();
}