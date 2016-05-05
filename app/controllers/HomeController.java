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

//        String username = session().get("email");
//        Http.Context.current().args.put("username", username);
//        return ok(views.html.index.render(User.findByEmail(session().get("email"))));

        return ok(views.html.index.render(User.findByEmail(request().username())));
    }

    public Result getUsers() {

        Model.Finder<String, User> finder = new Model.Finder<>(User.class);
        List<User> users = finder.all();

        return ok(toJson(users));
    }

    public static class Login extends User {

    }

    //    GET
    public Result login() {

        return ok(views.html.users.login.render("Login"));
    }

    //  POST\ Handle login form submission

    public Result authenticate() {

        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {

            flash("error", "tem erros no formulario");
            return redirect(routes.HomeController.index());
        } else {

            User user = loginForm.get();


            //BCrypt.checkpw(user.password, user.password);
            if (user.authenticate()) {
                session().clear();
                session("email", loginForm.get().email);
                Logger.info("OK!");
                flash("success", "Está logado com sucesso");

                return redirect(routes.HomeController.index());
            } else {
                Logger.info("TEM ERROS!");
                return redirect(routes.HomeController.index());
            }

        }
    }

    //        GET Logout

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.HomeController.login()
        );
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
