package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.cst8288.finalproject.model.User;
import com.cst8288.finalproject.model.UserFactory;

/**
 * Test class for the UserFactory class, used to create one of three types of users
 */
public class UserFactoryTest {
	/**
	 * Tests that "user" is returned as null in createUser when an invalid user type is provided
	 */
	@Test
	public void userFactoryTest() {
		User user = UserFactory.createUser("invalid user type", "user", "user", "user", "user");
		Assertions.assertNull(user);
	}
}
