package com.menoia.lav.swing;

import net.guts.event.Events;
import net.guts.gui.application.AppLifecycleStarter;
import net.guts.gui.naming.ComponentNamePolicy;
import net.guts.gui.resource.Resources;
import net.guts.gui.task.blocker.BlockerDialogPane;

import com.google.inject.AbstractModule;
import com.menoia.lav.server.entity.Customer;
import com.menoia.lav.swing.util.TasksGroupProgressPanel;

public class LavModule extends AbstractModule {

    protected void configure() {

        bind(AppLifecycleStarter.class).to(LavLifecycleStarter.class).asEagerSingleton();
        Events.bindChannel(binder(), Customer.class);
        Resources.bindRootBundle(binder(), getClass(), "resources");
        bind(BlockerDialogPane.class).to(TasksGroupProgressPanel.class);
        bind(ComponentNamePolicy.class).toInstance(new LavMain.LavNamePolicy());
        Resources.bindInjectionStrategy(binder()).to(SimpleInjectionDecisionStrategy.class);
    }
}
