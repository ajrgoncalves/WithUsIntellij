package Models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Update;
import com.avaje.ebean.annotation.Sql;
import org.h2.command.dml.Delete;

import javax.persistence.*;
import java.util.List;


/**
 * Created by Toz on 30/05/2016.
 */

@Entity
@Table(name = "areamodeluser")
public class AreaModelUser extends Model {

    @Column(name = "userId")
    public Long userId;


    @Column(name = "areaModelId")
    public Integer areaModelId;
    public AreaModelUser() {

    }


    public static Model.Finder<Integer, AreaModelUser> find = new Model.Finder(AreaModelUser.class);

    //Find all User Models
    public static List<AreaModelUser> getAllUserModels() {
        return find.all();
    }

    public static List<AreaModelUser> findByUserId(Long id) {
        return find.where().eq("userId", id).findList();
    }

    public boolean deleteRow(Long userId, Integer areaModelId) {


        //// FIXME: 02/06/2016
        // this may have an SQL Injection...
        System.out.println(" user id delete row"+ userId + "area model id" + areaModelId);

        String query = "DELETE FROM areamodeluser WHERE areamodeluser.userId = " + userId + " and areamodeluser.areaModelId = " + areaModelId + ";";

        SqlUpdate deleteRow = Ebean.createSqlUpdate(query);
        deleteRow.execute();
        //return Ebean.find(AreaModelUser.class).where().eq("userId", userId).eq("areaModelId", areaModelId).delete();

        return true;


        //System.out.println("Row apagada com sucesso");
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAreaModelId() {
        return areaModelId;
    }

    public void setAreaModelId(Integer areaModelId) {
        this.areaModelId = areaModelId;
    }
}
