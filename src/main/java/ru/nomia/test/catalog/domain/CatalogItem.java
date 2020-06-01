package ru.nomia.test.catalog.domain;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"description", "parentItem", "referenceItems"})
@EqualsAndHashCode(exclude = {"referenceItems", "parentItem"})
@Entity
@Table(name = "catalog_item", indexes = {
        @Index(columnList = "id", name = "item_idx"),
        @Index(columnList = "uuid", name = "item_uuid_idx")},
        uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class CatalogItem implements Serializable {
    private static final long serialVersionUID = -5411765544320136181L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "uuid", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    private ItemType type;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)")
    private String name;

    @Column(name = "description", columnDefinition = "varchar(65535)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ref_uuid",
            referencedColumnName = "uuid",
            foreignKey = @ForeignKey(name = "fk_ref_item_id"))
    private CatalogItem parentItem;

    @OneToMany(mappedBy = "parentItem",
            cascade = CascadeType.ALL)
    private List<CatalogItem> referenceItems;

    @ElementCollection
    @CollectionTable(name = "item_properties",
            joinColumns = @JoinColumn(name = "item_uuid", referencedColumnName = "uuid"),
            foreignKey = @ForeignKey(name = "fk_item_id"))
    @MapKeyColumn(name = "property_name")
    @Column(name = "property_value")
    private Map<String, String> properties;

    public void addReferenceItem(CatalogItem item) {
        if (referenceItems == null) referenceItems = new ArrayList<>();
        referenceItems.add(item);
    }

    @PrePersist
    public void generateUUID() {
        uuid = UUID.randomUUID();
    }
}
