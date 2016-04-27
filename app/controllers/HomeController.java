package controllers;

import Models.User;
import com.avaje.ebean.Model;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {


    @Inject
    FormFactory formFactory;


    public Result index() {
        return ok(views.html.index.render("Your new application is ready."));
    }

//    Get users, apresenta os dados que estao na bd
    public Result getUsers() {

        Model.Finder<Integer, User> finder = new Model.Finder<>(User.class);
        List<User> users = finder.all();

        return ok(toJson(users));
    }

//  GET
    public Result register() {

        return ok(views.html.users.register.render("register"));

    }
//    Post
    public Result create() {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            Logger.info("TEM ERROS!");
        } else {

            User user = userForm.get();
            user.save();
            Logger.info("USER: " + user.toString());
            //Logger.info("### User: " + user.name + " lastName: " + user.lastName);

        }
        return redirect("/users/register");
    }

}
