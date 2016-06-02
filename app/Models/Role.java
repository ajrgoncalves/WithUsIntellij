package Models;

import javax.persistence.*;
import com.avaje.ebean.Model;
import java.util.List;


@Entity
@Table(name = "role")
public class Role extends Model {
    // TODO: keep this synced with db
    public static final int SUPERADMIN = 1;
    public static final int ADMIN = 2;
    public static final int USER = 3;
    public static final int GUEST = 4;

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

    //Find all
    public static List<Role> getAllRoles() {
        return find.all();
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