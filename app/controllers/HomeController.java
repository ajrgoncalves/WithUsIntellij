package controllers;

import Models.Role;
import Models.User;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;

import javax.inject.Inject;

import java.util.List;

import static controllers.routes.*;
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
        Logger.info("USERNAME: " + request().username());

        User user = User.findByEmail(request().username());
        Role role = Role.findRole(user.getIdRole());
        List<User> allUsers = User.getAllUsers();

        Logger.info("Role: " + role);

        return ok(views.html.index.render(
                user,role,allUsers
        ));
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
            return redirect(HomeController.index());
        } else {

            User user = loginForm.get(); // FIXME: tira esta merda - ir buscar directamente ao form

            user = User.authenticate(user.getEmail(), user.getPassword());
            if (user != null) {
                Model.Finder<String, Role> finderRole = new Model.Finder<>(Role.class);

                session().clear();
                session("email", loginForm.get().email);
                session("idRole", Long.toString(user.getIdRole()));

                Role role = finderRole.where().eq("id", User.findByEmail(user.email).getIdRole()).findUnique();

                if (role == null) {
                    Logger.info("TEM ERROS1!");
                    return redirect(HomeController.index());
                } else {
                    Logger.info("previleges " + role.rolePrivileges);

                    if (role.rolePrivileges.equals("admin")) {
                        Logger.info("OK!");
                        flash("success", "Está logado com sucesso");

                        return redirect(HomeController.index());
                    } else {
                        Logger.info(" USer!");
                        return redirect(HomeController.index());
                    }
                }

            } else {
                Logger.info("TEM ERROS3!");
                return redirect(HomeController.index());
            }

        }
    }

    //        GET Logout

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                HomeController.login()
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
        return redirect("/users/login");
    }

    public Result getAllUsers() {
        List<User> allUsers = User.getAllUsers();
        return ok(views.html.users.allUsers.render(allUsers));

    }

    @Security.Authenticated(LoginController.Secured.class)
    public Result setRole(Long userId) {

        Form<User> roleForm = formFactory.form(User.class).bindFromRequest();
        if (roleForm.hasErrors()) {
            Logger.info("TEM ERROS!");
        } else {

            if( session().get("idRole").equals( Role.ADMIN))
            {
                User userRole =  roleForm.get();
                userRole.update();
            }

        }
        return redirect(HomeController.index());
    }

}
