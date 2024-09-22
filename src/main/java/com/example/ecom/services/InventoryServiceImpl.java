package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.exceptions.UnAuthorizedAccessException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import com.example.ecom.models.User;
import com.example.ecom.models.UserType;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Override
    public Inventory createOrUpdateInventory(int userId, int productId, int quantity)
            throws ProductNotFoundException, UserNotFoundException, UnAuthorizedAccessException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("UserNotFoundException"));
        if(user.getUserType() != UserType.ADMIN) {
            throw new UnAuthorizedAccessException("UnAuthorizedAccessException");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("ProductNotFoundException"));

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setProduct(product);
                    return newInventory;
                });

        inventory.setQuantity(quantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(int userId, int productId)
            throws UserNotFoundException, UnAuthorizedAccessException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("UserNotFoundException"));
        if(user.getUserType() != UserType.ADMIN) {
            throw new UnAuthorizedAccessException("UnAuthorizedAccessException");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("ProductNotFoundException"));

        Optional<Inventory> optionalInventory = inventoryRepository.findByProductId(productId);
        if(optionalInventory.isEmpty()) {
            return;
        }

        inventoryRepository.deleteByProductId(productId);
    }
}
