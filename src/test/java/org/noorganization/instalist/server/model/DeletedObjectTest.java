package org.noorganization.instalist.server.model;

import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.DeletedObject;

import static org.junit.Assert.*;

public class DeletedObjectTest {
    private DeletedObject deletedObject = new DeletedObject();

    @Before
    public void setValues() throws Exception{
        deletedObject.setId(1);
        deletedObject.setType(DeletedObject.Type.UNIT);
    }

    @Test
    // testing getId method of DeletedObject
    public void getId() throws Exception{
        int id = deletedObject.getId();
        assertEquals(1,id);
    }

    @Test
    // testing setId method of DeletedObject
    public void setId() throws Exception{
        deletedObject.setId(5);

        int id = deletedObject.getId();
        assertEquals(5, id);
    }

    @Test
    // testing widthId of DeletedObject
    public void withId() throws Exception{
        DeletedObject del = deletedObject.withId(10);

        assertEquals(10, del.getId());
    }

    @Test
    // testing getType of DeletedObject
    public void getType() throws Exception{
        DeletedObject.Type type = deletedObject.getType();

        assertEquals(DeletedObject.Type.UNIT, type);
    }

    @Test
    // testing mapType of DeletedObject
    public void mapType() throws Exception{
        DeletedObject.Type type = deletedObject.mapType(Category.class);

        assertEquals(DeletedObject.Type.CATEGORY, type);
    }
}
