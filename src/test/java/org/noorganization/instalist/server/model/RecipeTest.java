package org.noorganization.instalist.server.model;

import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.Recipe;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;
public class RecipeTest {
    private Recipe recipe = new Recipe();

    private Set<Ingredient> set = new HashSet<Ingredient>();

    @Before
    public void setValues() throws Exception{
        recipe.setId(1);

        Ingredient i1 = new Ingredient();
        Ingredient i2 = new Ingredient();
        set.add(i1);
        set.add(i2);

        recipe.setIngredients(set);
    }

    @Test
    // testing getId method of Recipe
    public void getId() throws Exception{
        int id = recipe.getId();
        assertEquals(1,id);
    }

    @Test
    // testing setId method of Recipe
    public void setId() throws Exception{
        recipe.setId(5);

        int id = recipe.getId();
        assertEquals(5, id);
    }

    @Test
    // testing widthId of Recipe
    public void withId() throws Exception{
        Recipe rec = recipe.withId(10);

        assertEquals(10, rec.getId());
    }

    @Test
    public void getIngredients() throws Exception{
        set = recipe.getIngredients();
        int num = set.size();

        assertEquals(2, num);
    }
}
