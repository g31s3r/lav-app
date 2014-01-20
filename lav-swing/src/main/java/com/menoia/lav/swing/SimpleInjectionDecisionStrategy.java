package com.menoia.lav.swing;

import java.util.Locale;

import net.guts.gui.resource.InjectionDecisionStrategy;

public class SimpleInjectionDecisionStrategy implements InjectionDecisionStrategy {

    @Override
    public void injectionPerformed(Object component, Locale locale) {

    }

    @Override
    public InjectionDecisionStrategy.InjectionDecision needsInjection(Object component, Locale locale) {

        return InjectionDecisionStrategy.InjectionDecision.INJECT_HIERARCHY;
    }
}
