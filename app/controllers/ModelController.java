package controllers;


import Models.AreaModel;
import Models.AreaModelUser;
import Models.User;
import play.mvc.Result;

import java.util.List;

import static play.mvc.Results.ok;

/**
 * Created by Toz on 30/05/2016.
 */
public class ModelController {


    public Result getUserModules(String email) {


        List<AreaModel> areaModelList = User.findModulesByEmail(email);

        return ok(views.html.users.moduleUser.render(areaModelList));
    }

}
