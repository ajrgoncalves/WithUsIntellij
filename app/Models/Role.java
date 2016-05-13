package Models;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebeaninternal.server.lib.util.Str;


import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "role")
public class Role extends Model {
    public static final int ADMIN = 1;
    public static final int USER = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "rolePrivileges")
    public String rolePrivileges;

    public Role() {
    }

    public Role(String rolePrivileges, User membro) {
        this.rolePrivileges = rolePrivileges;
    }



    private static Model.Finder<Long, Role> find = new Model.Finder(Role.class);

    //    retrieve role for user
    public static List<Role> findIvolving(String user) {
        return find.where()
                .eq("members.email", user).findList();
    }

    //    Create new Role
    public static Role create(String rolePrivileges, String membro) {
        Role role = new Role(rolePrivileges, User.find.ref(membro));
        role.save();
        return role;
    }

    //    Rename Role
    public static String rename(Long roleId, String newName) {
        Role role = find.ref(roleId);
        role.rolePrivileges = newName;
        role.update();
        return newName;
    }

//      Check if a user got a role

    public static boolean isMember(Long role, String user) {
        return find.where()
                .eq("members.email", user)
                .eq("id", role)
                .findRowCount() > 0;
    }

    public static Role findRole(long roleId) {
        return find.where().eq("id", roleId).findUnique();
    }

    @Override
    public String toString() {
        return "Role " + id;
    }


}