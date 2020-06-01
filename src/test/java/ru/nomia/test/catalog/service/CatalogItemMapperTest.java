package ru.nomia.test.catalog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nomia.test.catalog.domain.CatalogItem;
import ru.nomia.test.catalog.domain.CatalogItemDTO;
import ru.nomia.test.catalog.domain.ItemType;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@SpringBootTest
class CatalogItemMapperTest {

    @Test
    void toEntity() throws IOException {
        ObjectMapper om = new ObjectMapper();
        CatalogItemDTO itemDTO = om.readValue(getResourceURL("catalog_item.json"), CatalogItemDTO.class);
        CatalogItem result = CatalogItemMapper.toEntity(itemDTO);
        assertEquals(2, result.getReferenceItems().size());
        assertEquals(2, result.getReferenceItems().get(0).getReferenceItems().get(0).getReferenceItems().size());
        assertEquals(3, result.getReferenceItems().get(1).getReferenceItems().size());
    }

    @Test
    void toDtoList() {
        CatalogItem ci_1_child_1 = CatalogItem.builder()
                .uuid(UUID.randomUUID())
                .type(ItemType.CATEGORY)
                .name("ci_1_child_1")
                .build();
        CatalogItem ci_1_child_2 = CatalogItem.builder()
                .uuid(UUID.randomUUID())
                .type(ItemType.CATEGORY)
                .name("ci_1_child_2")
                .build();
        CatalogItem catalogItem1 = CatalogItem.builder()
                .uuid(UUID.randomUUID())
                .type(ItemType.CATEGORY)
                .name("ci_1_parent")
                .referenceItems(Arrays.asList(ci_1_child_1, ci_1_child_2))
                .build();
        CatalogItem catalogItem2 = CatalogItem.builder()
                .uuid(UUID.randomUUID())
                .type(ItemType.CATEGORY)
                .name("ci_2_parent")
                .build();

        List<CatalogItemDTO> result = CatalogItemMapper.toDtoList(Arrays.asList(catalogItem1, catalogItem2));
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getReferenceItems().size());
    }


    private URL getResourceURL(String name) {
        return CatalogItemMapperTest.class.getClassLoader().getResource(name);
    }

    @Test
    void toDTO() {
        CatalogItem ci_1_child_1 = CatalogItem.builder()
                .uuid(UUID.randomUUID())
                .type(ItemType.CATEGORY)
                .name("ci_1_child_1")
                .build();
        CatalogItem ci_1_child_2 = CatalogItem.builder()
                .uuid(UUID.randomUUID())
                .type(ItemType.CATEGORY)
                .name("ci_1_child_2")
                .build();
        CatalogItem catalogItem1 = CatalogItem.builder()
                .uuid(UUID.randomUUID())
                .type(ItemType.CATEGORY)
                .name("ci_1_parent")
                .referenceItems(Arrays.asList(ci_1_child_1, ci_1_child_2))
                .build();

        CatalogItemDTO result = CatalogItemMapper.toDTO(catalogItem1);
        assertEquals(2, result.getReferenceItems().size());
    }
}