package controllers;

import com.avaje.ebean.Model;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import Models.Role;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;

import java.util.List;

import static play.data.Form.form;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Created by Toz on 05/05/2016.
 */

@Security.Authenticated(LoginController.Secured.class)
public class RoleController extends Model {

    @Inject
    FormFactory formFactory;



}
