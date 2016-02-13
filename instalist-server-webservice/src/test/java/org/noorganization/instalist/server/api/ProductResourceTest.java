package org.noorganization.instalist.server.api;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.noorganization.instalist.comm.message.ProductInfo;
import org.noorganization.instalist.server.AuthenticationFilter;
import org.noorganization.instalist.server.CommonData;
import org.noorganization.instalist.server.controller.impl.ControllerFactory;
import org.noorganization.instalist.server.model.*;
import org.noorganization.instalist.server.support.DatabaseHelper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class ProductResourceTest extends JerseyTest {

    EntityManager mManager;
    String mToken;
    Product mProduct;
    Product mNAProduct;
    Product mProductWU;
    Unit mUnit;
    DeletedObject mDeletedProduct;
    DeviceGroup mGroup;
    DeviceGroup mNAGroup;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig rc = new ResourceConfig(ProductResource.class);
        rc.register(AuthenticationFilter.class);
        return rc;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        CommonData data = new CommonData();
        mManager = DatabaseHelper.getInstance().getManager();
        mManager.getTransaction().begin();

        mGroup = new DeviceGroup();
        mUnit = new Unit().withGroup(mGroup).withName("unit1").withUUID(UUID.randomUUID());
        mProduct = new Product().withGroup(mGroup).withName("product1").withUUID(UUID.randomUUID()).
                withDefaultAmount(1f).withStepAmount(0.5f);
        mProductWU = new Product().withGroup(mGroup).withName("product2").withUnit(mUnit).
                withUUID(UUID.randomUUID()).withDefaultAmount(2f).withStepAmount(2f);;
        mDeletedProduct = new DeletedObject().withGroup(mGroup).withUUID(UUID.randomUUID()).
                withType(DeletedObject.Type.PRODUCT);
        mNAGroup = new DeviceGroup();
        mNAProduct = new Product().withGroup(mNAGroup).withName("product3").
                withUUID(UUID.randomUUID());

        Device authorizedDevice = new Device().withAuthorized(true).withGroup(mGroup).
                withName("dev1").withSecret(data.mEncryptedSecret);

        mManager.persist(mGroup);
        mManager.persist(mUnit);
        mManager.persist(mProduct);
        mManager.persist(mProductWU);
        mManager.persist(mDeletedProduct);
        mManager.persist(mNAGroup);
        mManager.persist(mNAProduct);
        mManager.persist(authorizedDevice);
        mManager.getTransaction().commit();

        mManager.refresh(mGroup);
        mManager.refresh(mUnit);
        mManager.refresh(mProduct);
        mManager.refresh(mProductWU);
        mManager.refresh(mDeletedProduct);
        mManager.refresh(mNAGroup);
        mManager.refresh(mNAProduct);
        mManager.refresh(authorizedDevice);

        mToken = ControllerFactory.getAuthController().getTokenByHttpAuth(mManager,
                authorizedDevice.getId(), data.mSecret);
        assertNotNull(mToken);
    }

    @After
    public void tearDown() throws Exception {
        mManager.close();
        super.tearDown();
    }

    @Test
    public void testGetProducts() throws Exception {
        String url = "/groups/%d/products";

        Instant preQuery = Instant.now();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        ProductInfo[] allProductInfo = okResponse1.readEntity(ProductInfo[].class);
        assertEquals(3, allProductInfo.length);
        for(ProductInfo current: allProductInfo) {
            if (mProduct.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals("product1", current.getName());
                assertEquals(mProduct.getUpdated(), current.getLastChanged().toInstant());
                assertNull(current.getRemoveUnit());
                assertNull(current.getUnitUUID());
                assertEquals(1f, current.getDefaultAmount(), 0.001f);
                assertEquals(0.5f, current.getStepAmount(), 0.001f);
                assertFalse(current.getDeleted());
            } else if (mProductWU.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertEquals("product2", current.getName());
                assertEquals(mProductWU.getUpdated(), current.getLastChanged().toInstant());
                assertNull(current.getRemoveUnit());
                assertEquals(mUnit.getUUID(), UUID.fromString(current.getUnitUUID()));
                assertEquals(2f, current.getDefaultAmount(), 0.001f);
                assertEquals(2f, current.getStepAmount(), 0.001f);
                assertFalse(current.getDeleted());
            } else if (mDeletedProduct.getUUID().equals(UUID.fromString(current.getUUID()))) {
                assertNull(current.getName());
                assertNull(current.getRemoveUnit());
                assertNull(current.getUnitUUID());
                assertNull(current.getDefaultAmount());
                assertNull(current.getStepAmount());
                assertEquals(mDeletedProduct.getTime(), current.getLastChanged());
                assertTrue(current.getDeleted());
            } else
                fail("Unexpected unit.");
        }

        mManager.getTransaction().begin();
        mProduct.setUpdated(Instant.now());
        mManager.getTransaction().commit();
        Response okResponse2 = target(String.format(url, mGroup.getId())).
                queryParam("changedsince", ISO8601Utils.format(Date.from(preQuery)), true).
                request().header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse2.getStatus());
        ProductInfo[] oneProductInfo = okResponse2.readEntity(ProductInfo[].class);
        assertEquals(1, oneProductInfo.length);
        assertEquals(mProduct.getUUID(), UUID.fromString(oneProductInfo[0].getUUID()));
        assertEquals("product1", oneProductInfo[0].getName());
        assertFalse(oneProductInfo[0].getDeleted());
    }

    @Test
    public void testGetProduct() throws Exception {
        String url = "/groups/%d/products/%s";

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mProductWU.getUUID().toString())).request().get();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mProductWU.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").get();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(410, goneResponse.getStatus());

        Response okResponse1 = target(String.format(url, mGroup.getId(),
                mProductWU.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).get();
        assertEquals(200, okResponse1.getStatus());
        ProductInfo returnedProductInfo = okResponse1.readEntity(ProductInfo.class);
        assertNotNull(returnedProductInfo);
        assertEquals(mProduct.getUUID(), UUID.fromString(returnedProductInfo.getUUID()));
        assertEquals("product2", returnedProductInfo.getName());
        assertEquals(mProduct.getUpdated(), returnedProductInfo.getLastChanged().toInstant());
        assertNull(returnedProductInfo.getRemoveUnit());
        assertEquals(mUnit.getUUID(), UUID.fromString(returnedProductInfo.getUnitUUID()));
        assertEquals(2f, returnedProductInfo.getDefaultAmount(), 0.001f);
        assertEquals(2f, returnedProductInfo.getStepAmount(), 0.001f);
        assertFalse(returnedProductInfo.getDeleted());
    }

    @Test
    public void testPostProduct() throws Exception {
        String url = "/groups/%d/products";
        ProductInfo newProduct = new ProductInfo().withUUID(mProduct.getUUID()).
                withName("product4").withDefaultAmount(1.0f).withStepAmount(0.3f);
        Instant preInsert = Instant.now();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId())).request().
                post(Entity.json(newProduct));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                post(Entity.json(newProduct));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newProduct));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newProduct));
        assertEquals(409, goneResponse.getStatus());
        mManager.refresh(mProduct);
        assertEquals("unit1", mProduct.getName());

        newProduct.setUUID(UUID.randomUUID());
        Response okResponse = target(String.format(url, mGroup.getId())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).post(Entity.json(newProduct));
        assertEquals(201, okResponse.getStatus());
        TypedQuery<Product> savedProductsQuery = mManager.createQuery("select p from " +
                "Product p where p.group = :group and p.UUID = :uuid", Product.class);
        savedProductsQuery.setParameter("group", mGroup);
        savedProductsQuery.setParameter("uuid", UUID.fromString(newProduct.getUUID()));
        List<Product> savedProducts = savedProductsQuery.getResultList();
        assertEquals(1, savedProducts.size());
        assertEquals("product4", savedProducts.get(0).getName());
        assertTrue(preInsert.isBefore(savedProducts.get(0).getUpdated()));
    }

    @Test
    public void testPutProduct() throws Exception {
        String url = "/groups/%d/products/%s";
        Instant preUpdate = mProduct.getUpdated();
        ProductInfo updatedProduct = new ProductInfo().withDeleted(false).withName("changedprod");

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().put(Entity.json(updatedProduct));
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").
                put(Entity.json(updatedProduct));
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedProduct));
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedProduct));
        assertEquals(404, wrongListResponse.getStatus());

        Response goneResponse = target(String.format(url, mGroup.getId(),
                mDeletedProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedProduct));
        assertEquals(410, goneResponse.getStatus());
        mManager.refresh(mProduct);
        assertEquals("product1", mProduct.getName());

        updatedProduct.setLastChanged(new Date(preUpdate.toEpochMilli() - 10000));
        Response conflictResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedProduct));
        assertEquals(409, conflictResponse.getStatus());
        mManager.refresh(mProduct);
        assertEquals("product1", mProduct.getName());

        updatedProduct.setLastChanged(Date.from(Instant.now()));
        Response okResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).
                put(Entity.json(updatedProduct));
        assertEquals(200, okResponse.getStatus());
        mManager.refresh(mProduct);
        assertEquals("changedproduct", mProduct.getName());
        assertEquals(1f, mProduct.getDefaultAmount(), 0.001f);
        assertEquals(0.5f, mProduct.getStepAmount(), 0.001f);
        assertNull(mProduct.getUnit());
        assertTrue(preUpdate + " is not before " + mProduct.getUpdated(),
                preUpdate.isBefore(mProduct.getUpdated()));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        String url = "/groups/%d/products/%s";
        Instant preDelete = mProduct.getUpdated();

        Response notAuthorizedResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().delete();
        assertEquals(401, notAuthorizedResponse.getStatus());

        Response wrongAuthResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token wrongauth").delete();
        assertEquals(401, wrongAuthResponse.getStatus());

        Response wrongGroupResponse = target(String.format(url, mNAGroup.getId(),
                mNAProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(401, wrongGroupResponse.getStatus());

        Response wrongListResponse = target(String.format(url, mGroup.getId(),
                mNAProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(404, wrongListResponse.getStatus());

        Response okResponse = target(String.format(url, mGroup.getId(),
                mProduct.getUUID().toString())).request().
                header(HttpHeaders.AUTHORIZATION, "X-Token " + mToken).delete();
        assertEquals(200, okResponse.getStatus());

        TypedQuery<Product> savedUnitQuery = mManager.createQuery("select p from Product p where " +
                "p.group = :group and p.UUID = :uuid", Product.class);
        savedUnitQuery.setParameter("group", mGroup);
        savedUnitQuery.setParameter("uuid", mProduct.getUUID());
        List<Product> savedProducts = savedUnitQuery.getResultList();
        assertEquals(0, savedProducts.size());
        TypedQuery<DeletedObject> savedDeletedProductQuery = mManager.createQuery("select do " +
                "from DeletedObject do where do.group = :group and do.UUID = :uuid and " +
                "do.type = :type", DeletedObject.class);
        savedDeletedProductQuery.setParameter("group", mGroup);
        savedDeletedProductQuery.setParameter("uuid", mProduct.getUUID());
        savedDeletedProductQuery.setParameter("type", DeletedObject.Type.PRODUCT);
        List<DeletedObject> savedDeletedUnits = savedDeletedProductQuery.getResultList();
        assertEquals(1, savedDeletedUnits.size());
        assertTrue(preDelete.isBefore(savedDeletedUnits.get(0).getTime().toInstant()));
    }
}
