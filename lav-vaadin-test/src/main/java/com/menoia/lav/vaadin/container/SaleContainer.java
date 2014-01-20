package com.menoia.lav.vaadin.container;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Order;

import com.menoia.lav.vaadin.entity.Customer;
import com.menoia.lav.vaadin.entity.Sale;
import com.menoia.lav.vaadin.entity.Status;

import enterpriseapp.hibernate.DefaultHbnContainer;

@SuppressWarnings("unchecked")
public class SaleContainer extends DefaultHbnContainer<Sale> {

    private static final int DAYS_TO_PAY = 30;
    private static final long serialVersionUID = 1L;

    public SaleContainer() {

        super(Sale.class);
    }

    public Collection<?> getCallReportData(Customer customer, String[] properties, Class<?>[] classes) {

        List<?> result;
        if (customer != null) {
            result =
                specialQuery(
                    "select date, prototype.description, prototype.unitPrice, quantity, prototype.unitPrice * quantity, status from Sale where prototype.customer = ?",
                    new Object[] { customer });
        } else {
            result =
                specialQuery("select date, prototype.description, prototype.unitPrice, quantity, prototype.unitPrice * quantity, status from Sale");
        }
        return parseSpecialQueryResult(result, properties, classes);
    }

    @Override
    public void beforeSaveOrUpdate(Sale entity) {

        // Fill the delivered date
        if (entity.getStatus() == Status.DELIVERED && entity.getDelivered() == null) {
            entity.setDelivered(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
        }
        
        // Set billing date if delivered is not null
        if (entity.getDelivered() != null && entity.getBilling() == null) {
            entity.setBilling(DateUtils.addDays(entity.getDelivered(), DAYS_TO_PAY));
        }
        
    }

    @Override
    protected Order getNaturalOrder(boolean flipOrder) {

        if (!flipOrder) {
            return Order.desc("date");
        } else {
            return Order.asc("date");
        }
    }

    @Override
    protected DynaBean objectArrayToBean(Object[] values, String[] properties, Class<?>[] classes) {

        DynaBean dynaBean = null;

        try {
            DynaProperty[] columnsDynaProperties = getColumnsDynaProperties(properties, classes);
            BasicDynaClass clazz =
                new BasicDynaClass(this.getClass().getSimpleName(), BasicDynaBean.class, columnsDynaProperties);
            dynaBean = clazz.newInstance();

            for (int i = 0; i < columnsDynaProperties.length; i++) {

                Object value = values[i];
                if (value != null && value.getClass().isEnum()) {
                    value = value.toString();
                }

                dynaBean.set(columnsDynaProperties[i].getName(), value);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error creating dynamic bean", e);
        }

        return dynaBean;
    }

}
