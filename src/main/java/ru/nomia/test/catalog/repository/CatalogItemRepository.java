package ru.nomia.test.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nomia.test.catalog.domain.CatalogItem;

import java.util.List;
import java.util.UUID;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {

    CatalogItem getCatalogItemByUuid(UUID uuid);

    List<CatalogItem> getAllByParentItemIsNull();
}
