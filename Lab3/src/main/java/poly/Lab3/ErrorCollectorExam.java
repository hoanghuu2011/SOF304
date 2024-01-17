package poly.Lab3;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import junit.framework.Assert;

public class ErrorCollectorExam {
	
		@Rule
		public ErrorCollector errorCollect = new ErrorCollector();

		@Test
		public void example() {
			errorCollect.addError(new Throwable("There is an error in first line"));
			errorCollect.addError(new Throwable("There is an error in second line"));
			System.out.println("Hello");
			try {
				Assert.assertTrue("A" == "B");
			} catch (Throwable t) {
				errorCollect.addError(t);
			}
			System.out.println("World !!!");
		}

}
