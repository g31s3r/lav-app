package com.menoia.livros.server.injector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.Module;

/**
 * <p>
 * This <code>@WithModule</code> annotation is used when writing test that requires Guice. It indicates which modules
 * should be loaded.
 * </p>
 * 
 * @author Antoine DESSAIGNE
 * @see com.google.inject.Module
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface WithModules {
	/**
	 * Array of all the modules' classes to load for configuring the Guice injector.
	 * 
	 * @return Modules' classes to load.
	 */
	public Class<? extends Module>[] value();
}