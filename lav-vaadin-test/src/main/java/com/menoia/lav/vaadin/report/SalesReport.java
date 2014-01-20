package com.menoia.lav.vaadin.report;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;

import com.menoia.lav.vaadin.container.LavanderiaContainerFactory;
import com.menoia.lav.vaadin.container.SaleContainer;
import com.menoia.lav.vaadin.entity.Customer;
import com.menoia.lav.vaadin.entity.Sale;
import com.menoia.lav.vaadin.ui.Constants;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

import enterpriseapp.Utils;
import enterpriseapp.ui.crud.EntityField;
import enterpriseapp.ui.reports.PrintViewReport;

/**
 * A simple report with all the entries in the address book.
 * 
 * @author Alejandro Duarte
 * 
 */
public class SalesReport extends PrintViewReport {

    private static final long serialVersionUID = 1L;

    private EntityField customerField;

    @Override
    public String[] getColumnProperties() {

        return new String[] { "date", "processes", "unitPrice", "quantity", "total", "status" };
    }

    @Override
    public Class<?>[] getColumnClasses() {

        return new Class<?>[] { Date.class, String.class, BigDecimal.class, Long.class, BigDecimal.class, String.class };
    }

    @Override
    public String[] getColumnTitles() {

        return new String[] { Utils.getPropertyLabel("sale", "date"), Utils.getPropertyLabel("prototype", "processes"),
            Utils.getPropertyLabel("prototype", "unitPrice"), Utils.getPropertyLabel("sale", "quantity"),
            Utils.getPropertyLabel("sale", "total"), Utils.getPropertyLabel("sale", "status") };
    }

    @Override
    public Collection<?> getData() {

        // here we need to return all the data to show in the report
        // this will be called when the "Refresh" button is clicked
        SaleContainer saleContainer = (SaleContainer) LavanderiaContainerFactory.getInstance().getContainer(Sale.class);
        return saleContainer.getCallReportData((Customer) customerField.getValue(), getColumnProperties(),
            getColumnClasses());
    }

    @Override
    public Component getParametersComponent() {

        // we will add a component into the "Configuration" section
        customerField =
            new EntityField(Customer.class, LavanderiaContainerFactory.getInstance().getContainer(Customer.class));
        customerField.setCaption(Constants.uiCustomer);
        customerField.setImmediate(true);

        VerticalLayout component = new VerticalLayout();
        component.addComponent(customerField);

        Button btn = new Button("PDF");
        btn.addListener(new ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                exportToPdf();
            }
        });
        component.addComponent(btn);

        return component;
    }

    @Override
    public DynamicReportBuilder getReportBuilder() {

        // let's override this method to add some elements to the report
        DynamicReportBuilder reportBuilder = super.getReportBuilder();

        Style headerStyle = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM).build();

        reportBuilder.addAutoText(Constants.uiForInternalUseOnly, AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT,
            200, headerStyle);
        reportBuilder.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT,
            200, 10, headerStyle);
        reportBuilder.addAutoText(Utils.getCurrentTimeAndDate(), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT,
            200, headerStyle);

        Style titleStyle =
            new StyleBuilder(true).setPadding(0).setFont(Font.ARIAL_BIG_BOLD)
                .setHorizontalAlign(HorizontalAlign.CENTER).build();
        reportBuilder.setTitleStyle(titleStyle);
        reportBuilder.setTitleHeight(18);
        String title = Constants.uiSalesReport;

        if (customerField.getValue() != null) {
            title += " - " + customerField.getValue();
        }

        reportBuilder.setTitle(title);

        Style footerStyle = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM).setTextColor(Color.GRAY).build();
        reportBuilder.addAutoText("Powered by Enterprise App for Vaadin", AutoText.POSITION_FOOTER,
            AutoText.ALIGMENT_LEFT, 200, footerStyle);

        return reportBuilder;
    }
}
