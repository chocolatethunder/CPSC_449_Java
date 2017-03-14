package parser;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.net.*;


public class Main {    
    
	public static void main (String[] args) {
		try {
			LoadParser newProgram = new LoadParser(args);
		} catch (Exception e) { 
			System.out.println(e.getMessage());
		}

	} 
}
