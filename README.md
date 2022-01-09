# Simple Web Form to collect user info  
  
The web form is created using Spring Boot framework and Thymeleaf as the template engine. There will be 3 user input fields. When the user hit the submit button, the form will send out a POST request to the server. The server will generate a random 8 character long hex string as the file name and store the user info into that file.  

To retrieve the info, a GET requests and the id/filename have to be sent to /contact/<id>. The server will then check for the file and display the content. If the file does not exist, then the server will return a not found message.  

## Command line Argument  
  
The server will take in 1 command line argument which is `--dataDir`. Without this argument, the server will exit. The argument is the directory which will use to store and retrieve the user info.  
```
mvn spring-boot:run -Dspring-boot.run.arguments="--dataDir=database"
```
  
For powershell user,
```
mvn spring-boot:run '-Dspring-boot.run.arguments="--dataDir=database"'
```