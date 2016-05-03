package controllers;

import play.Logger;
import play.api.libs.openid.UserInfo;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import Models.User;
import play.mvc.Security;

import javax.inject.Inject;


public class LoginController extends Controller {

    @Inject
    FormFactory formFactory;


    //    GET
    public Result login() {

        return ok(views.html.users.login.render("Login"));
    }

    //  POST\
    public Result authenticate() {

        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {

            flash("error", "tem erros no formulario");
            return redirect(routes.HomeController.index());
        } else {
            User user = loginForm.get();
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
                routes.HomeController.index()
        );
    }
    public static class Secured extends Security.Authenticator {

        @Override
        public String getUsername(Http.Context ctx) {
            return ctx.session().get("email");
        }

        @Override
        public Result onUnauthorized(Http.Context ctx) {
            return redirect(routes.LoginController.login());
        }
    }



    public static class Login extends User {

    }
}



