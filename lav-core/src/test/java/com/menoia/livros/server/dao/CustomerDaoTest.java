package com.menoia.livros.server.dao;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.persist.Transactional;
import com.menoia.lav.server.dao.CustomerDao;
import com.menoia.lav.server.entity.Customer;
import com.menoia.lav.server.injector.PersistenceModule;
import com.menoia.livros.server.injector.GuiceTestRunner;
import com.menoia.livros.server.injector.WithModules;

@RunWith(GuiceTestRunner.class)
@WithModules(PersistenceModule.class)
public class CustomerDaoTest {

    private CustomerDao dao;

    @Inject
    public CustomerDaoTest(CustomerDao dao) {

        this.dao = dao;
    }

    @Test
    @Transactional
    public void save() {

        Customer cust = new Customer();
        cust.setBrand("767 JEANS");
        cust.setName("A. F. FELIPE CONFECCOES");
        cust.setLocation("PEROLA PR, BRASIL");

        dao.save(cust);
        Assert.assertNotNull(cust.getId());
    }

    @Test
    @Transactional
    public void remove() {

        Customer cust = new Customer();
        cust.setBrand("767 JEANS");
        cust.setName("A. F. FELIPE CONFECCOES");
        cust.setLocation("PEROLA PR, BRASIL");
        
        dao.save(cust);
        dao.remove(cust.getId());
        Assert.assertNull(dao.get(cust.getId()));

    }

    @Test
    @Transactional
    public void update() {

        Customer cust = new Customer();
        cust.setBrand("767 JEANS");
        cust.setName("A. F. FELIPE CONFECCOES");
        cust.setLocation("PEROLA PR, BRASIL");
        
        dao.save(cust);
        cust.setBrand("DOCTOR JEANS");
        dao.update(cust);
        Customer cust2 = dao.get(cust.getId());
        Assert.assertEquals("DOCTOR JEANS", cust2.getBrand());
    }

    @Test
    @Transactional
    public void find() {

        Customer cust = new Customer();
        cust.setBrand("767 JEANS");
        cust.setName("A. F. FELIPE CONFECCOES");
        cust.setLocation("PEROLA PR, BRASIL");
        dao.save(cust);

        Customer cust2 = new Customer();
        cust.setBrand("LOORS");
        cust.setName("CONFECCOES LOORS");
        cust.setLocation("SAO JORGE DO PATROCINIO PR, BRASIL");
        dao.save(cust2);

        Customer cust3 = new Customer();
        cust.setBrand("LOORS");
        cust.setName("WR INDUSTRIA VESTUARIO");
        cust.setLocation("SAO JORGE DO PATROCINIO PR, BRASIL");
        dao.save(cust3);

        List<Customer> list = dao.findAll();
        Assert.assertEquals(3, list.size());
        Assert.assertTrue(list.contains(cust));
        Assert.assertTrue(list.contains(cust2));
        Assert.assertTrue(list.contains(cust3));

        Long count = dao.countAll();
        Assert.assertEquals(new Long(3), count);
    }

}
