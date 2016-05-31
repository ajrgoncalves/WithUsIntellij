package controllers;


import Models.AreaModel;
import Models.Role;
import Models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.List;

import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by Toz on 30/05/2016.
 */
public class ModelController {

    @Inject
    FormFactory formFactory;

    //GET
    @Security.Authenticated(LoginController.Secured.class)
    public Result getUserModules(Long userId) {

        User user = User.findByID(userId);
        List<AreaModel> areaModelList = User.findModulesByEmail(userId);

        return ok(views.html.users.moduleUser.render(
                areaModelList, user

        ));
    }

    //GET
    @Security.Authenticated(LoginController.Secured.class)
    public Result getCreateModule() {
        AreaModel areaModel = new AreaModel();
        return ok(views.html.users.modules.createModule.render(areaModel));
    }

    //POST
    @Security.Authenticated(LoginController.Secured.class)
    public Result createModule() {
        Form<AreaModel> moduleForm = formFactory.form(AreaModel.class).bindFromRequest();
        if (moduleForm.hasErrors()) {

            Logger.info("Criação de module com erros!!! ");
        } else {
            AreaModel areaModel = moduleForm.get();

            areaModel.save();
        }
        //TODO: Alterar este redirect
        return redirect("/users/modules/createModule");
    }


    //GET All Modules

    public Result getModules(){
        List<AreaModel> allAreaModel = AreaModel.getAllModels();

        return ok(views.html.users.modules.allModules.render(allAreaModel));
    }
}
