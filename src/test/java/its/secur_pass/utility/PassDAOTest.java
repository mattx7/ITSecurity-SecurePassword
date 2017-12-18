package its.secur_pass.utility;

import its.secur_pass.enitities.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PassDAOTest {

    @Test
    public void testRegisterAUser() throws Exception {
        PassDAO dao = new PassDAO("test.csv");
        final User user = new User("name", "1234");
        dao.registerNewUser(user);
        Assert.assertTrue(dao.isRegisteredUser(user));
    }


}