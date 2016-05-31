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


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "userId")
    public Long userID;

    @Column(name = "areaModelId")
    public Integer modelAccessAreaId;

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



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Integer getAreaModelId() {
        return modelAccessAreaId;
    }

    public void setModelAccessAreaId(Integer modelAccessAreaId) {
        this.modelAccessAreaId = modelAccessAreaId;
    }
}
