import Models.Country;
import Models.User;
import org.junit.Test;
import play.twirl.api.Content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Controller.request;
import Models.Role;

import views.html.*;

import java.util.List;

/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }
    User user = User.findByEmail(request().username());
    Role role = Role.findRole(user.getIdRole());
    List<User> allUsers = User.getAllUsers();
    List<Country> allCountries = Country.getAllCountries();
    List<Role> allRoles = Role.getAllRoles();

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render( User.findByEmail(request().username()),
                Role.findRole(user.getIdRole()),
                allUsers = User.getAllUsers(),
                allCountries = Country.getAllCountries(),
                allRoles = Role.getAllRoles()

        );
        assertEquals("text/html", html.contentType());
        assertTrue(html.body().contains("Your new application is ready."));
    }


}
