import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * 
 * loads a jar file
 * @author Kowther
 *
 */
public class JarFileLoader {
	private static final Class<URLClassLoader> URLCLASSLOADER =
	        URLClassLoader.class;
	private static final Class<?>[] PARAMS = new Class[] { URL.class };
	
	/**
	 * 
	 * constructor. *****WANT TO PUT THE FOLLOWING CODE IN DIFFERENT METHODS WITH TRY-CATCH BLOCKS, WILL BE NEATER********
	 * @param filename
	 * @param classname
	 * @throws MalformedURLException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 */
	public JarFileLoader( String filename, String classname ) throws MalformedURLException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		// Opens the jar file ------------------------------------------------------------------
		// used the example from the notes and this other source:
		// http://stackoverflow.com/questions/3580752/java-dynamically-loading-a-class
		File f = new File( filename ); // hard coded right now
		URL url = f.toURI().toURL();
		
		URLClassLoader systloader = ( URLClassLoader )ClassLoader.getSystemClassLoader( );
		Class<?> sysclass = URLClassLoader.class;
		
		Method method = sysclass.getDeclaredMethod( "addURL", PARAMS );
		
		method.setAccessible( true );
		
		method.invoke( systloader, new Object[] { url } );
	
		// Creates the class object from file ------------------------------------------------
		System.out.println( sysclass.forName( classname ) );
		
		Class c = sysclass.forName( classname );		// Must use forname (??) to create the class object from the file
		
	}

}
