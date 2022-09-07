package com.abnamro.recipe.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Size(max = 100)
    @NotNull
    private String dishName;
    @NotNull
    @Size(max = 30)
    private String dishType;
    @NotNull
    private int portionSize;
    private String cookingInstructions;
    @CreationTimestamp
    private Date createDate;
    @UpdateTimestamp
    private Date updateDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="recipe_id")
    private List<Ingredient> ingredients;
}
