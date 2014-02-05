package com.menoia.lav.vaadin.container;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.menoia.lav.vaadin.entity.Prototype;
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

    public List<Sale> findByPrototype(Prototype prototype) {

        return query("from Sale where prototype = ?", new Object[] { prototype });
    }

    public void updateAllWithNewPrototype(Prototype prototype) {

        List<Sale> sales = findByPrototype(prototype);
        for (Sale sale : sales) {
            saveOrUpdateEntity(sale);
        }
    }

    public Collection<?> getCallReportData(Customer customer, Status status, Date from, Date to, String[] properties, Class<?>[] classes) {

        List<Object> filters = new ArrayList<>();
        
        StringBuilder sb =new StringBuilder();
        sb.append("select customer.name, date, delivered, billing, prototype.seal, unitPrice, quantity, total, status from Sale where 1=1 ");
        
        if (customer != null) {
            sb.append("and customer = ? ");
            filters.add(customer);
        }
        
        if(status != null) {
            sb.append("and status = ? ");
            filters.add(status);
        }
        
        if(from != null) {
            sb.append("and date >= ? ");
            filters.add(from);
        }
        
        if(to != null) {
            sb.append("and date <= ? ");
            filters.add(to);
        }
        
        sb.append("order by date, prototype.seal");
        
        List<?> result = specialQuery(sb.toString(), filters.toArray());
        
        return parseSpecialQueryResult(result, properties, classes);
    }

    @Override
    public void beforeSaveOrUpdate(Sale entity) {

        // Set customer, total, and unitprice based on prototype
        if (entity.getPrototype() != null) {
            entity.setCustomer(entity.getPrototype().getCustomer());
            entity.setUnitPrice(entity.getPrototype().getUnitPrice());
            entity.setTotal(entity.getPrototype().getUnitPrice()
                .multiply(new BigDecimal(entity.getQuantity().toString())));
        }

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
