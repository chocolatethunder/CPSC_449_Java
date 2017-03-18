There is a UML diagram provided which will help navigate the classes better. 

There is an issue with the (rand) command. It comes and goes. Sometimes a reload or two are 
required for it to function properly. 

This README is split into 2 sections so choose your own adventure:

	A: Automated 
	B: Boring

*******************************	
*******************************
*          AUTOMATED          *
*******************************
*******************************

A1: Please instal Apache Ant.
	Windows Installation: https://www.mkyong.com/ant/how-to-install-apache-ant-on-windows/
	Mac Installation: https://www.mkyong.com/ant/how-to-apache-ant-on-mac-os-x/

A2: From this directory in Terminal/Command Prompt you may run these commands:
		ant clean   : cleans the object files and javadocs in the /bin and /docs folders
		ant makedir : creates all the necesary folders to store files in
		ant compile : compiles and creates object files in the /bin folder
		ant docs    : generates all the javadocs into the /docs folder
		ant jar     : this will 2 things
						(1) It will create a methods.jar (name can be changed in build.xml) file which will have the parser in it.
						(2) It will create a Commands.jar file using the class in the /ref folder. For testing purposes it currently contains Rob's Commands.java file.

		ant main    : does all of the above in one go!

********************************************
*          HOW TO RUN THE PROGRAM          *
********************************************

Unfortunately, this is not automated so run the following command:

java -jar methods.jar commands.jar

********************************************************
*          HOW TO RUN TESTS IN AUTOMATED MODE          *
********************************************************

You may:

Place only one (1) .java file in the /ref folder and run "ant main". This java file contains all the methods that will 
compile into commands.jar which can be then imported into methods.jar at RUNTIME.

- OR -

You may place as many "command" jar files in the root folder and import them into method.jar at RUNTIME one at a time. 


****************************	
****************************
*          BORING          *
****************************
****************************

For this method:
B1. compile all the files in the src/parser folder into the bin/ folder
B2. package them into a jar file using the object files in the bin/ folder
B3. place the testing jar containing commands with the file created in B2.
B4. run: java -jar [jar from B2] [jar from B3]

Profit!!
