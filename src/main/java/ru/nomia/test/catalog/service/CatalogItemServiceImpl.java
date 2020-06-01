package ru.nomia.test.catalog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.nomia.test.catalog.domain.CatalogItem;
import ru.nomia.test.catalog.domain.CatalogItemDTO;
import ru.nomia.test.catalog.repository.CatalogItemRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class CatalogItemServiceImpl implements CatalogItemService {

    @Autowired
    private CatalogItemRepository repository;

    @Override
    public CatalogItem saveCatalogItem(final CatalogItemDTO itemDTO) {
        CatalogItem catalogItem = CatalogItemMapper.toEntity(itemDTO);
        CatalogItem parentItem = catalogItem.getParentItem();
        if (parentItem != null) {
            CatalogItem persistParentItem = repository.getCatalogItemByUuid(parentItem.getUuid());
            persistParentItem.addReferenceItem(catalogItem);
            return repository.save(persistParentItem);
        }
        return repository.save(catalogItem);
    }

    @Override
    public List<CatalogItemDTO> getCatalogItem() {
        List<CatalogItem> catalogItemList = repository.getAllByParentItemIsNull();
        return CatalogItemMapper.toDtoList(catalogItemList);
    }

    @Override
    public CatalogItem updateCatalogItem(final CatalogItemDTO itemDTO) {
        CatalogItem persistItem = repository.getCatalogItemByUuid(UUID.fromString(itemDTO.getUuid()));
        updateParentItem(itemDTO, persistItem);
        updateReferenceItems(itemDTO, persistItem);
        BeanUtils.copyProperties(itemDTO, persistItem, "referenceItems", "parentItem");
        return repository.save(persistItem);
    }

    private void updateParentItem(final CatalogItemDTO itemDTO, final CatalogItem persistItem) {
        if (itemDTO.getParentItem() != null) {
            CatalogItem parentCatalogItem = repository.getCatalogItemByUuid(UUID.fromString(itemDTO.getParentItem().getUuid()));
            persistItem.setParentItem(parentCatalogItem);
        }
    }

    private void updateReferenceItems(final CatalogItemDTO itemDTO, final CatalogItem persistItem) {
        if (itemDTO.getReferenceItems() != null) {
            for (CatalogItemDTO referenceItemDTO : itemDTO.getReferenceItems()) {
                CatalogItem persistReferenceItem = repository.getCatalogItemByUuid(UUID.fromString(referenceItemDTO.getUuid()));
                if (persistReferenceItem == null) {
                    persistReferenceItem = CatalogItemMapper.toEntity(referenceItemDTO);
                }
                if (persistItem.getReferenceItems() != null && !persistItem.getReferenceItems().contains(persistReferenceItem)) {
                    persistReferenceItem.setParentItem(persistItem);
                    persistItem.addReferenceItem(persistReferenceItem);
                }
            }
        }
    }

    @Override
    public boolean deleteCatalogItem(final CatalogItemDTO itemDTO) {
        UUID uuid = UUID.fromString(itemDTO.getUuid());
        CatalogItem catalogItem = repository.getCatalogItemByUuid(uuid);
        if (catalogItem == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Объект не найден");
        }
        repository.delete(catalogItem);
        return true;
    }
}
