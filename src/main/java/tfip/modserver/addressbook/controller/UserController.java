package tfip.modserver.addressbook.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.boot.ApplicationArguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tfip.modserver.addressbook.Contacts;
import tfip.modserver.addressbook.model.User;

@Controller
@RequestMapping(path = "/contact")
public class UserController {
    
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ApplicationArguments applicationArguments;

    private String idGenerator(){
        List<String> listHexStrings = IntStream.rangeClosed(0, 15)
            .boxed()
            .map(Integer::toHexString)
            .collect(Collectors.toList());
        Random rand = new Random();
        String hexString="";
        for (int i =0; i<8;i++){
            hexString += listHexStrings.get(rand.nextInt(16));
        }
        return hexString;
    }

    private boolean duplicateID(String dir, String id){
        boolean dup=true;
        try {
            List<String> listFileName = Files.list(Path.of(dir))
                .map(Path::toString)
                .toList();
            if (listFileName.isEmpty()){
                dup = false;
            }
            else{
                dup =  listFileName.contains(id);
            }
        }
        catch (IOException e) {
            logger.info("error checking folder for duplicate id");
        }
        return dup;
    }

    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        produces = "text/html"
        )
    @RequestMapping(method = RequestMethod.POST)
    public String createUser(@ModelAttribute User user, Model model){
        String cmddir = applicationArguments.getOptionValues("dataDir").get(0);
        Contacts database = new Contacts(cmddir);
        List<String> userinfo = new ArrayList<>();
        
        String id = idGenerator();
        while(duplicateID( cmddir, id)){
            id = idGenerator();
        }
        String filename = "/"+id;
        
        userinfo.add("Name: " + user.getName());
        userinfo.add("Email: " + user.getEmail());
        userinfo.add("Phone Number: " + user.getPhone());

        database.createFile(filename);
        database.writeFile(filename, userinfo);
        model.addAttribute("nameEntered", user.getName());
        model.addAttribute("emailEntered", user.getEmail());
        model.addAttribute("phoneEntered", user.getPhone());
        model.addAttribute("id", id);
        return "dataSubmit";
    }

    @GetMapping("{id}")
    public String getID(
        @PathVariable(name="id", required = true) String id,
        Model model){
            logger.info("path variable = " + id);
            String cmddir = applicationArguments.getOptionValues("dataDir").get(0);
            Contacts database = new Contacts(cmddir);
            try{
                List<String> idFile = database.readFile("001");

            }
            catch (IOException e){
                logger.info("cannot read file when retrieve using get method");;
            }
        

        return "getid";
    }
}
// mvn spring-boot:run '-Dspring-boot.run.arguments="--dataDir=database"'