PLEASE READ THIS VERY CAREFULLY.

- Please install Apache Ant on your machine. It takes 3 minutes and you will love this thing forever and ever.
    - Ant already comes pre-installed with Eclipse (How bow dah!) so you just have to enable it.
	Windows Installation: https://www.mkyong.com/ant/how-to-install-apache-ant-on-windows/
	Mac Installation: https://www.mkyong.com/ant/how-to-apache-ant-on-mac-os-x/	

- I encourage you to look in the build.xml file to see how the automation is happening but do not change it.



USAGE:

ant clean   : cleans the object files and javadocs in the /bin and /docs folders
ant makedir : creates all the necesary folders to store files in
ant compile : compiles and creates object files in the /bin folder
ant docs    : generates all the javadocs into the /docs folder
ant jar     : this will 2 things
                (1) It will create a methods.jar (name can be changed in build.xml) file which will have the parser in it.
                (2) It will create a Commands.jar file using the class(es) in the /ref folder. For testing purposes it currently contains Rob's file.

ant main    : does all of the above in one go!



FINAL NOTES:

I am still trying to figure out how to automate "java -jar methods.jar" as I go along but it is not a priority.

Apache Ant and Apache Maven are powerful tools so use 'em!
