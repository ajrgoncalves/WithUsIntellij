package Models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "user")
public class User extends Model {

//    private static final long serialVersionUID = 1L;
//    public static final HashMap<String, Integer> variablePermissions = new HashMap<>();
//
//    static {
//        variablePermissions.put("name",Role.USER);
//        variablePermissions.put("lastName",Role.USER);
//        variablePermissions.put("age",Role.USER);
//        variablePermissions.put("password",Role.USER);
//        variablePermissions.put("country",Role.USER);
//        variablePermissions.put("phoneNumber",Role.USER);
//        variablePermissions.put("homeAddress",Role.USER);
//        variablePermissions.put("idRole",Role.SUPERADMIN);
//
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(name = "name")
    public String name;
    @Column(name = "lastName")
    public String lastName;
    @Column(name = "age")
    public Date age;
    @Column(name = "email")
    public String email;
    @Column(name = "password")
    public String password;
    @Column(name = "phoneNumber")
    public Integer phoneNumber;
    @Column(name = "homeAddress")
    public String homeAddress;
    @Column(name = "country")
    public String country;
    @Column(name = "idQualifications")
    public Integer idQualifications;
    @Column(name = "idCompanyData")
    public Integer idCompanyData;
    @Column(name = "idRole")
    public Integer idRole;


    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public User(String name, String lastName, String email, String password, Date age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public User(String name, String lastName, String email, String password, Date age, int phoneNumber, String homeAddress, String country, int idQualifications, int idCompanyData, int idRole) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.country = country;
        this.idQualifications = idQualifications;
        this.idCompanyData = idCompanyData;
        this.idRole = idRole;
    }


    // Queries

    public static Model.Finder<String, User> find = new Model.Finder(User.class);


//    Retrieve a user from email.


    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }

    // Procurar todos os users

    public static List<User> getAllUsers() {
        return find.all();
    }

// Authenticate

    public static User authenticate(String email, String password) {
        Model.Finder<String, User> finder = new Model.Finder<>(User.class);
//        Model.Finder<String, Role> finderRole = new Model.Finder<>(Role.class);

        Logger.info("### email: " + email + " password: " + password);

        User user = finder.where().eq("email", email).findUnique();

        if (user != null) {
            if (BCrypt.checkpw(password, user.password))
                return user;
        }

        return null;
    }
//    public String checkRole () {
//
//            if (idRole == 1) {
//                return "admin";
//            } else if (idRole == 2) {
//                return "user";
//            } else {
//                return "No Role";
//            }
//        }

    public String UserInfo() {
        return "User: [name = " + name + " | lastName = " + lastName + " | age = " + age + " | Role: " + idRole + "]";
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(Date age) {
        Logger.info("setAge: " + age.toString());
        this.age=age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getIdQualifications() {
        return idQualifications;
    }

    public void setIdQualifications(Integer idQualifications) {
        this.idQualifications = idQualifications;
    }

    public Integer getIdCompanyData() {
        return idCompanyData;
    }

    public void setIdCompanyData(Integer idCompanyData) {
        this.idCompanyData = idCompanyData;
    }

    public long getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }
}

