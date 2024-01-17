package poly.Lab3;
import  static org.hamcrest.CoreMatchers.instanceOf;
import  static org.junit.Assert.assertEquals;
import  static org.junit.Assert.assertThat;
import  static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
public class TestMathUtils {
	@Test(expected = ArithmeticException.class)
    public void testMathUtils1() throws Exception {
         MathUtils.divide(1, 0);
    }

    @Test
    public void testMathUtils2() throws Exception {
        try {
            MathUtils.divide(1, 0);
            fail("Not throw exception");
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
            assertEquals(e.getMessage(), "divide by zero");
        }
    }

    @Test
    public void testMathUtils3() throws Exception {
        try {
             MathUtils.divide(1, 1);
        } catch (Exception e) {
             fail("Not throw exception");
        }
   }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldTestExceptionMessage() throws Exception{
        thrown.expect(ArithmeticException.class);
        thrown.expectMessage("divide by zero");
        MathUtils.divide(1, 0);
    }
}
