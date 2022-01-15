package tfip.modserver.addressbook.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import tfip.modserver.addressbook.model.User;

@Repository
public class UserRepo {
    
    @Autowired
    @Qualifier("redisDB")
    private RedisTemplate<String, String> template;

    public void add(String id, User user){
        JsonObject userJson = Json.createObjectBuilder()
            .add("name",user.getName())
            .add("email", user.getEmail())
            .add("phone", user.getPhone())
            .build();
        template.opsForValue().set(id,userJson.toString());
    }

    public String getID(String id){
        String jsonString = template.opsForValue().get(id);
        return jsonString;
    }

    public boolean keyExist(String id){
        return template.hasKey(id);
    }
}