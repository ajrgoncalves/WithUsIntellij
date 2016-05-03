package controllers;

import Models.User;
import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

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

    @Security.Authenticated(LoginController.Secured.class)
    public Result index() {

        String username = session().get("email");

        Http.Context.current().args.put("username", username);

        return ok(views.html.index.render(""));
    }

    public Result getUsers() {

        Model.Finder<String, User> finder = new Model.Finder<>(User.class);
        List<User> users = finder.all();

        return ok(toJson(users));
    }

//  GET

    public Result register() {

        return ok(views.html.users.register.render(""));



    }
//    Post

    public Result create() {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            Logger.info("TEM ERROS!");
        } else {

            User user = userForm.get();
            user.password = BCrypt.hashpw(user.password, BCrypt.gensalt());
            session().put("email", user.email);

            user.save();
            Logger.info("USER: " + user.toString());
            //Logger.info("### User: " + user.name + " lastName: " + user.lastName);

        }
        return redirect("/users/register");
    }

}
