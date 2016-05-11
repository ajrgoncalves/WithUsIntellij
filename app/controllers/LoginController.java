package controllers;

import Models.Role;
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
import javax.xml.ws.spi.http.HttpContext;


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
            return redirect(routes.HomeController.login());
        }


        public static boolean isMemberOf(Long role) {
            return Role.isMember(
                    role,
                    Http.Context.current().request().username()
            );
        }
    }
    public String getidRole(Http.Context ctx) {return ctx.session().get("idRole");}




}



