package Models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

import java.util.List;


@Entity
public class User extends Model {

//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @Column(name = "name")
    public String name;
    @Column(name = "lastName")
    public String lastName;
    @Column(name = "age")
    public int age;
    @Column(name = "email")
    public String email;
    @Column(name = "password")
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

    // Queries

    public static Model.Finder<String, User> find = new Model.Finder(User.class);

//    Retrieve all users

    public static List<User> findall() {
        return find.all();
    }

//    Retrieve a user from email.

    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }



    public boolean authenticate() {
        Model.Finder<String, User> finder = new Model.Finder<>(User.class);
        Logger.info("### email: " + this.email + " password: " + this.password);

        User users = finder.where()
                .eq("email", this.email).findUnique();

        return BCrypt.checkpw(this.password, users.password);
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
