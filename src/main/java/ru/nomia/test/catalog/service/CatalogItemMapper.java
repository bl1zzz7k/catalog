package ru.nomia.test.catalog.service;


import ru.nomia.test.catalog.domain.CatalogItem;
import ru.nomia.test.catalog.domain.CatalogItemDTO;
import ru.nomia.test.catalog.domain.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CatalogItemMapper {

    private CatalogItemMapper() {
    }

    public static CatalogItem toEntity(CatalogItemDTO itemDTO) {
        return getCatalogItem(itemDTO);
    }

    private static List<CatalogItem> toItemList(CatalogItem parentItemDto, List<CatalogItemDTO> itemDTOList) {
        if(itemDTOList == null) return null;
        List<CatalogItem> result = new ArrayList<>();
        itemDTOList.forEach(itemDTO -> {
            CatalogItem catalogItem = getCatalogItem(itemDTO);
            catalogItem.setParentItem(parentItemDto);
            result.add(catalogItem);
        });
        return result;
    }

    public static CatalogItemDTO toDTO(CatalogItem item){
        return getCatalogItemDTO(item);
    }

    public static List<CatalogItemDTO> toDtoList(List<CatalogItem> itemList) {
        if (itemList == null) return null;
        List<CatalogItemDTO> result = new ArrayList<>();
        itemList.forEach(item -> result.add(getCatalogItemDTO(item)));
        return result;
    }

    private static CatalogItem getCatalogItem(CatalogItemDTO itemDTO) {
        if (itemDTO == null) return null;
        UUID catalogItemUuid = itemDTO.getUuid() == null? null : UUID.fromString(itemDTO.getUuid());
        CatalogItem result = CatalogItem
                .builder()
                .uuid(catalogItemUuid)
                .name(itemDTO.getName())
                .type(ItemType.valueOf(itemDTO.getType()))
                .description(itemDTO.getDescription())
                .properties(itemDTO.getProperties())
                .build();
        result.setReferenceItems(toItemList(result, itemDTO.getReferenceItems()));
        return result;
    }

    private static CatalogItemDTO getCatalogItemDTO(CatalogItem item) {
        if (item == null) return null;
        return CatalogItemDTO.builder()
                .uuid(item.getUuid().toString())
                .name(item.getName())
                .type(item.getType().name())
                .description(item.getDescription())
                .properties(item.getProperties())
                .referenceItems(toDtoList(item.getReferenceItems()))
                .build();
    }
}
