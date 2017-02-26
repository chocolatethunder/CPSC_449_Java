import java.lang.reflect.*; //contains Java Reflection Model API
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.awt.*;
import java.io.File;

public class ReflectionPractice {
	private static final Class<URLClassLoader> URLCLASSLOADER =
	        URLClassLoader.class;
	private static final Class<?>[] PARAMS = new Class[] { URL.class };
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, MalformedURLException, IllegalAccessException, IllegalArgumentException {
		
		// Opens the jar file ------------------------------------------------------------------
		// used the example from the notes and this other source:
		// http://stackoverflow.com/questions/3580752/java-dynamically-loading-a-class
		File f = new File("C:\\Users\\Kowther\\Desktop\\Commands.jar"); // hard coded right now
		URL url = f.toURI().toURL();
		
		URLClassLoader systloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class<?> sysclass = URLClassLoader.class;
		
		Method method = sysclass.getDeclaredMethod("addURL", PARAMS );
		
		method.setAccessible(true);
		
		method.invoke(systloader, new Object[] { url });
	
		// Creates the class object from file ------------------------------------------------
		System.out.println(sysclass.forName("Commands"));
		
		Class c = sysclass.forName("Commands");		// Must use forname (??) to create the class object from the file
		
		// prints out method names -------------------------------------------------------------
		// prints out the method names in (almost) the format needed for the assignment.
		// Not completely right, prints out extra methods (???). Used the example from the 
		// class notes.
		Method[] theMethods = c.getMethods(); //get the class public methods
		for (int i = 0; i < theMethods.length; i++) {
			String methodString = theMethods[i].getName(); //get the method name
			System.out.print("(" + methodString);
			Class[] parameterTypes = theMethods[i].getParameterTypes();
			System.out.print(" ");
			for (int k = 0; k < parameterTypes.length; k ++) {
				//get the name of each parameter
				String parameterString = parameterTypes[k].getName();
				System.out.print(" " + parameterString);
			}
			System.out.print(") : ");
			//get the method return type
			String returnString = theMethods[i].getReturnType().getName();
			System.out.println(returnString);
			//get the method parameters types
		}
	}
}
