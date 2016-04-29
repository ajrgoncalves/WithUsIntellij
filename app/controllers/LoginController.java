package controllers;

import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import Models.User;

import javax.inject.Inject;


public class LoginController extends Controller {

    @Inject
    FormFactory formFactory;


//    GET
    public  Result login() {

        return ok(views.html.users.login.render("Login"));
    }

//  POST
    public  Result authenticate() {

        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {

            return redirect(routes.LoginController.authenticate());
        }
        else
        {

            User user = loginForm.get();
        if(user.authenticate())
        {
            Logger.info("OK!");
            return redirect(routes.HomeController.index());
        }
        else
        {
            Logger.info("TEM ERROS!");
          return redirect(routes.LoginController.authenticate());
        }

        }

    }

    public static class Login extends User {

    }


    }

