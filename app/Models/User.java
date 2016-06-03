package Models;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;

import com.avaje.ebean.annotation.Transactional;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    // Procurar User pelo ID

    public static User findByID(Long id) {
        return find.where().eq("id", id).findUnique();
    }

    // Procura todos os users

    public static List<User> getAllUsers() {
        return find.all();
    }

    //Modules


    @Transactional
    public static List<AreaModelUser> findAreaModulesById(Long userId) {

        // Encontra o User com o id indicado
        User user = find.where().eq("id", userId).findUnique();

        // Encontra as relações existentes para o user encontrado antes
        List<AreaModelUser> areaModelUser = AreaModelUser.findByUserId(user.id);

        // Retorna a lista de Modules encontrada para aquele utilizador
        return areaModelUser;

    }

    public boolean hasModule(int moduleId){


         Model.Finder<String, AreaModelUser> find = new Model.Finder(AreaModelUser.class);
        return find.where().eq("areaModelId", moduleId).eq("userId", this.id).findUnique() != null;
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

    public boolean isValid() {
        //TODO: Verificar se realmente os campos estão a ser aceites correctamente.


        Pattern letras = Pattern.compile("[a-zA-Z]+");
        Pattern morada = Pattern.compile("([a-zA-Z0-9\\s]*)");
        Pattern contacto = Pattern.compile("(?=.*[0-9]).{9,9}");
        Pattern numeros = Pattern.compile("([0-9])+");
        Pattern emailPattern = Pattern.compile("([A-Za-z]+[._]{0,1}[A-Za-z]+)+@withus\\.pt");
        Pattern passwordPattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}");

        //Nome
        if (this.name == null) return false;
        Matcher name = letras.matcher(this.name);
        if (!name.matches()) {
            System.out.println("Erro no nome");
            return false;
        }

        //Ultimo Nome
        if (this.lastName == null) return false;
        Matcher lastName = letras.matcher(this.lastName);

        if (!lastName.matches()) {
            System.out.println("Erro no ultimo nome");
            return false;
        }


        //Morada
        if (this.homeAddress == null) return false;
        Matcher homeAdress = morada.matcher(this.homeAddress);

        if (!homeAdress.matches()) {
            System.out.println("Erro na morada");
            return false;
        }


        //Password
        if (this.password == null) return false;
        Matcher password = passwordPattern.matcher(this.password);

        if (!password.matches()) {
            System.out.println("Erro na password");
            return false;
        }


        //Pais
        if (this.countryId == null) return false;
        Matcher country = numeros.matcher((this.countryId).toString());

        if (!country.matches()) {
            System.out.println("Erro no pais");
            return false;
        }

        //Data de Nascimento
        if (this.age == null) return false;
        Integer idade = 0;

        try {
            LocalDate bDate = new LocalDate(age);
            LocalDate now = new LocalDate();

            idade = now.getYear() - bDate.getYear();
//           System.out.println("Age em idade: " + idade);

            Years oAge = Years.yearsBetween(bDate, now);
//            System.out.println("Age: " + oAge.getYears());

            if ((now.getYear() - bDate.getYear() < 16) || (now.getYear() - bDate.getYear() > 75)) {
                System.out.println("Erro no idade");
                return false;
            }

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getLocalizedMessage() + "\n" + ex.getMessage() + "\n" + ex.getStackTrace() + "\n");
            ex.printStackTrace();
        }


        //Contacto
        if (this.phoneNumber == null) return false;

        Matcher phoneNumber = contacto.matcher((this.phoneNumber).toString());

        if (!phoneNumber.matches()) {
            System.out.println("Erro no phoneNumber");
            return false;
        }


        //Email
        Matcher email = emailPattern.matcher(this.email);
        if (!email.matches()) {
            System.out.println("Erro no email");
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

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }
}

