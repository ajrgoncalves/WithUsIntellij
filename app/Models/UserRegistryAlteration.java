package Models;

import com.avaje.ebean.Model;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by Toz on 24/05/2016.
 */

@Entity
@Table(name = "userregistryalteration")
public class UserRegistryAlteration extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @Column(name = "userId")
    public Long userId;
    @Column(name = "updaterId")
    public Long updaterId;
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

    public UserRegistryAlteration(User user){
        this.userId = user.getId();
        this.updaterId = user.getId();
        this.name = user.getName();
        this.lastName= user.getLastName();
        this.age= user.getAge();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber= user.getPhoneNumber();
        this.homeAddress= user.getHomeAddress();
        this.countryId= user.getCountryId();
        this.idQualifications= user.getIdQualifications();
        this.idCompanyData= user.getIdCompanyData();
        this.idRole= user.getIdRole();
    }

    public UserRegistryAlteration(User updater, User updated){
        this.userId = updated.getId();
        this.name = updated.getName();
        this.lastName= updated.getLastName();
        this.age= updated.getAge();
        this.email = updated.getEmail();
        this.password = updated.getPassword();
        this.phoneNumber= updated.getPhoneNumber();
        this.homeAddress= updated.getHomeAddress();
        this.countryId= updated.getCountryId();
        this.idQualifications= updated.getIdQualifications();
        this.idCompanyData= updated.getIdCompanyData();
        this.idRole= updated.getIdRole();

        this.updaterId = updater.getId();

    }

    public UserRegistryAlteration(Long userId,Long updaterId,String name, String lastName, String email, String password, Date age, int phoneNumber, String homeAddress, Integer countryId, int idQualifications, int idCompanyData, int idRole) {

        this.userId= userId;
        this.updaterId= updaterId;
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




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(Long updaterId) {
        this.updaterId = updaterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
