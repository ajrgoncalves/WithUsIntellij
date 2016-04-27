package Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.avaje.ebean.Model;


@Entity
public class User extends Model {

//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String name;
    public String lastName;
    public int age;

    public String email;
    public String password;


    public User() {

    }


    public User(String name) {
        this.name = name;
    }

    public User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public User(String name, String lastName, String email, String password, int age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User: [name = " + name + " | lastName = " + lastName + " | age = " + age + "]";
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
