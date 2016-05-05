package controllers;

import org.mindrot.jbcrypt.BCrypt;
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




}



