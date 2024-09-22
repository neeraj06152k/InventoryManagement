package com.example.ecom.controllers;

import com.example.ecom.dtos.*;
import com.example.ecom.models.Inventory;
import com.example.ecom.services.InventoryService;
import org.springframework.stereotype.Controller;

@Controller
public class InventoryController {


    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public CreateOrUpdateResponseDto createOrUpdateInventory(CreateOrUpdateRequestDto requestDto){
        CreateOrUpdateResponseDto responseDto = new CreateOrUpdateResponseDto();
        responseDto.setResponseStatus(ResponseStatus.FAILURE);

        try {
            Inventory inventory = inventoryService
                    .createOrUpdateInventory(
                            requestDto.getUserId(), requestDto.getProductId(), requestDto.getQuantity()
                    );

            responseDto.setInventory(inventory);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {

        }

        return responseDto;
    }

    public DeleteInventoryResponseDto deleteInventory(DeleteInventoryRequestDto requestDto){
        DeleteInventoryResponseDto responseDto = new DeleteInventoryResponseDto();
        responseDto.setResponseStatus(ResponseStatus.FAILURE);
        try{
            inventoryService.deleteInventory(requestDto.getUserId(), requestDto.getProductId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e){
        }

        return responseDto;
    }


}
