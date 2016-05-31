package controllers;

import Models.Country;
import Models.UserRegistryAlteration;
import Models.Role;
import Models.User;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


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
        if(session().get("email") != null) {
            Logger.info("USERNAME: " + request().username());

            User user = User.findByEmail(request().username());
            Role role = Role.findRole(user.getIdRole());
            List<User> allUsers = User.getAllUsers();
            List<Country> allCountries = Country.getAllCountries();
            List<Role> allRoles = Role.getAllRoles();

            Logger.info("Role: " + role);

            return ok(views.html.index.render(
                    user, role, allUsers, allCountries, allRoles
            ));
        }else{
            return ok(views.html.users.login.render(""));
        }
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
                Logger.info("TEM ERROS no login!");
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
        List<Role> allRoles = Role.getAllRoles();

        User user = new User();
        return ok(views.html.users.register.render(
                user, allCountries, allRoles
        ));

    }


//    Post
    @Transactional
    public Result create() {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {

            Logger.info("Criação de user com erros!!! ");
            return redirect("/users/register");
        } else {

            User user = userForm.get();
            if(user.isValid()) {
                user.password = BCrypt.hashpw(user.password, BCrypt.gensalt());
                user.idRole= Role.GUEST;
                user.save();
                //cria um novo registo na tabela
                user = User.findByEmail(user.email);
                UserRegistryAlteration newUserRegistryAlteration = new UserRegistryAlteration(user);

                Ebean.execute(() ->{
                    System.out.println(Ebean.currentTransaction());

                    newUserRegistryAlteration.save();

                });
            }

            return redirect(HomeController.login());
        }

    }

//    GET
    @Security.Authenticated(LoginController.Secured.class)
    public Result getAllUsers() {
        List<User> allUsers = User.getAllUsers();
        List<Role> allRoles = Role.getAllRoles();
        return ok(views.html.users.allUsers.render(allUsers, allRoles));

    }

//    GET update User

    public Result getUpdateUser(Long userId){
        if(session().get("email") != null) {
            Logger.info("USERNAME: " + request().username());

            User user = User.find.where().eq("id", userId).findUnique();
            Role role = Role.findRole(user.getIdRole());
            List<User> allUsers = User.getAllUsers();
            List<Country> allCountries = Country.getAllCountries();
            List<Role> allRoles = Role.getAllRoles();

            Logger.info("Role: " + role);

            return ok(views.html.users.updateUser.render(user, role, allUsers, allCountries, allRoles));

        }else{
            return ok(views.html.users.login.render(""));
        }

    }

        //POST
    @Transactional
    @Security.Authenticated(LoginController.Secured.class)
    public Result updateUser(Long userId) {

        Form<User> userUpdateForm = formFactory.form(User.class).bindFromRequest();

        if (userUpdateForm.hasErrors()) {
            Logger.info("TEM ERROS!");
        } else {

            //Saber qual é o user que estamos a alterar
            User user = User.find.where().eq("id", userId).findUnique();
            Integer currentUserRole = Integer.parseInt(session().get("idRole"));
            User updater = User.findByEmail(session().get("email"));

            if (user != null) {
                if(user.isValid()) {

                    if (currentUserRole == Role.SUPERADMIN || currentUserRole == Role.ADMIN || (currentUserRole == Role.USER && userId == Integer.parseInt(session().get("id")))) {

                        //TODO: update de campos USER

                        if (userId == Integer.parseInt(session().get("id")) || user.idRole > currentUserRole) {

                            user.setName((userUpdateForm.data().get("name")));
                            user.setLastName((userUpdateForm.data().get("lastName")));
                            user.setHomeAddress((userUpdateForm.data().get("homeAddress")));
                            user.setCountryId(Integer.parseInt(userUpdateForm.data().get("countryId")));
                            user.setAgeStringFormat(userUpdateForm.data().get("age"));
                            user.setPhoneNumber(Integer.parseInt(userUpdateForm.data().get("phoneNumber")));
                            if((!(userUpdateForm.data().get("password").isEmpty())) && userUpdateForm.data().get("password").matches(userUpdateForm.data().get("confirmPassword"))) {
                                user.setPassword(BCrypt.hashpw(userUpdateForm.data().get("password"), BCrypt.gensalt()));
                            }
                        } else {
                            flash("Não tem permissões para o que esta a tentar fazer");
                        }

                    }

                    if (currentUserRole == Role.SUPERADMIN || (currentUserRole == Role.ADMIN && userId == Integer.parseInt(session().get("id")))) {

                        if (userId == Integer.parseInt(session().get("id")) || user.idRole > currentUserRole) {
                            user.setIdRole(Integer.parseInt(userUpdateForm.data().get("idRole")));
                        } else {
                            flash("Não tem permissões para o que esta a tentar fazer");
                        }
                    }

                    if (currentUserRole == Role.SUPERADMIN && userId == Integer.parseInt(session().get("id"))) {

                        if (userId == Integer.parseInt(session().get("id")) || user.idRole > currentUserRole) {

                        } else {
                            flash("Não tem permissões para o que esta a tentar fazer");
                        }
                    }
                    UserRegistryAlteration newUserRegistryAlteration = new UserRegistryAlteration(updater,user);

                    Ebean.execute(() ->{
                        System.out.println(Ebean.currentTransaction());
                        user.save();
                        newUserRegistryAlteration.save();

                    });

                }
            }
        }
        return redirect(HomeController.index());
    }
}



