package Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.avaje.ebean.Model;



@Entity
public class User extends Model{

//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer id;

    public String name;
    public String lastName;
    public int age;
    public String email;
    public String password;


    public User(){

    }


    public User(String name) {
        this.name = name;
    }

    public User(String name, String lastName){
        this.name = name;
        this.lastName = lastName;
    }

    public User(String name, String lastName, String email, String password, int age){
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

}
