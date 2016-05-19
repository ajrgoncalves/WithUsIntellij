package Models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Toz on 19/05/2016.
 */

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "countryId")
    public Integer countryId;

    @Column(name = "countryNome")
    public String countryNome;

    @Column(name = "countryName")
    public String countryName;

    private static Model.Finder<Integer, Country> find = new Model.Finder(Country.class);

    public static Country findCountry(Integer countryId) {
        return find.where().eq("id", countryId).findUnique();
    }

    // Procurar todos os countries

    public static List<Country> getAllCountries() {
        return find.all();
    }



    @Override
    public String toString() {
        return "Role " + countryId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryNome() {
        return countryNome;
    }

    public void setCountryNome(String countryNome) {
        this.countryNome = countryNome;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
