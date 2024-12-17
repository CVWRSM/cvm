To execute the test model and generate a comparison report, run gradlew with a test task in this sub-project.
This can be done from this directory by launching gradlew in the parent directory, or by launching gradlew from
this directory with the following command:
```shell
../gradlew testReport
 
```

The testReport task will download a test watershed as a zip artifact from GitHub packages, and run WRIMS in 
build/testProjects/CalLite4.1_TR with run controls set in the file Test_01.config.

The report that the comparison tool produces is also stored as an artifact. The latest version can be found 
by following this link on the WRIMS project home page:  
![image](../README_images/compare_img1.png)

The PDF of the report is the last item in the list *following* all the checksum values.  
![image](../README_images/compare_img2.png)


The CalLite test model has been tested successfully running with Java 8 build 212. Newer versions of Java may
may not run the model successfully.
