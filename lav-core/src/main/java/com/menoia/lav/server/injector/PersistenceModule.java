package com.menoia.lav.server.injector;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.menoia.lav.server.dao.CustomerDao;

/**
 * Private Persistent Module to connect with database unit.
 * 
 * @author gmenoia
 */
public class PersistenceModule extends AbstractModule {

    private static final String PERSISTENCE_UNIT = "lav";

    @Override
    protected void configure() {

        // Create module and load properties
        install(new JpaPersistModule(PERSISTENCE_UNIT));

        // Start the persistence service
        bind(JpaInitializer.class).asEagerSingleton();
        bind(CustomerDao.class);
    }

}
