package com.abnamro.recipe.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Size(max = 30)
    @NotNull
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;
}
