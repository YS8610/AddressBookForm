package tfip.modserver.addressbook.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import tfip.modserver.addressbook.model.User;
import tfip.modserver.addressbook.repo.UserRepo;

@Controller
@RequestMapping(path = "/contact")
public class UserController {
    
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepo userRepo; 

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

    @PostMapping(
        consumes = "application/x-www-form-urlencoded",
        produces = "text/html"
        )
    @RequestMapping(method = RequestMethod.POST)
    public String createUser(@ModelAttribute User user, Model model){
        
        String id = idGenerator();
        while(userRepo.keyExist(id)){
            id = idGenerator();
        }

        userRepo.add(id, user);
        model.addAttribute("nameEntered", user.getName());
        model.addAttribute("emailEntered", user.getEmail());
        model.addAttribute("phoneEntered", user.getPhone());
        model.addAttribute("id", id);
        return "dataSubmit";
    }

    @GetMapping("{id}")
    public String getID(
        @PathVariable(name="id", required = true) String id, Model model){
            logger.info("path variable = " + id);

            String userJsonString = userRepo.getID(id);
            try {
                InputStream inputStream = new ByteArrayInputStream(userJsonString.getBytes());
                JsonReader reader = Json.createReader(inputStream);
                JsonObject data = reader.readObject();
                model.addAttribute("name", data.getString("name"));
                model.addAttribute("email", data.getString("email"));
                model.addAttribute("phone", data.getString("phone"));
                model.addAttribute("id", id);
            } 
            catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"wrong id");
            }
        return "getid";
    }
}