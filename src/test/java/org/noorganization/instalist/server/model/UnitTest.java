package org.noorganization.instalist.server.model;


import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.Ingredient;
import org.noorganization.instalist.server.model.Product;
import org.noorganization.instalist.server.model.Recipe;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;
public class UnitTest {
    private Unit unit = new Unit();

    private Set<Product> set = new HashSet<Product>();

    @Before
    public void setValues() throws Exception{
        unit.setId(1);

        Product i1 = new Product();
        Product i2 = new Product();
        set.add(i1);
        set.add(i2);

        unit.setProducts(set);
    }

    @Test
    // testing getId method of Unit
    public void getId() throws Exception{
        int id = unit.getId();
        assertEquals(1,id);
    }

    @Test
    // testing setId method of Unit
    public void setId() throws Exception{
        unit.setId(5);

        int id = unit.getId();
        assertEquals(5, id);
    }

    @Test
    // testing widthId of Unit
    public void withId() throws Exception{
        Unit un = unit.withId(10);

        assertEquals(10, un.getId());
    }

    @Test
    public void getProducts() throws Exception{
        set = unit.getProducts();
        int num = set.size();

        assertEquals(2, num);
    }

}
