package poly.edu.SOFT204_Junit_01;

import static org.junit.Assert.*;



import static org.mockito.Mockito.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;


public class HinhChuNhatTest {
	
	@Test
	public void testTinhDienTich() {
		HinhChuNhat hcn = new HinhChuNhat(5, 10);
		assertEquals(50.0, hcn.tinhDienTich(), 0);
	}

	@Test
	public void testTinhChuVi() {
		HinhChuNhat hcn = new HinhChuNhat(5, 10);
		assertEquals(30, hcn.tinhChuVi(), 0);
	}
	 @Ignore // Bỏ qua phương thức này trong quá trình kiểm thử
	 @Test
	    public void testTinhChuVi2() {
	        HinhChuNhat hcn = new HinhChuNhat(5, 10);
	        double expected = 18; // Giá trị chu vi mong đợi
	        double actual = hcn.tinhChuVi();
	        assertEquals(expected, actual, 0.01);
	    }
}