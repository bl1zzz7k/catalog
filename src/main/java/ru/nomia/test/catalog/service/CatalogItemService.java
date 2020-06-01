package ru.nomia.test.catalog.service;

import ru.nomia.test.catalog.domain.CatalogItem;
import ru.nomia.test.catalog.domain.CatalogItemDTO;

import java.util.List;

public interface CatalogItemService {
    CatalogItem saveCatalogItem(CatalogItemDTO itemDTO);
    List<CatalogItemDTO> getCatalogItem();
    CatalogItem updateCatalogItem(CatalogItemDTO itemDTO);
    boolean deleteCatalogItem(CatalogItemDTO itemDTO);

}
