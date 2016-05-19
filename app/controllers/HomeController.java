package controllers;

import Models.Country;
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


import javax.inject.Inject;
import java.sql.Date;
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
        List<Country> allCountries = Country.getAllCountries();

        Logger.info("Role: " + role);

        return ok(views.html.index.render(
                user, role, allUsers, allCountries
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
                session("id", user.getId().toString());
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
        List<Country> allCountries = Country.getAllCountries();
        return ok(views.html.users.register.render(allCountries));

    }


//    Post

    public Result create() {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {

            Logger.info("Criação de user com erros!!! ");
        } else {

            User user = userForm.get();
            user.password = BCrypt.hashpw(user.password, BCrypt.gensalt());
            session().put("email", user.email);

            Logger.info("USER: " + user.age.toString());
            user.save();
            Logger.info("USER: " + user.toString());


        }
        return redirect("/users/login");
    }

    public Result getAllUsers() {
        List<User> allUsers = User.getAllUsers();
        return ok(views.html.users.allUsers.render(allUsers));

    }

    @Security.Authenticated(LoginController.Secured.class)
    public Result updateUser(Long userId) {

        Form<User> userUpdateForm = formFactory.form(User.class).bindFromRequest();

        if (userUpdateForm.hasErrors()) {
            Logger.info("TEM ERROS!");
        } else {
            //Saber qual é o user que estamos a alterar
            User user = User.find.where().eq("id", userId).findUnique();
            Integer currentUserRole = Integer.parseInt(session().get("idRole"));

            if (user != null) {


                if (currentUserRole == Role.SUPERADMIN || currentUserRole == Role.ADMIN || (currentUserRole == Role.USER && userId == Integer.parseInt(session().get("id")))) {

                    //TODO: update de campos USER

                    if (userId == Integer.parseInt(session().get("id")) || user.idRole > currentUserRole) {

                        user.setName((userUpdateForm.data().get("name")));
                        user.setLastName((userUpdateForm.data().get("lastName")));
                        user.setHomeAddress((userUpdateForm.data().get("homeAddress")));
                        user.setCountryId(Integer.parseInt(userUpdateForm.data().get("countryId")));

                        user.setAgeStringFormat(userUpdateForm.data().get("age"));

                        //userUpdateForm.data().get("age"));  //TODO: verificar este set date

                        user.setPhoneNumber(Integer.parseInt(userUpdateForm.data().get("phoneNumber")));
                    } else {
                        flash("Não tem permissões para o que esta a tentar fazer");
                    }

                }

                if (currentUserRole == Role.SUPERADMIN || (currentUserRole == Role.ADMIN && userId == Integer.parseInt(session().get("id")))) {

                    if (userId == Integer.parseInt(session().get("id")) || user.idRole > currentUserRole) {

                    } else {
                        flash("Não tem permissões para o que esta a tentar fazer");
                    }
                }

                if (currentUserRole == Role.SUPERADMIN && userId == Integer.parseInt(session().get("id"))) {

                    if (userId == Integer.parseInt(session().get("id")) || user.idRole > currentUserRole) {
                        user.setIdRole(Integer.parseInt(userUpdateForm.data().get("idRole")));

                    } else {
                        flash("Não tem permissões para o que esta a tentar fazer");
                    }
                }

                user.save();
            }
        }
        return redirect(HomeController.index());
    }
}
