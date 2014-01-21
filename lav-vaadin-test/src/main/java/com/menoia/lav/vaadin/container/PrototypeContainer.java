package com.menoia.lav.vaadin.container;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.menoia.lav.vaadin.entity.Prototype;
import com.menoia.lav.vaadin.entity.Sale;
import com.menoia.lav.vaadin.entity.Status;

import enterpriseapp.hibernate.DefaultHbnContainer;

@SuppressWarnings("unchecked")
public class PrototypeContainer extends DefaultHbnContainer<Prototype> {

    private static final long serialVersionUID = 1L;

    public PrototypeContainer() {

        super(Prototype.class);
    }

    public Prototype refresh(Prototype entity) {

        if (entity != null) {
            sessionManager.getSession().refresh(entity);
            // just to load processes
            entity.getProcesses();
        }
        return entity;
    }

    @Override
    public void afterSaveOrUpdate(Prototype entity) {

        SaleContainer saleContainer = (SaleContainer) LavanderiaContainerFactory.getInstance().getContainer(Sale.class);
        saleContainer.updateAllWithNewPrototype(entity);
    }

    @Override
    public void beforeSaveOrUpdate(Prototype entity) {

        if (entity.getStatus() == null) {
            entity.setStatus(Status.INPUT);
        }

        // Calculate suggested price
        BigDecimal suggested = BigDecimal.ZERO;
        for (com.menoia.lav.vaadin.entity.Process process : entity.getProcesses()) {
            suggested = suggested.add(process.getPrice());
        }
        entity.setSuggestedPrice(suggested);

        // Fill the leave date
        if (entity.getStatus() == Status.DELIVERED && entity.getLeave() == null) {
            entity.setLeave(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
        }
    }

}
