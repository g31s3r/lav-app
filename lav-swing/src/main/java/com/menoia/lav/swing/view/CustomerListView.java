package com.menoia.lav.swing.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.guts.event.Channel;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.DefaultEventTableModel;

import com.menoia.lav.server.entity.Customer;

@Singleton
public class CustomerListView extends JPanel {

    private static final long serialVersionUID = 7068262166438989381L;

    private final EventList<Customer> customers;
    private final JTable table = new JTable();

    @Inject
    public CustomerListView(final Channel<Customer> selectedCustomerChannel) {

        setLayout(new BorderLayout());
        // this.customers = GlazedLists.eventList(dao.findAll());
        this.customers = GlazedLists.eventList(createTestCustomers());

        String[] properties = { "name", "brand", "location" };
        TableFormat<Customer> format = GlazedLists.tableFormat(Customer.class, properties, properties);

        final DefaultEventTableModel<Customer> model = new DefaultEventTableModel<Customer>(this.customers, format);

        this.table.setModel(model);

        this.table.setSelectionMode(0);
        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            private int lastSelection = -1;

            public void valueChanged(ListSelectionEvent event) {

                int selected = CustomerListView.this.table.getSelectedRow();
                if (selected != this.lastSelection) {
                    Customer row = null;
                    if (selected > -1) {
                        row = (Customer) model.getElementAt(selected);
                    }
                    this.lastSelection = selected;
                    selectedCustomerChannel.publish(row);
                }
            }
        });
        add(new JScrollPane(this.table));
    }

    private List<Customer> createTestCustomers() {

        List<Customer> list = new ArrayList<>();

        Customer cust = new Customer();
        cust.setId(1L);
        cust.setBrand("767 JEANS");
        cust.setName("A. F. FELIPE CONFECCOES");
        cust.setLocation("PEROLA PR, BRASIL");
        list.add(cust);

        Customer cust2 = new Customer();
        cust2.setId(2L);
        cust2.setBrand("LOORS");
        cust2.setName("CONFECCOES LOORS");
        cust2.setLocation("SAO JORGE DO PATROCINIO PR, BRASIL");
        list.add(cust2);

        Customer cust3 = new Customer();
        cust3.setId(3L);
        cust3.setBrand("LOORS");
        cust3.setName("WR INDUSTRIA VESTUARIO");
        cust3.setLocation("SAO JORGE DO PATROCINIO PR, BRASIL");
        list.add(cust3);

        return list;
    }
}
