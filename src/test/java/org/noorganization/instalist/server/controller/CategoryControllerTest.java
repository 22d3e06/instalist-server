package org.noorganization.instalist.server.controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.CommonData;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.Category;
import org.noorganization.instalist.server.model.DeletedObject;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.DatabaseHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Application;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CategoryControllerTest extends JerseyTest {
    private ICategoryController mInstance;
    private DeviceGroup mDevicegroup;
    private EntityManager mManager;
    private Device mDevice1;
    private Device mDevice2;

    private CommonData mData;

    @Override
    protected Application configure() {
        return new ResourceConfig();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mData = new CommonData();

        mManager = DatabaseHelper.getInstance().getManager();
        mManager.getTransaction().begin();
        mDevicegroup = new DeviceGroup().withReadableId("123456");
        mDevice1 = new Device().withGroup(mDevicegroup).withName("dev1").withAuthorized(true)
                .withSecret(mData.mEncryptedSecret);
        mDevice2 = new Device().withGroup(mDevicegroup).withName("dev2").withAuthorized(true)
                .withSecret(mData.mEncryptedSecret);
        mManager.persist(mDevicegroup);
        mManager.persist(mDevice1);
        mManager.persist(mDevice2);
        mManager.getTransaction().commit();
        mManager.refresh(mDevicegroup);
        mManager.refresh(mDevice1);
        mManager.refresh(mDevice2);

        mInstance = ControllerFactory.getCategoryController(mManager);
    }

    @After
    public void tearDown() throws Exception {
        mManager.close();
        super.tearDown();
    }

    @Test
    public void add() throws Exception{
        UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        String name = "teszt";
        Instant instant = Instant.now();
        Category cat = mInstance.add(1, uid, name, instant);
        assertEquals(uid,cat.getUUID());
    }

    @Test
    public void update() throws Exception{
        // add a new element
        UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        String name = "teszt";
        Instant instant = Instant.now();
        Category cat = mInstance.add(1, uid, name, instant);

        //update element
        Category cat2 = mInstance.update(1,uid,"teszt2", instant);

        assertEquals("teszt2", cat2.getName());
    }


    @Test
    public void delete() throws Exception{
        // add a new element
        UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        String name = "teszt";
        Instant instant = Instant.now();
        Category cat = mInstance.add(1, uid, name, instant);

        //delete element
        DeletedObject deletedObject = mInstance.delete(1,uid);

        assertEquals(uid,deletedObject.getUUID());
    }
}
