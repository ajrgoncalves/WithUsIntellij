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

    public  Result login() {

        return ok(views.html.users.login.render(formFactory.form(Login.class)));
    }


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
        }
        else
        {
            Logger.info("TEM ERROS!");
        }



            //int res = user.authenticate(username,password)
//            if (res == 0)
//                redirect(pagina sucesso);
//                redirect(pagina_erro);
//            else


//            session().clear();
//            session("email", loginForm.get().email);
//            session("password",loginForm.get().password);

            return redirect(routes.HomeController.index());
        }
    }

    public static class Login extends User {

    }


    }

