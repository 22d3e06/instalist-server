package org.noorganization.instalist.server.model;

import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.Ingredient;
import org.noorganization.instalist.server.model.Product;
import org.noorganization.instalist.server.model.Recipe;

import static org.junit.Assert.*;

public class IngrediantTest {
    private Ingredient ingredient = new Ingredient();

    @Before
    public void setValues() throws Exception{
        ingredient.setId(1);

        Recipe recipe = new Recipe();
        recipe.setId(100);
        ingredient.setRecipe(recipe);

        Product product = new Product();
        product.setId(1000);
        ingredient.setProduct(product);
    }

    @Test
    // testing getId method of Ingredient
    public void getId() throws Exception{
        int id = ingredient.getId();
        assertEquals(1,id);
    }

    @Test
    // testing setId method of Ingredient
    public void setId() throws Exception{
        ingredient.setId(5);

        int id = ingredient.getId();
        assertEquals(5, id);
    }

    @Test
    // testing widthId of Ingredient
    public void withId() throws Exception{
        Ingredient ing = ingredient.withId(10);

        assertEquals(10, ing.getId());
    }

    @Test
    // testing getRecipe of Ingredient
    public void getRecipe() throws Exception{
        Recipe recipe = ingredient.getRecipe();

        assertEquals(100, recipe.getId());
    }

    @Test
    // testing getProduct of Ingredient
    public void getProduct() throws Exception{
        Product product = ingredient.getProduct();

        assertEquals(1000, product.getId());
    }

}
