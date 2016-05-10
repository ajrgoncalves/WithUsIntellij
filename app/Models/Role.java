package Models;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebeaninternal.server.lib.util.Str;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="role")
public class Role extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    @Column(name = "rolePrivileges")
    @OneToMany
    public String rolePrivileges;

    @ManyToOne
    public List<User> members = new ArrayList<User>();

    public Role (String rolePrivileges, User membro){
        this.rolePrivileges = rolePrivileges;
        this.members.add(membro);
    }


    public static Model.Finder<Long, Role> find = new Model.Finder<Long, Role>(Role.class);

//    retrieve role for user
    public static List<Role> findIvolving(String user) {
        return find.where()
                .eq("members.email", user).findList();
    }

//    Create new Role
    public static Role create(String rolePrivileges, String membro){
        Role role = new Role(rolePrivileges, User.find.ref(membro));
        role.save();
        return role;
    }

//    Rename Role
    public  static String rename(Long roleId, String newName){
        Role role = find.ref(roleId);
        role.rolePrivileges = newName;
        role.update();
        return newName;
    }

//   Add member to role
    public static void addMember(Long role, String user){
        Role r = Role.find.setId(role).fetch("members", "email").findUnique();
        r.members.add(
                User.find.ref(user));
        r.save();
    }















}