package com.menoia.lav.server.injector;

import javax.inject.Inject;

import com.google.inject.persist.PersistService;

public class JpaInitializer {

    @Inject
    public JpaInitializer(final PersistService service) {

        service.start();
    }
}
