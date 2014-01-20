package com.menoia.lav.swing;

import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.guts.gui.application.AbstractApplication;
import net.guts.gui.message.MessageModule;
import net.guts.gui.naming.ComponentNamingModule;
import net.guts.gui.naming.DefaultComponentNamePolicy;

import com.google.inject.Module;
import com.menoia.lav.server.injector.PersistenceModule;

public class LavMain extends AbstractApplication {

    private static final long serialVersionUID = -6795634002610051026L;
    private static final String WINDOWS_LOOKFEEL_CLASS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    static {
        try {
            UIManager.setLookAndFeel(WINDOWS_LOOKFEEL_CLASS);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
            | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new LavMain().launch(args);
    }

    protected static class LavNamePolicy extends DefaultComponentNamePolicy {

        protected String separator() {

            return "";
        }
    }

    @Override
    protected void initModules(String[] args, List<Module> modules) {

        System.setProperty("sun.awt.noerasebackground", "true");

        modules.add(new PersistenceModule());
        modules.add(new MessageModule());
        modules.add(new ComponentNamingModule());
        modules.add(new LavModule());
    }
}
