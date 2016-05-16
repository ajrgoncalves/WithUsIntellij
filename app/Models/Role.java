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
    // TODO: keep this synced with db
    public static final int SUPERADMIN = 1;
    public static final int ADMIN = 2;
    public static final int USER = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "rolePrivileges")
    public String rolePrivileges;

    public Role() {
    }

    public Role(String rolePrivileges, User membro) {
        this.rolePrivileges = rolePrivileges;
    }



    private static Model.Finder<Integer, Role> find = new Model.Finder(Role.class);

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
    public static String rename(Integer roleId, String newName) {
        Role role = find.ref(roleId);
        role.rolePrivileges = newName;
        role.update();
        return newName;
    }

//      Check if a user got a role

    public static boolean isMember(Integer role, String user) {
        return find.where()
                .eq("members.email", user)
                .eq("id", role)
                .findRowCount() > 0;
    }

    public Integer getId() {
        return id;
    }

    public static Role findRole(long roleId) {
        return find.where().eq("id", roleId).findUnique();
    }

    @Override
    public String toString() {
        return "Role " + id;
    }


}