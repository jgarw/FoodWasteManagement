package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cst8288.finalproject.controller.UserDAOImpl;
import com.cst8288.finalproject.model.User;
import com.cst8288.finalproject.model.UserFactory;


public class LoginTest {
	
	@Test
	public void loginTest() {
		User testUser = UserFactory.createUser("consumer", "test", "test@gmail.com", "test123", "123456789");

		UserDAOImpl dao = new UserDAOImpl();
		dao.insertUser(testUser);
		
		User authenticatedUser = dao.authUser(testUser.getEmail(), testUser.getPassword());
		Assertions.assertNotNull(authenticatedUser);
	}
}
