package com.menoia.lav.vaadin;

import java.util.ArrayList;

import com.menoia.lav.vaadin.container.DummyDataCreator;
import com.menoia.lav.vaadin.container.LavanderiaContainerFactory;
import com.menoia.lav.vaadin.job.DummyDataCreationJob;
import com.menoia.lav.vaadin.module.ConfigurationModule;
import com.menoia.lav.vaadin.module.EntriesModule;
import com.menoia.lav.vaadin.module.ReportsModule;
import com.menoia.lav.vaadin.ui.Constants;
import com.menoia.lav.vaadin.ui.LoginWindow;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

import enterpriseapp.EnterpriseApplication;
import enterpriseapp.hibernate.ContainerFactory;
import enterpriseapp.hibernate.dto.User;
import enterpriseapp.ui.window.MDIWindow;
import enterpriseapp.ui.window.Module;

public class LavanderiaApplication extends EnterpriseApplication {

    private static final long serialVersionUID = 1L;

    private static boolean firstInit = true;

    @Override
    public void init() {

        super.init();

        if (firstInit) {
            firstInit = false;

            // we will use a custom ContainerFactory
            ContainerFactory.init(new LavanderiaContainerFactory());

            // create dummy data if necesary
            DummyDataCreator.bootstrap();

            // Schedule dummy data creation job
            DummyDataCreationJob.schedule();
        }

        // show content according to the state of getUser()
        if (getUser() == null) {
            showPublicContent();
        } else {
            showPrivateContent();
        }
    }

    private void showPublicContent() {

        removeWindow(getMainWindow());

        // we are gonna create an empty window and add a new LoginWindow to it
        LoginWindow emptyMainWindow = new LoginWindow();
        Window mainWindow = new Window(Constants.uiAppName);

        mainWindow.addWindow(emptyMainWindow);
        setMainWindow(mainWindow);
    }

    private void showPrivateContent() {

        removeWindow(getMainWindow());

        // we will use the Enterprise Application modules feature that allows us
        // to implement rol based access per module
        ArrayList<Module> modules = getModules();

        // now, we create an instance of MDIWindow, initialize its content and
        // set it to be the main window for the app
        MDIWindow mainWindow = new MDIWindow(Constants.uiAppName, modules);
        mainWindow.initWorkbenchContent((User) getUser(), null);
        setMainWindow(mainWindow);

        // we need to add a welcome tab
        Label label = new Label(Constants.uiWelcomeMessage, Label.CONTENT_XHTML);
        label.setSizeFull();
        Panel panel = new Panel();
        panel.setSizeFull();
        panel.addComponent(label);
        mainWindow.addWorkbenchContent(panel, Constants.uiWelcome, null, false, false);

        // finally, let's add a "close session" option
        mainWindow.getMenuBar().addItem(Constants.uiLogout, new MenuBar.Command() {

            private static final long serialVersionUID = 1L;

            @Override
            public void menuSelected(MenuItem selectedItem) {

                setUser(null); // remove user from session
                init(); // reload app
            }
        });
    }

    private ArrayList<Module> getModules() {

        // let's define the modules
        ConfigurationModule configurationModule = new ConfigurationModule();
        EntriesModule entriesModule = new EntriesModule();
        ReportsModule reportsModule = new ReportsModule();

        // add them to a new ArrayList so we can pass them to the MDIWindow
        // constructor
        ArrayList<Module> modules = new ArrayList<Module>();
        modules.add(configurationModule);
        modules.add(entriesModule);
        modules.add(reportsModule);

        return modules;
    }
}
