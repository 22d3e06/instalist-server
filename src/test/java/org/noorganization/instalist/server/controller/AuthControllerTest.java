/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.noorganization.instalist.server.controller;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.CommonData;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.DatabaseHelper;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Application;

import static org.junit.Assert.*;

public class AuthControllerTest extends JerseyTest {

    private IAuthController mInstance;
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

        mInstance = ControllerFactory.getAuthController();
    }

    @After
    public void tearDown() throws Exception {
        mManager.close();
        super.tearDown();
    }

    @Test
    public void testGetTokenByHttpAuth() throws Exception {
        assertNull(mInstance.getTokenByHttpAuth(mManager, 0, null));
        assertNull(mInstance.getTokenByHttpAuth(mManager, mDevice1.getId(), ""));
        assertNull(mInstance.getTokenByHttpAuth(mManager, 0, mData.mSecret));

        String token = mInstance.getTokenByHttpAuth(mManager, mDevice1.getId(), mData.mSecret);
        assertNotNull(token);
        assertEquals(mDevice1, mInstance.getDeviceByToken(token));

        String token2 = mInstance.getTokenByHttpAuth(mManager, mDevice1.getId(), mData.mSecret);
        assertNotEquals(token, token2);
        assertNull(mInstance.getDeviceByToken(token));
        assertEquals(mDevice1, mInstance.getDeviceByToken(token2));

        String tokenDev2 = mInstance.getTokenByHttpAuth(mManager, mDevice2.getId(), mData.mSecret);
        assertNotNull(tokenDev2);
        assertNotEquals(token2, tokenDev2);
        assertEquals(mDevice1, mInstance.getDeviceByToken(token2));
        assertEquals(mDevice2, mInstance.getDeviceByToken(tokenDev2));
    }
}
