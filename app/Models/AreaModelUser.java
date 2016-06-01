package Models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;



/**
 * Created by Toz on 30/05/2016.
 */

@Entity
@Table(name = "areamodeluser")
public class AreaModelUser extends Model {



    @Column(name = "userId")
    public Long userID;


    @Column(name = "areaModelId")
    public Integer areaModelId;

    public AreaModelUser() {

    }



    public static Model.Finder<Integer, AreaModelUser> find = new Model.Finder(AreaModelUser.class);

    //Find all User Models
    public static List<AreaModelUser> getAllUserModels() {
        return find.all();
    }

    public static List<AreaModelUser> findByUserId(Long id){
        return find.where().eq("userId", id).findList();
    }




    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Integer getAreaModelId() {
        return areaModelId;
    }

    public void setAreaModelId(Integer areaModelId) {
        this.areaModelId = areaModelId;
    }
}
