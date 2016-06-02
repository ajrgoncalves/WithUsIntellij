package Models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.SqlUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Toz on 30/05/2016.
 */

@Entity
@Table(name = "areamodel")
public class AreaModel extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Column(name = "description")
    public String description;

    public AreaModel(){}


    public static Model.Finder<Integer, AreaModel> find = new Model.Finder(AreaModel.class);

    //Find all Model areas
    public static List<AreaModel> getAllModels() {
        return find.all();
    }

    public static AreaModel findByID(Integer id){
        return find.where().eq("id", id).findUnique();
    }

    public boolean deleteAreaModel(Integer areaModelId) {

        // this may have an SQL Injection...
        System.out.println( "area model id" + areaModelId);

        String query = "DELETE FROM areamodel WHERE areamodel.id = " + areaModelId + ";";

        SqlUpdate deleteAreaModel = Ebean.createSqlUpdate(query);
        deleteAreaModel.execute();

        return true;

        //System.out.println("Row apagada com sucesso");
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
