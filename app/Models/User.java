package Models;

import javax.persistence.*;

import com.avaje.ebean.Model;

import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    @Column(name = "countryId")
    public Integer countryId;
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

    public User(String name, String lastName, String email, String password, Date age, int phoneNumber, String homeAddress, Integer countryId, int idQualifications, int idCompanyData, int idRole) {

        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.homeAddress = homeAddress;
        this.countryId = countryId;
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

    public boolean isValid (){

        Pattern letras = Pattern.compile("[a-zA-Z]+");
        Pattern letrasEnumeros = Pattern.compile("[a-zA-Z]+,[0-9]+");
        Pattern numeros = Pattern.compile("[0-9]+");
        Pattern emailPattern = Pattern.compile("([A-Za-z]+[._]{0,1}[A-Za-z]+)+@withus\\.pt");

        //Nome
        if(this.name == null) return false;
        Matcher name = letras.matcher(this.name);
        if(this.name == null && (name.matches()))  return false;

        {
            //Ultimo Nome
            if(this.lastName == null) return false;
            Matcher lastName = letras.matcher(this.lastName);

            if (this.lastName == null && (lastName.matches())) return false;
        }

        {
            //Morada
        if(this.homeAddress == null) return false;
        Matcher homeAdress = letrasEnumeros.matcher(this.homeAddress);

        if( (homeAdress.matches())) return false;

        }

        {
            //Password
            if(this.password == null) return false;
            Matcher password = letrasEnumeros.matcher(this.password);

            if (this.password == null && this.password.length() < 6 && (password.matches()))
                return false;
        }

        {
            //Pais
            if(this.countryId == null) return false;
        Matcher country = numeros.matcher((this.countryId).toString());

        if(this.countryId == null && (country.matches()))
                return false;
        }

        {
            //Data de Nascimento
            if(this.age == null) return false;

            Date date = new Date();
            Integer year =date.toInstant().get(ChronoField.YEAR);
            Date age = new Date();
            Integer ageYear =age.toInstant().get(ChronoField.YEAR);
            Integer idade = (year- ageYear);

            if(idade < 16 && idade > 75 )
                return false;
        }

        {
            //Contacto
            if(this.phoneNumber == null) return false;

            Matcher phoneNumber = numeros.matcher((this.phoneNumber).toString());
            if(this.phoneNumber == null && this.phoneNumber.toString().length() !=9 && phoneNumber.matches())
                return false;
        }

        {
            //Email
            if(this.email == null) return false;
            Matcher email = emailPattern.matcher(this.email);
            if(this.email != null && email.matches())
                return false;

        }
        return true;
    }





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
        this.age = age;
    }

    public void setAgeStringFormat(String ageInString) {
        DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        try {
            age = dateF.parse(ageInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


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

    public String getAgeStringFormat() {
        if (age != null) {
            DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
            String dateRequired = dateF.format(age.getTime());

            return dateRequired;
        }
        return null;
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

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
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

