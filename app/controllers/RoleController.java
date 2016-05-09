package controllers;

import com.avaje.ebean.Model;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import Models.Role;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;

import static play.data.Form.form;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by Toz on 05/05/2016.
 */

@Security.Authenticated(LoginController.Secured.class)
public class RoleController extends Model {

    @Inject
    FormFactory formFactory;


    public Result create(){

        Form<Role> roleForm = formFactory.form(Role.class).bindFromRequest();
        if (roleForm.hasErrors()) {
            Logger.info("Tem erros !!!");
        } else {
            Role newRole = roleForm.get();
            newRole.save();


        }
        return redirect("/users/login");

    }

// rename role  -- falta if(Secured.isMemberOf(role))

    public  static Result rename (String role) {
        return ok(Role.rename(role,
                form().bindFromRequest().get("name")));
    }

//Delete Role -- falta if(Secured.isMemberOf(role))

    public static Result role (String role){
        Role.find.ref(role).delete();
        return ok();
        }


}
