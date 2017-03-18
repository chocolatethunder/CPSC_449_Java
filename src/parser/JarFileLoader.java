/************************************************************************************
 * CPSC 449 - Winter 2017															*
 * Prof: Rob Kremer																	*
 * Assignment: Java																	*
 * Group #: 32																		*
 * Members: Saurabh Tomar, Kaylee Stelter, Kowther Hassan, Matthew Mullins, Tsz Lam	*
 * Description:																		*
 * 		-Contains methods which attempt to load the jar file and class.				*
 *  																				*
 * Contain methods:																	*			
 * 		+JarFileLoader(String, String) <- Constructor								*
 * 		+getLoadedClass():Class														*
 * 		+isFileLoaded():boolean														*
 * 																					*
 ************************************************************************************/

package parser;

import java.io.File;
import java.lang.reflect.*;
import java.net.*;

public class JarFileLoader {

	private Class[] parameters = new Class[]{URL.class};
	private Class loadedClass;
	private boolean loadSuccessful = false;

	/**
	 * 
	 * Attempts to load the provided jar file and class, throwing appropriate
	 * exceptions if the process is not possible.
	 * 
	 * @param fileName - represents the jar file being loaded
	 * @param className - represents the class being loaded
	 * @throws ErrorLoadingJarFile - Fatal error thrown when the jar file could not be loaded
	 * @throws ErrorFindingClass - Fatal error thrown when the class could not be found
	 * 
	 */
	public JarFileLoader(String fileName, String className) throws ErrorLoadingJarFile, ErrorFindingClass {

		// Lifted from Robert Kremer's Java Reflection Notes.

		// Load file here
		try {
			// Take the file and convert it into a classpath using a URL conversion
			File jarFile = new File(fileName);
			URL url = jarFile.toURI().toURL();
			
			// Debug
			/*
			System.out.println(fileName);
			System.out.println(className);			
			*/
			
			// Get the Classloader method
			URLClassLoader sysLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
			// Turn the URLClassLoader into a class object
			Class<?> sysClass = URLClassLoader.class;
			// Grab the "addURL" method
			Method method = sysClass.getDeclaredMethod("addURL", parameters);
			// Cheat and set it to accesible because it is private
			method.setAccessible(true);
			// Invoke it with the Jar file url
			method.invoke(sysLoader, new Object[] { url });

			// Load the class here
			try {
				// sysClass holds the info for the loaded jar file
				this.loadedClass = sysClass.forName(className);
				this.loadSuccessful = true;
			}
			// If there is any error loading the class throw the fatal error exception
			catch (Exception e) {
				throw new ErrorFindingClass(className);
			}

		} 
		// If there is any error loading the jar file throw the fatal error exception
		catch (Exception e) {
			throw new ErrorLoadingJarFile(fileName);
		}

	}

	/**
	 * @return - Returns the class which was loaded
	 */
	public Class getLoadedClass() {
		return this.loadedClass;
	}

	/**
	 * @return - Returns a boolean which represents if the file was loaded successfully 
	 */
	public boolean isFileLoaded() {
		return this.loadSuccessful;
	}

}