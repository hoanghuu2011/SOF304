package poly.Lab3;

import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PersonTest {
	// Sử dụng “ExpectedException Rule” để theo dõi ngoại lệ
		@Rule
		public ExpectedException expectException = ExpectedException.none();

		// Kiểm thử phương thức age<0
		@Test
		public void testExpectedException() {
			expectException.expect(IllegalArgumentException.class);
			new Person("Person", -1);
		}

		// Kiểm thử mong muốn
		@Test(expected = IllegalArgumentException.class)
		public void testExpectedException2() {
			new Person("Person", -1);
		}

		// Sử dụng “try-catch” để theo dõi ngoại lệ
		@Test
		public void testExpectedException3() {
			try {
				new Person("Person", -1);
				fail("Should have thrown an IllegalArgumentException because is invalid");
			} catch (IllegalArgumentException e) {
			}
		}

}
