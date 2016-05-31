package Models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Toz on 30/05/2016.
 */

@Entity
@Table(name = "areamodel")
public class AreaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "description")
    public String description;


    public static Model.Finder<Integer, AreaModel> find = new Model.Finder(AreaModel.class);

    //Find all Model areas
    public static List<AreaModel> getAllUserModels() {
        return find.all();
    }

    public static AreaModel findByID(Integer id){
        return find.where().eq("id", id).findUnique();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
