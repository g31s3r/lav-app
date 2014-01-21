package com.menoia.lav.vaadin.report;

public class LavanderiaReportColumn implements Comparable<LavanderiaReportColumn> {

    private final Integer index;
    private final String columnProperty;
    private final String reportProperty;
    private final String columnTitle;
    private final Class<?> columnClass;

    public LavanderiaReportColumn(Integer index, String columnProperty, String columnTitle, Class<?> columnClass) {

        this(index, columnProperty, columnTitle, columnClass, columnProperty);
    }

    public LavanderiaReportColumn(Integer index, String columnProperty, String columnTitle, Class<?> columnClass,
        String reportProperty) {

        this.index = index;
        this.columnProperty = columnProperty;
        this.columnTitle = columnTitle;
        this.columnClass = columnClass;
        this.reportProperty = reportProperty;
    }

    public Integer getIndex() {

        return index;
    }

    public String getColumnProperty() {

        return columnProperty;
    }

    public String getColumnTitle() {

        return columnTitle;
    }

    public Class<?> getColumnClass() {

        return columnClass;
    }

    public String getReportProperty() {

        return reportProperty;
    }

    @Override
    public int compareTo(LavanderiaReportColumn o) {

        return this.index.compareTo(o.getIndex());
    }

}
