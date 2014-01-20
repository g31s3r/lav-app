package com.menoia.lav.swing.util;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProgressCellRenderer extends JProgressBar implements TableCellRenderer {

    private static final long serialVersionUID = 5951519482563749014L;

    public ProgressCellRenderer() {

        setMinimum(0);
        setMaximum(100);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        int progress = ((Integer) value).intValue();
        progress = Math.max(0, progress);
        progress = Math.min(100, progress);
        setValue(progress);
        return this;
    }
}
