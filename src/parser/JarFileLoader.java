package parser;

import java.io.File;
import java.lang.reflect.*;
import java.net.*;

public class JarFileLoader {

	private Class[] parameters = new Class[]{URL.class};
	private Class loadedClass;
	private boolean loadSuccessful = false;

	public JarFileLoader(String fileName, String className) throws ErrorLoadingJarFile, ErrorFindingClass {

		// Lifted from Robert Kremer's Java Reflection Notes.

		// Load file here
		try {
			// Take the file and convert it into a classpath using a URL conversion
			File jarFile = new File(fileName);
			URL url = jarFile.toURI().toURL();
			
			// Debug
			System.out.println(fileName);
			System.out.println(className);			

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
				// SysClass holds the info for the loaded jar file
				this.loadedClass = sysClass.forName(className);
				this.loadSuccessful = true;
			}
			// If there is any error loading the jar file throw the generic <exception>
			catch (Exception e) {
				throw new ErrorFindingClass(className);
			}

		}
		// If there is any error loading the jar file throw the generic <exception>
		catch (Exception e) {
			throw new ErrorLoadingJarFile(fileName);
		}

	}

	public Class getLoadedClass() {
		return this.loadedClass;
	}

	public boolean isFileLoaded() {
		return this.loadSuccessful;
	}

}