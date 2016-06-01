package controllers;


import Models.*;
import com.avaje.ebean.annotation.Transactional;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.List;

import static play.mvc.Controller.request;
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
    public Result  getUserModules(Long userId) {

        User user = User.findByID(userId);
        List<AreaModel> areaModelList = AreaModel.getAllModels();
        List<AreaModelUser> areaModelUserList = User.finAreaModulesByEmail(userId);
        ModuleList m = new ModuleList();
        for(AreaModel areaModel : areaModelList){
            boolean b = false;
            for(AreaModelUser areaModelUser : areaModelUserList){
                if(areaModelUser.getAreaModelId() == areaModel.getId()){
                    m.addModule(areaModel, true);
                    b = true;
                    break;
                }
            }
            if(!b){
                m.addModule(areaModel, false);
            }
        }
        return ok(views.html.users.modules.associateUserModels.render(
                user, areaModelList, areaModelUserList, m

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

    public Result getModules() {
        List<AreaModel> allAreaModel = AreaModel.getAllModels();

        return ok(views.html.users.modules.allModules.render(allAreaModel));
    }


    //GET Update Modules

    public Result getUpdateModule(Integer areaModelId) {
        AreaModel areaModel = AreaModel.find.where().eq("id", areaModelId).findUnique();
        List<AreaModel> allAreaModel = AreaModel.getAllModels();

        return ok(views.html.users.modules.updateModules.render(areaModel, allAreaModel));
    }

    //POST Update Modules

    public Result updateModule(Integer areaModelId) {

        Form<AreaModel> moduleForm = formFactory.form(AreaModel.class).bindFromRequest();
        if (moduleForm.hasErrors()) {

            Logger.info("Update de module com erros!!! ");
        } else {
            AreaModel areaModel = AreaModel.find.where().eq("id", areaModelId).findUnique();

            areaModel.setDescription(moduleForm.data().get("description"));

            areaModel.save();
        }
        return redirect(routes.ModelController.getModules());

    }



////GET
//    public Result getaddModelUser(){
//        List<AreaModel> allAreaModel = AreaModel.getAllModels();
//        List<User> users = User.getAllUsers();
//        User user = User.findByEmail(request().username());
//
//        return ok(views.html.users.modules.associateUserModels.render(user, allAreaModel));
//    }

    //POST
    @Transactional
    @Security.Authenticated(LoginController.Secured.class)
    public Result addModelUser() {
        Form<AreaModelUser> areaModelUserForm = formFactory.form(AreaModelUser.class).bindFromRequest();
        if (areaModelUserForm.hasErrors()) {

            Logger.info("Associação entre o user e o modulo com erros!!! ");
        } else {
            AreaModelUser areaModelUser = areaModelUserForm.get();

            areaModelUser.save();

        }
        //TODO: Verificar este redirect
        return redirect(routes.ModelController.getModules());
    }
}
