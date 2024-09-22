package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;

import java.util.Optional;

public abstract class InventoryRepository extends InMemRepository<Inventory> {
    public abstract Optional<Inventory> findByProductId(int productId);
    public abstract void deleteByProductId(int productId);
}
