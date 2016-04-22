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

package org.noorganization.instalist.server;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.server.model.DeviceGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by damihe on 03.02.16.
 */
public class HibernateTest extends TestCase {

    private EntityManagerFactory mEntityManagerFactory;

    @Before
    @Override
    protected void setUp() throws Exception {
        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        mEntityManagerFactory = Persistence.createEntityManagerFactory( "org.noorganization" +
                ".instalist.server.test" );
    }

    @After
    @Override
    protected void tearDown() throws Exception {
        mEntityManagerFactory.close();
    }

    @Test
    public void testBasicUsage() {
        // create a couple of events...
        EntityManager entityManager = mEntityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        DeviceGroup testGroup = new DeviceGroup();
        testGroup.setReadableId("HALLO!");
        entityManager.persist( testGroup );
        entityManager.getTransaction().commit();
        entityManager.close();

        // now lets pull events from the database and list them
        entityManager = mEntityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<DeviceGroup> result = entityManager.createQuery("from DeviceGroup", DeviceGroup.class)
                .getResultList();
        for (DeviceGroup group : result ) {
            System.out.println( "DeviceGroup (" + group.getId() + ") : " + group.getReadableId()
                    + ", " + group.getCreated() );
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
