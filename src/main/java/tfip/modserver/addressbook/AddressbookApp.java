package tfip.modserver.addressbook;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AddressbookApp {
	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(AddressbookApp.class);
		app.run(args);
	}

}
