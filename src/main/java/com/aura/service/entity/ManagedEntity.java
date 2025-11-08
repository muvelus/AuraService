package com.aura.service.entity;

import com.aura.service.enums.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "managed_entities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityType type;
    
    @Column
    private String director;
    
    @ElementCollection
    @CollectionTable(name = "entity_actors", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "actor")
    private List<String> actors = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "entity_competitors",
        joinColumns = @JoinColumn(name = "entity_id"),
        inverseJoinColumns = @JoinColumn(name = "competitor_id")
    )
    private List<ManagedEntity> competitors = new ArrayList<>();
}
