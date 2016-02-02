package org.noorganization.instalist.server.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.junit.Assert.*;

public class CategoriesResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ResourceConfig(CategoriesResource.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Ignore("Not implemented yet.")
    @Test
    public void testGetCategories() throws Exception {

    }

    @Ignore("Not implemented yet.")
    @Test
    public void testPutCategoryById() throws Exception {

    }

    @Ignore("Not implemented yet.")
    @Test
    public void testPostCategoryById() throws Exception {

    }

    @Ignore("Not implemented yet.")
    @Test
    public void testDeleteCategoryById() throws Exception {

    }
}