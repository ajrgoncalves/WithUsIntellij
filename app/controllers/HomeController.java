package controllers;

import javax.inject.Inject;


import com.avaje.ebean.Model;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import Models.User;

import java.util.List;

import static play.data.Form.form;
import static play.libs.Json.toJson;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {



    @Inject
    FormFactory formFactory;


    public Result index() {
        return ok(views.html.index.render("Your new application is ready."));
    }



    public Result getUsers(){

    	Model.Finder<Integer, User> finder = new Model.Finder<>(User.class);
    	List<User> users = finder.all();

    	return ok(toJson(users));
    }

    @Transactional
    public Result register(){
        //User usr = formFactory.form(User.class).bindFromRequest().data().get("test");
        User usr = Form.form(User.class).bindFromRequest().get();
        Logger.info("### USER: " + usr.name + " lastName: " + usr.lastName);
        usr.save();
//
//        User uData = form.get();

        return ok(views.html.register.render(""));

    }



    //Login GET
    public Result login(){

        return ok(views.html.login.render(formFactory.form(Login.class)));
        //return ok(views.html.login.render(form(Login.class)));
    }

    //Login POST
    public Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()){
            return badRequest(views.html.login.render(loginForm));
        }else{
            session().clear();
            session("email", loginForm.get().email);
            return redirect(routes.HomeController.index());
        }
    }



//    public Result getUsers(){
//        return TODO;
//    }


    public Result createUser(){
        return TODO;
    }



    public static class Login {

        public static String email;
        public static String password;

//        public static String validate() {
//            if(User.authenticate(email, password) == null) {
//                return "Invalid User or Password";
//            }
//            return null;
//        }
    }


}
