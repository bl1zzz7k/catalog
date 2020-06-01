package ru.nomia.test.catalog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import ru.nomia.test.catalog.domain.CatalogItem;
import ru.nomia.test.catalog.domain.CatalogItemDTO;
import ru.nomia.test.catalog.service.CatalogItemMapper;
import ru.nomia.test.catalog.service.CatalogItemService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/catalog")
public class CatalogItemController {
    private static final String SUCCESS = "SUCCESS";

    @Autowired
    CatalogItemService catalogItemService;

    @PostMapping(value = "/create")
    public ResponseEntity<CatalogItemDTO> createCatalogItem(@RequestBody CatalogItemDTO itemDTO){
        CatalogItem catalogItem = catalogItemService.saveCatalogItem(itemDTO);
        CatalogItemDTO catalogItemDTO = CatalogItemMapper.toDTO(catalogItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogItemDTO);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<CatalogItemDTO>> getCatalogItemList(){
        List<CatalogItemDTO> catalogItem = catalogItemService.getCatalogItem();
        return ResponseEntity.ok(catalogItem);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateCatalogItem(@RequestBody CatalogItemDTO itemDTO){
        CatalogItem catalogItem = catalogItemService.updateCatalogItem(itemDTO);
        CatalogItemDTO catalogItemDTO = CatalogItemMapper.toDTO(catalogItem);
        return ResponseEntity.ok().body(catalogItemDTO);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteCatalogItem(@RequestBody CatalogItemDTO itemDTO){
        boolean result = catalogItemService.deleteCatalogItem(itemDTO);
        if (result) return ResponseEntity.ok().body(SUCCESS);
        else throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Удалить не удалось");
    }
}
