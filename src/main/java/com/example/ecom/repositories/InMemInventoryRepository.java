package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class InMemInventoryRepository extends InventoryRepository {

    public Optional<Inventory> findByProductId(int productId){
        return map.values().stream()
                .filter(inventory -> inventory.getProduct().getId() == productId)
                .findFirst();
    }

    @Override
    public void deleteByProductId(int productId) {
        for(Map.Entry<Integer, Inventory> entry: map.entrySet()){
            if(entry.getValue().getProduct().getId() == productId){
                map.remove(entry.getKey());
                return;
            }
        }
    }
}
