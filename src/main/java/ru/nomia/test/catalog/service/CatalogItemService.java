package ru.nomia.test.catalog.service;

import ru.nomia.test.catalog.domain.CatalogItem;
import ru.nomia.test.catalog.domain.CatalogItemDTO;

import java.util.List;

public interface CatalogItemService {
    CatalogItem saveCatalogItem(final CatalogItemDTO itemDTO);
    List<CatalogItemDTO> getCatalogItem();
    CatalogItem updateCatalogItem(final CatalogItemDTO itemDTO);
    boolean deleteCatalogItem(final CatalogItemDTO itemDTO);

}
