package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toz on 01/06/2016.
 */
public class ModuleList {
    public List<AreaModel> modelList;
    public List<Boolean> modelAccess;
    public ModuleList(){
        modelList = new ArrayList<>();
        modelAccess = new ArrayList<>();
    }

    public void addModule(AreaModel a, boolean b){
        modelList.add(a);
        modelAccess.add(b);
    }
}
