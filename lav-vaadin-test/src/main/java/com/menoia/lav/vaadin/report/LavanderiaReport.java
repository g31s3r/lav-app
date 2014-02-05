package com.menoia.lav.vaadin.report;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;

import com.menoia.lav.vaadin.ui.Constants;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.Content;
import com.vaadin.ui.VerticalLayout;

import enterpriseapp.Utils;
import enterpriseapp.ui.reports.PrintViewReport;

/**
 * A simple report with all the entries in the address book.
 * 
 * @author Alejandro Duarte
 * 
 */
public abstract class LavanderiaReport extends PrintViewReport {

    private static final long serialVersionUID = 1L;

    private SortedSet<LavanderiaReportColumn> columns;
    private Button printButton;
    private PopupView columnsPopup;
    private PopupView groupingPopup;

    public abstract SortedSet<LavanderiaReportColumn> getReportColumns();

    @Override
    public void initLayout() {

        columns = getReportColumns();

        super.initLayout();

        printButton = new Button(Constants.uiPrint);
        printButton.addListener(this);

        displayLayout.removeAllComponents();
        displayLayout.addComponent(refreshButton);
        displayLayout.addComponent(printButton);
        
        // displayLayout.addComponent(createColumnsOptions());
        // displayLayout.addComponent(createGroupingOptions());
        // displayLayout.addComponent(createExportOptions());

        columnsPopup = new PopupView(new GroupingPopupContent(Constants.uiColumnsCount, columnsCheckBoxes));
        columnsPopup.setHideOnMouseOut(false);
        
        groupingPopup = new PopupView(new GroupingPopupContent(Constants.uiGroupingCount, groupingCheckBoxes));
        groupingPopup.setHideOnMouseOut(false);

        displayLayout.addComponent(columnsPopup);
        displayLayout.addComponent(groupingPopup);
    }

    @Override
    public void buttonClick(ClickEvent event) {

        super.buttonClick(event);

        if (event.getButton().equals(printButton)) {
            exportToPdf();
        }
    }

    @Override
    public String[] getColumnProperties() {

        List<String> list = new ArrayList<>();
        for (LavanderiaReportColumn reportColumn : columns) {
            list.add(reportColumn.getColumnProperty());
        }
        return list.toArray(new String[] {});
    }

    @Override
    public Class<?>[] getColumnClasses() {

        List<Class<?>> list = new ArrayList<>();
        for (LavanderiaReportColumn reportColumn : columns) {
            list.add(reportColumn.getColumnClass());
        }
        return list.toArray(new Class<?>[] {});
    }

    @Override
    public String[] getColumnTitles() {

        List<String> list = new ArrayList<>();
        for (LavanderiaReportColumn reportColumn : columns) {
            list.add(reportColumn.getColumnTitle());
        }
        return list.toArray(new String[] {});
    }

    @Override
    public DynamicReportBuilder getReportBuilder() {

        // let's override this method to add some elements to the report
        DynamicReportBuilder reportBuilder = super.getReportBuilder();

        // reportBuilder.addGlobalHeaderVariable(columnAmount,
        // ColumnsGroupVariableOperation.SUM);

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
        reportBuilder.setTitle(getTitle());

        Style footerStyle = new StyleBuilder(true).setFont(Font.ARIAL_MEDIUM).setTextColor(Color.GRAY).build();
        reportBuilder.addAutoText("Powered by Enterprise App for Vaadin", AutoText.POSITION_FOOTER,
            AutoText.ALIGMENT_LEFT, 200, footerStyle);

        return reportBuilder;
    }

    public String getTitle() {

        return Constants.uiSalesReport;
    }

    public class GroupingPopupContent implements Content {

        private static final long serialVersionUID = 1L;

        private String caption;
        private CheckBox[] checkboxes;

        public GroupingPopupContent(String caption, CheckBox[] checkboxes) {

            super();
            this.caption = caption;
            this.checkboxes = checkboxes;
        }

        @Override
        public String getMinimizedValueAsHTML() {

            List<String> values = new ArrayList<>();

            for (CheckBox check : checkboxes) {
                if (check.booleanValue()) {
                    values.add(check.getCaption());
                }
            }

            return String.format(caption, values.size());
        }

        @Override
        public Component getPopupComponent() {

            VerticalLayout groupingLayout = new VerticalLayout();
            groupingLayout.setSizeUndefined();
            groupingLayout.setSpacing(true);

            for (CheckBox box : checkboxes) {
                groupingLayout.addComponent((Component) box);
            }

            return groupingLayout;
        }

    }

}
