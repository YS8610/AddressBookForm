package tfip.modserver.addressbook.model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private String phone;

    public User(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName(){
        return this.name;
    } 

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    } 

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phonenumber){
        this.phone = phonenumber;
    }
}
