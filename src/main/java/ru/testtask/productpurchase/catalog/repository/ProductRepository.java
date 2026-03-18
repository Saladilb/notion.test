package ru.testtask.productpurchase.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.testtask.productpurchase.catalog.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

