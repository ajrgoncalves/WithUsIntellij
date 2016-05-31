package controllers;


import Models.AreaModelUser;
import Models.User;

import java.util.List;

import static play.mvc.Results.ok;

/**
 * Created by Toz on 30/05/2016.
 */
public class ModelController {


    public play.mvc.Result getUserModules (String email){
        User user = User.find.where().eq("email", email).findUnique();

       List<AreaModelUser> areaModelList = User.findModulesByEmail(user.email);

        return ok(views.html.users.moduleUser.render(areaModelList));
    }

}
