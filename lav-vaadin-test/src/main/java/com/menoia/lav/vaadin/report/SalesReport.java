package com.menoia.lav.vaadin.report;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import com.menoia.lav.vaadin.container.LavanderiaContainerFactory;
import com.menoia.lav.vaadin.container.SaleContainer;
import com.menoia.lav.vaadin.entity.Customer;
import com.menoia.lav.vaadin.entity.Sale;
import com.menoia.lav.vaadin.entity.Status;
import com.menoia.lav.vaadin.field.EnumField;
import com.menoia.lav.vaadin.ui.Constants;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import enterpriseapp.Utils;
import enterpriseapp.ui.crud.EntityField;

public class SalesReport extends LavanderiaReport {

    private static final long serialVersionUID = 1L;

    private EntityField customerField;
    private EnumField statusField;
    private DateField fromField;
    private DateField toField;

    @Override
    public SortedSet<LavanderiaReportColumn> getReportColumns() {

        SortedSet<LavanderiaReportColumn> set = new TreeSet<>();
        set.add(new LavanderiaReportColumn(1, "name", Utils.getPropertyLabel("prototype", "customer"), String.class));
        set.add(new LavanderiaReportColumn(2, "date", Utils.getPropertyLabel("sale", "date"), Date.class));
        set.add(new LavanderiaReportColumn(3, "delivered", Utils.getPropertyLabel("sale", "delivered"), Date.class));
        set.add(new LavanderiaReportColumn(4, "billing", Utils.getPropertyLabel("sale", "billing"), Date.class));
        set.add(new LavanderiaReportColumn(5, "seal", Utils.getPropertyLabel("prototype", "seal"), Long.class));
        set.add(new LavanderiaReportColumn(6, "unitPrice", Utils.getPropertyLabel("prototype", "unitPrice"),
            BigDecimal.class));
        set.add(new LavanderiaReportColumn(7, "quantity", Utils.getPropertyLabel("sale", "quantity"), Long.class));
        set.add(new LavanderiaReportColumn(8, "total", Utils.getPropertyLabel("sale", "total"), BigDecimal.class));
        set.add(new LavanderiaReportColumn(9, "status", Utils.getPropertyLabel("sale", "status"), String.class));
        return set;
    }

    @Override
    public Collection<?> getData() {

        Customer customer = (Customer) customerField.getValue();
        Status status = (Status) statusField.getValue();
        Date from = (Date) fromField.getValue();
        Date to = (Date) toField.getValue();

        SaleContainer saleContainer = (SaleContainer) LavanderiaContainerFactory.getInstance().getContainer(Sale.class);
        return saleContainer.getCallReportData(customer, status, from, to, getColumnProperties(), getColumnClasses());
    }

    @Override
    public Component getParametersComponent() {

        customerField =
            new EntityField(Customer.class, LavanderiaContainerFactory.getInstance().getContainer(Customer.class));
        customerField.setCaption(Constants.uiCustomer);
        customerField.setImmediate(true);

        statusField = new EnumField(Status.class);
        statusField.setCaption(Constants.uiStatus);
        statusField.setImmediate(true);

        fromField = new DateField(Utils.getPropertyLabel("sale", "date"));
        fromField.setResolution(DateField.RESOLUTION_DAY);
        toField = new DateField(Utils.getPropertyLabel("sale", "date"));
        toField.setResolution(DateField.RESOLUTION_DAY);

        HorizontalLayout dateLayout = new HorizontalLayout();
        dateLayout.setSpacing(true);
        dateLayout.setSizeFull();
        
        dateLayout.addComponent(statusField);
        dateLayout.setExpandRatio(statusField, 0.6f);
        dateLayout.addComponent(fromField);
        dateLayout.addComponent(toField);

        VerticalLayout component = new VerticalLayout();
        component.setSpacing(true);
        component.setSizeFull();
        component.addComponent(customerField);
        component.addComponent(dateLayout);

        return component;
    }

    @Override
    public String getTitle() {

        String title = super.getTitle();

        if (customerField.getValue() != null) {
            title += " - " + customerField.getValue().toString();
        }

        return title;
    }
}
