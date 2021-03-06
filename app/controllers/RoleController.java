package controllers;

import Models.User;
import com.avaje.ebean.Model;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import Models.Role;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;

import java.util.List;

import static controllers.routes.HomeController;
import static play.data.Form.form;
import static play.mvc.Controller.flash;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by Toz on 05/05/2016.
 */

@Security.Authenticated(LoginController.Secured.class)
public class RoleController extends Model {

    @Inject
    FormFactory formFactory;


    @Security.Authenticated(LoginController.Secured.class)
    public Result setRole(Long userId) {
// vou buscar a informação que está no form
        Form<User> roleForm = formFactory.form(User.class).bindFromRequest();
        if (roleForm.hasErrors()) {
            Logger.info("TEM ERROS no Role!");
        } else {
            //escolhe o current user(ou o user que esta na linha)
            User user = User.find.where().eq("id", userId).findUnique();
            if(user!= null) {
                //faz set do idRole consoante o que está no form
                user.setIdRole(Integer.parseInt(roleForm.data().get("idRole")));
                user.update();
            }else{
                flash("error", "user not found");
            }
        }
        return redirect(HomeController.index());
    }



}
