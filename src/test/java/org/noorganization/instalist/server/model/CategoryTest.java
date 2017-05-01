package org.noorganization.instalist.server.model;

import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.Category;
import static org.junit.Assert.*;

public class CategoryTest{

    private Category category = new Category();

    @Before
    public void setIdValue() throws Exception{
        category.setId(1);
    }

    @Test
    // testing getId method of Category
    public void getId() throws Exception{
        int id = category.getId();
        assertEquals(1,id);
    }

    @Test
    // testing setId method of Category
    public void setId() throws Exception{
        category.setId(5);

        int id = category.getId();
        assertEquals(5, id);
    }

    @Test
    // testing widthId of Category
    public void withId() throws Exception{
        Category cat = category.withId(10);

        assertEquals(10, cat.getId());
    }

    @Test
    // testing hashCode of Category
    public void HashCode() throws Exception{
        int id = category.hashCode();

        assertEquals(1, id);
    }

    @Test
    // testing equals of Category
    public void equals(){
        Category c = category;
        c.setId(1);

        boolean equal = category.equals(c);

        assertEquals(true, equal);
    }
}
