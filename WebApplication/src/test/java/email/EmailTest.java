package email;

import org.junit.jupiter.api.Test;

import com.cst8288.finalproject.controller.RetailerSubject;

public class EmailTest {

	@Test
	public void emailTest() {
	RetailerSubject subject = new RetailerSubject();

	subject.notifyObservers("Test message to ensure email sends");
	}
}
