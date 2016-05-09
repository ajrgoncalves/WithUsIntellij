package Models;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;

import org.mindrot.jbcrypt.BCrypt;
import play.Logger;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Role extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;
    @Column(name = "rolePrivileges")
    public String rolePrivileges;

    @ManyToMany
    public List<User> members = new ArrayList<User>();



    public Role(String rolePrivileges , User userPriv) {
        this.rolePrivileges = rolePrivileges;
        this.members.add(userPriv);
    }


    public static Model.Finder<String, Role> find = new Model.Finder(Role.class);



//    Retrieve all roles

    public static List<Role> findall() {
        return find.all();
    }


//    Create a new Role

    public static  Role create (String rolePrivileges, String userPriv){
        Role role = new Role(rolePrivileges, User.find.ref(userPriv));
        role.save();
       // role.members.addAll() saveManyToManyAssociations("members");
        return role;
    }



//    delete role

    public static void deleteRole (String name) {
        Ebean.createSqlUpdate("delete from role where name = : name")
                .setParameter("name", name).execute();
    }


// Rename Role

    public  static String rename (String roleId, String newName){
        Role role = find.ref(roleId);
        role.rolePrivileges = newName;
        role.update();
        return newName;
    }



}