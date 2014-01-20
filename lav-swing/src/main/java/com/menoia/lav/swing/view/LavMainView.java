package com.menoia.lav.swing.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LavMainView extends JPanel {

    private static final long serialVersionUID = 597861979604528572L;

    @Inject
    public LavMainView(CustomerListView list, CustomerDetailView detail) {

        setLayout(new BorderLayout());
        JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        mainSplit.setTopComponent(list);
        mainSplit.setBottomComponent(detail);
        add(mainSplit);
    }
}
