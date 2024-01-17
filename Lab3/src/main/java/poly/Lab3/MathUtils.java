package poly.Lab3;

public class MathUtils {
	 public static int divide(int input1, int input2 ) throws Exception {
         if (input2 == 0) {
          throw new ArithmeticException("divide by zero");
         }
         return input1/input2;
     }
	 
}
