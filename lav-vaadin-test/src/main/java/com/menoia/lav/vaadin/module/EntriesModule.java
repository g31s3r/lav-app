package com.menoia.lav.vaadin.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.menoia.lav.vaadin.crud.LavanderiaCrud;
import com.menoia.lav.vaadin.entity.Brand;
import com.menoia.lav.vaadin.entity.Customer;
import com.menoia.lav.vaadin.entity.Prototype;
import com.menoia.lav.vaadin.entity.Sale;
import com.menoia.lav.vaadin.entity.User;
import com.menoia.lav.vaadin.ui.Constants;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import enterpriseapp.ui.crud.CrudComponent;
import enterpriseapp.ui.window.MDIWindow;
import enterpriseapp.ui.window.Module;

public class EntriesModule implements Module, Command {

    private static final long serialVersionUID = 1L;

    private static Logger LOGGER = LoggerFactory.getLogger(EntriesModule.class);

    private MDIWindow mdiWindow;

    private MenuItem customersItem;
    private MenuItem brandsItem;
    private MenuItem salesItem;
    private MenuItem prototypeItem;
    private MenuItem processItem;

    private User user;

    @Override
    public void init() {

        // not used
    }

    @Override
    public boolean userCanAccess(enterpriseapp.hibernate.dto.User user) {

        this.user = (User) user;
        // we must return true if the given user has access to this module
        return this.user.isReadAccess() || this.user.isModifyAccess();
    }

    @Override
    public void add(MDIWindow mdiWindow, enterpriseapp.hibernate.dto.User user) {

        this.mdiWindow = mdiWindow;
        // save reference for further use
        // this will be called when the module is added to the window
        // first, we get the MDI window menu bar, then we add some items to it
        MenuItem ab = mdiWindow.getMenuBar().addItem(Constants.uiEntries, null);
        customersItem = ab.addItem(Constants.uiCustomers, this);
        brandsItem = ab.addItem(Constants.uiBrands, this);
        salesItem = ab.addItem(Constants.uiSales, this);
        prototypeItem = ab.addItem(Constants.uiPrototypes, this);
        processItem = ab.addItem(Constants.uiProcesses, this);
    }

    @Override
    public void menuSelected(MenuItem selectedItem) {

        if (customersItem.equals(selectedItem)) {
            LOGGER.info("People menu item selected.");
            // "People" menu item is selected
            // let's create a CRUD with the helper class CrudBuilder (you can
            // also instantiate a crud directly)
            CrudComponent<Customer> crud =
                new LavanderiaCrud<>(Customer.class, user.isModifyAccess() && !Constants.dbUseCloudFoundryDatabase);

            // now, we add it to the MDI window
            mdiWindow.addWorkbenchContent(crud, Constants.uiCustomers, null, true, true);
        } else if (brandsItem.equals(selectedItem)) {
            LOGGER.info("Departments menu item selected.");
            // "Departments" menu item is selected
            // let's create a CRUD instantiating it directly
            CrudComponent<Brand> crud =
                new LavanderiaCrud<>(Brand.class, user.isModifyAccess() && !Constants.dbUseCloudFoundryDatabase);

            // now, we add it to the MDI window
            mdiWindow.addWorkbenchContent(crud, Constants.uiBrands, null, true, false);
        } else if (prototypeItem.equals(selectedItem)) {
            openPrototype();
        } else if (processItem.equals(selectedItem)) {
            LOGGER.info("Processes menu item selected.");
            // "Departments" menu item is selected
            // let's create a CRUD instantiating it directly
            CrudComponent<com.menoia.lav.vaadin.entity.Process> crud =
                new LavanderiaCrud<>(com.menoia.lav.vaadin.entity.Process.class, user.isModifyAccess()
                    && !Constants.dbUseCloudFoundryDatabase);

            // now, we add it to the MDI window
            mdiWindow.addWorkbenchContent(crud, Constants.uiProcesses, null, true, false);
        } else if (salesItem.equals(selectedItem)) {
            LOGGER.info("Sales menu item selected.");

            CrudComponent<Sale> crud =
                new LavanderiaCrud<>(Sale.class, user.isModifyAccess() && !Constants.dbUseCloudFoundryDatabase);

            // now, we add it to the MDI window
            mdiWindow.addWorkbenchContent(crud, Constants.uiSales, null, true, false);
        }
    }

    private void openPrototype() {

        LOGGER.info("Prototypes menu item selected.");
        // "Departments" menu item is selected
        // let's create a CRUD instantiating it directly
        CrudComponent<Prototype> crud =
            new LavanderiaCrud<>(Prototype.class, user.isModifyAccess() && !Constants.dbUseCloudFoundryDatabase);

        mdiWindow.addWorkbenchContent(crud, Constants.uiPrototypes, null, true, false);
    }

}
