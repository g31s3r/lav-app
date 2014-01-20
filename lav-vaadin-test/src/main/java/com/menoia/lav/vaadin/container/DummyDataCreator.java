package com.menoia.lav.vaadin.container;

import java.math.BigDecimal;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menoia.lav.vaadin.entity.Brand;
import com.menoia.lav.vaadin.entity.Customer;
import com.menoia.lav.vaadin.entity.Process;
import com.menoia.lav.vaadin.entity.User;

import enterpriseapp.hibernate.DefaultHbnContainer;

public class DummyDataCreator {

    private static Logger LOGGER = LoggerFactory.getLogger(DummyDataCreator.class);

    public static void bootstrap() {

        // if there are no users in the database, create one
        UserContainer uc = (UserContainer) LavanderiaContainerFactory.getInstance().getContainer(User.class);

        if (uc.count() == 0) {
            LOGGER.info("Creating admin user...");
            User user = new User();
            user.setLogin("admin");
            user.setPassword("admin");
            user.setConfigurationAccess(true);
            user.setModifyAccess(true);
            user.setReportsAccess(true);
            uc.saveEntity(user);
        }

        // createNonExistentCustomers();
        // createNonExistentProcesses();
    }

    @SuppressWarnings("unchecked")
    public static void createNonExistentProcesses() {

        Object[][] processes =
            { { "Amaciado", new BigDecimal("1.10") }, { "Destroyed", new BigDecimal("1.70") },
                { "Alvejamento em PT", new BigDecimal("2.10") }, { "Hiper clear", new BigDecimal("2.20") },
                { "Sujinho", new BigDecimal("2.50") }, { "Super stone", new BigDecimal("1.50") },
                { "Bigode lixado", new BigDecimal("0.9") }, { "Lixado local / total", new BigDecimal("1.40") },
                { "Used local / total", new BigDecimal("1.30") }, { "Puidos diferenciados", new BigDecimal("1.20") },
                { "Tingimento PT", new BigDecimal("3.50") } };

        DefaultHbnContainer<com.menoia.lav.vaadin.entity.Process> container =
            LavanderiaContainerFactory.getInstance().getContainer(Process.class);

        for (Object[] process : processes) {

            Process p = new Process();
            p.setName((String) process[0]);
            p.setPrice((java.math.BigDecimal) process[1]);
            container.saveEntity(p);
        }
    }

    public static void createNonExistentBrands() {

        String[] brandsNames =
            { "Loors", "Le Réve", "Área Restrita", "Aion", "Eghuz", "Aconchego", "Karapalida", "Ou" };

        BrandContainer bc = (BrandContainer) LavanderiaContainerFactory.getInstance().getContainer(Brand.class);

        for (String brandName : brandsNames) {

            Brand brand = bc.getByName(brandName);
            if (brand == null) {
                brand = new Brand();
                brand.setName(brandName);
                bc.saveEntity(brand);
            }
        }
    }

    public static void createNonExistentCustomers() {

        String[] customers =
            { "Confeccções Loors Ltda", "Aion Confecções Ltda", "R. Ruwer Ltda", "Ou Confecções Ltda" };

        String[][] brandsNames =
            { { "Loors", "Le Réve" }, { "Área Restrita", "Aion" }, { "Eghuz" }, { "Karapalida", "Ou" } };

        String[] cities = { "São Jorge do Patrocínio, PR", "Campo Mourão, PR", "Umuarama, PR", "Cianorte, PR" };

        CustomerContainer dc =
            (CustomerContainer) LavanderiaContainerFactory.getInstance().getContainer(Customer.class);

        int i = 0;
        for (String name : customers) {
            Customer customer = dc.getByName(name);

            if (customer == null) {
                customer = new Customer();
                customer.setName(name);
                customer.setCity(cities[i]);
                customer.setBrands(new HashSet<Brand>());

                for (String strings : brandsNames[i]) {
                    Brand brand = new Brand();
                    brand.setName(strings);
                    customer.getBrands().add(brand);
                }

                dc.saveEntity(customer);
            }
            i++;
        }
    }

}
