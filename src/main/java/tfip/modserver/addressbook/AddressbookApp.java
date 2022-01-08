package tfip.modserver.addressbook;

import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class AddressbookApp {
	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(AddressbookApp.class);
		SpringApplication app = new SpringApplication(AddressbookApp.class);
		ApplicationArguments cmdarg = new DefaultApplicationArguments(args);
		String dataDir;

		if (cmdarg.containsOption("dataDir")){
			dataDir = cmdarg.getOptionValues("dataDir").get(0);
			Path pDataDir = Path.of(dataDir);
			if ( !Files.exists(pDataDir) ){
				try{
					Files.createDirectory(pDataDir);
				} 
				catch (IOException e) {
					logger.info("directory cannot be created");
					System.exit(1);
				}
			} 
		}
		else{
			logger.info("Error. DataDir is not specified. System will exit");
			System.exit(1);
		}

		app.run(args);
	}

}
