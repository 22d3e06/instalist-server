package org.noorganization.instalist.server.model;

import org.noorganization.instalist.server.model.generic.NamedBaseItem;
import org.noorganization.instalist.server.model.generic.NamedItem;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "recipes")
public class Recipe extends NamedBaseItem<Recipe> {
    private int         mId;

    private Set<Ingredient> mIngredients;

    public Recipe() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public Recipe withId(int _id) {
        setId(_id);
        return this;
    }

    @OneToMany(mappedBy = "recipe")
    public Set<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(Set<Ingredient> _ingredients) {
        mIngredients = _ingredients;
    }
}
