# Simple Web Form to collect user info  
  
The web form is created using Spring Boot framework and Thymeleaf as the template engine. There will be 3 user input fields. When the user hit the submit button, the form will send out a POST request to the server. The server will generate a random 8 character long hex string as id and save the data to Redis database. The id generated will be the key and user info will be stored as JSON string.  
  
To retrieve the info, a GET requests and the id/filename have to be sent to /contact/`id` where id is 8 character long hex string. The server will then check for the file and display the content. If the file does not exist, then the server will return a not found message.The configurations for redis server and port are set in src\main\resources\application.properties file.  
  
To run,  
```
mvn spring-boot:run
```