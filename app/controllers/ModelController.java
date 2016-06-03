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

import static play.mvc.Controller.flash;
import static play.mvc.Http.Context.Implicit.session;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by Toz on 30/05/2016.
 */
public class ModelController {

    @Inject
    FormFactory formFactory;

    public Integer currentUserRole = Integer.parseInt(session().get("idRole"));

    //GET
    @Security.Authenticated(LoginController.Secured.class)
    public Result getUserModules(Long userId) {

        if (currentUserRole == Role.SUPERADMIN) {

            User user = User.findByID(userId);
            List<AreaModel> areaModelList = AreaModel.getAllModels();
            List<AreaModelUser> areaModelUserList = User.findAreaModulesById(userId);
            ModuleList moduleList = new ModuleList();
            for (AreaModel areaModel : areaModelList) {
                boolean b = false;
                for (AreaModelUser areaModelUser : areaModelUserList) {
                    if (areaModelUser.getAreaModelId() == areaModel.getId()) {
                        moduleList.addModule(areaModel, true);
                        b = true;
                        break;
                    }
                }
                if (!b) {
                    moduleList.addModule(areaModel, false);
                }
            }
            return ok(views.html.users.modules.associateUserModels.render(
                    user, areaModelList, areaModelUserList, moduleList

            ));
        } else {
            return null;
        }
    }

    //GET
    @Security.Authenticated(LoginController.Secured.class)
    public Result getCreateModule() {

        if (currentUserRole == Role.SUPERADMIN) {
            AreaModel areaModel = new AreaModel();
            return ok(views.html.users.modules.createModule.render(areaModel));
        } else {
            return null;
        }
    }

    //POST
    @Security.Authenticated(LoginController.Secured.class)
    public Result createModule() {

        if (currentUserRole == Role.SUPERADMIN) {
            Form<AreaModel> moduleForm = formFactory.form(AreaModel.class).bindFromRequest();
            if (moduleForm.hasErrors()) {

                Logger.info("Criação de module com erros!!! ");
            } else {
                AreaModel areaModel = moduleForm.get();

                areaModel.save();
            }
            //TODO: Alterar este redirect
            return redirect(routes.ModelController.getModules());
        } else {
            return null;
        }
    }


    //GET All Modules
    @Security.Authenticated(LoginController.Secured.class)
    public Result getModules() {

        if (currentUserRole == Role.SUPERADMIN) {
            List<AreaModel> allAreaModel = AreaModel.getAllModels();

            return ok(views.html.users.modules.allModules.render(allAreaModel));
        } else {
            return null;
        }
    }


    //GET Update Modules
    @Security.Authenticated(LoginController.Secured.class)
    public Result getUpdateModule(Integer areaModelId) {

        if (currentUserRole == Role.SUPERADMIN) {
            AreaModel areaModel = AreaModel.find.where().eq("id", areaModelId).findUnique();
            List<AreaModel> allAreaModel = AreaModel.getAllModels();

            return ok(views.html.users.modules.updateModules.render(areaModel, allAreaModel));
        } else {
            return null;
        }
    }

    //POST Update Modules
    @Security.Authenticated(LoginController.Secured.class)
    public Result updateModule(Integer areaModelId) {

        if (currentUserRole == Role.SUPERADMIN) {
            Form<AreaModel> moduleForm = formFactory.form(AreaModel.class).bindFromRequest();
            if (moduleForm.hasErrors()) {

                Logger.info("Update de module com erros!!! ");
            } else {
                AreaModel areaModel = AreaModel.find.where().eq("id", areaModelId).findUnique();

                areaModel.setDescription(moduleForm.data().get("description"));

                areaModel.save();
            }
            return redirect(routes.ModelController.getModules());
        } else {
            return null;
        }
    }

    @Security.Authenticated(LoginController.Secured.class)
    public Result getDeleteModule(Integer areaModelId) {

        if (currentUserRole == Role.SUPERADMIN) {
            AreaModel areaModel = AreaModel.find.where().eq("id", areaModelId).findUnique();
            List<AreaModel> allAreaModel = AreaModel.getAllModels();

            return ok(views.html.users.modules.deleteModule.render(areaModel, allAreaModel));
        } else {
            return null;
        }
    }

    //POST
    @Security.Authenticated(LoginController.Secured.class)
    public Result deleteModule(Integer areaModelId) {

        if (currentUserRole == Role.SUPERADMIN) {
            Form<AreaModel> moduleForm = formFactory.form(AreaModel.class).bindFromRequest();
            if (moduleForm.hasErrors()) {
                Logger.info("Delete de module com erros!!! ");
            } else {
                AreaModel areaModel = AreaModel.find.where().eq("id", areaModelId).findUnique();

                areaModel.deleteAreaModel(areaModelId);
            }
            return redirect(routes.ModelController.getModules());
        } else {
            return null;
        }
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
    public Result addModelUser(Long userId) {

        if (currentUserRole == Role.SUPERADMIN) {

            Form<AreaModelUser> areaModelUserForm = formFactory.form(AreaModelUser.class).bindFromRequest();
            if (areaModelUserForm.hasErrors()) {
                Logger.info("Associação entre o user e o modulo com erros!!! ");
            } else {

                User user = User.findByID(userId);
                for (AreaModel am : AreaModel.getAllModels()) {
                    if (areaModelUserForm.data().containsKey("description-" + am.getId())) {
                        if (!user.hasModule(am.getId())) {
                            AreaModelUser areaModelUser = new AreaModelUser();
                            areaModelUser.setUserId(userId);
                            areaModelUser.setAreaModelId(am.id); // prego?
                            areaModelUser.save();
                        }
                    } else {
                        if (user.hasModule(am.getId())) {
                            // give it back to me, bitch (DELETE)

                            List<AreaModelUser> areaModelUserList = User.findAreaModulesById(userId);
                            for (AreaModelUser amuser : areaModelUserList) {
                                if (amuser.getAreaModelId() == am.id) {
                                    amuser.deleteRow(userId, am.id);
                                    break;
                                }
                            }
                            //boolean deletedRows = areaModelUser.deleteRow(userId, am.getId());
                        }
                    }
                }
            }
            //TODO: Verificar este redirect
            return redirect(routes.ModelController.getUserModules(userId));
        } else {
            return null;
        }
    }
}