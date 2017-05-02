package org.noorganization.instalist.server.model;

import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.Device;

import static org.junit.Assert.*;

public class DeviceTest {
    private Device device = new Device();

    @Before
    public void setIdValue() throws Exception{
        device.setId(1);
    }

    @Test
    // testing getId method of Device
    public void getId() throws Exception{
        int id = device.getId();
        assertEquals(1,id);
    }

    @Test
    // testing setId method of Device
    public void setId() throws Exception{
        device.setId(5);

        int id = device.getId();
        assertEquals(5, id);
    }

    @Test
    // testing widthId of Device
    public void withId() throws Exception{
        Device dev = device.withId(10);

        assertEquals(10, dev.getId());
    }

}
