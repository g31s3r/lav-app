package com.menoia.lav.swing.view;

import javax.inject.Singleton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.guts.event.Consumes;
import net.java.dev.designgridlayout.DesignGridLayout;

import com.menoia.lav.server.entity.Customer;

@Singleton
public class CustomerDetailView extends JPanel {

    private static final long serialVersionUID = -1436540113538430985L;

    private final JLabel lblName = new JLabel();
    private final JTextField txfName = new JTextField();
    private final JLabel lblBrand = new JLabel();
    private final JTextField txfBrand = new JTextField();
    private final JLabel lblLocation = new JLabel();
    private final JTextField txfLocation = new JTextField();

    public CustomerDetailView() {

        this.txfName.setEditable(false);
        this.txfBrand.setEditable(false);
        this.txfLocation.setEditable(false);

        DesignGridLayout layout = new DesignGridLayout(this);
        layout.row().grid(this.lblName).add(new JComponent[] { this.txfName });
        layout.row().grid(this.lblBrand).add(new JComponent[] { this.txfBrand });
        layout.row().grid(this.lblLocation).add(new JComponent[] { this.txfLocation });
        layout.margins(0.5D);
    }

    @Consumes
    public void onEvent(Customer selected) {

        if (selected != null) {
            this.txfName.setText(selected.getName());
            this.txfBrand.setText(selected.getBrand());
            this.txfLocation.setText(selected.getLocation());
        } else {
            this.txfName.setText("");
            this.txfBrand.setText("");
            this.txfLocation.setText("");
        }
    }
}
