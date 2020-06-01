package ru.nomia.test.catalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"parentItem", "referenceItems", "description"})
@EqualsAndHashCode(exclude = {"parentItem", "referenceItems"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogItemDTO implements Serializable {
    private static final long serialVersionUID = -5170049383284076569L;

    private String uuid;

    private String type;

    private String name;

    private String description;

    private CatalogItemDTO parentItem;

    private List<CatalogItemDTO> referenceItems;

    private Map<String, String> properties;
}
