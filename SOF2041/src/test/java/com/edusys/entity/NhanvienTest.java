package com.edusys.entity;

import static org.junit.Assert.*;

import javax.annotation.processing.Generated;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import junit.framework.Assert;

@Generated(value = "org.junit-tools-1.1.0")
public class NhanvienTest {
	Nhanvien nhanVien;

	@Before
	public void setUp() throws Exception {
		nhanVien = new Nhanvien();
	}

	@After
	public void tearDown() throws Exception {
		nhanVien = null;
	}

	private Nhanvien createTestSubject() {
		return new Nhanvien();
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

//	---------------------------------------------------------

	// Test MaNV
	@Test
	public void testMaNVNull() throws Exception {
		String MaNV = null;
		exception.expect(IllegalArgumentException.class);
		nhanVien.setMaNV(MaNV);
	}

	// Test MaNV
	@Test
	public void testMaNVRong() throws Exception {
		String MaNV = "";
		try {
			nhanVien.setMaNV(MaNV);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("maNV khong duoc de trong hoac rong", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test MaNV
	@Test
	public void testMaNVNgan() throws Exception {
		String MaNV = "1234";
		try {
			nhanVien.setMaNV(MaNV);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("maNV có 5-15 ký tự tự, chữ hoặc số", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test MaNV
	@Test
	public void testMaNVDai() throws Exception {
		String MaNV = "1234512345123451";
		try {
			nhanVien.setMaNV(MaNV);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("maNV có 5-15 ký tự tự, chữ hoặc số", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test MaNV
	@Test
	public void testMaNVSai() throws Exception {
		String MaNV = "Hieubq@";
		try {
			nhanVien.setMaNV(MaNV);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("maNV có 5-15 ký tự tự, chữ hoặc số", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test MaNV
	@Test
	public void testMaNVDung() throws Exception {
		String MaNV = "Hieubq";
		nhanVien.setMaNV(MaNV);
		assertEquals("Hieubq", nhanVien.getMaNV());
	}
	
	// Test MaNV
	@Rule
	public ErrorCollector collector = new ErrorCollector();
		@Test
		public void testMaNVDung2() throws Exception {
			
			
			collector.addError(new Throwable("Lỗi ở dòng 1"));
			collector.addError(new Throwable("Lỗi ở dòng 2"));
			nhanVien.setMaNV("Hieubq");
			try {
				Assert.assertTrue("Hieubq" ==  nhanVien.getMaNV());
			} catch (Throwable e) {
				collector.addError(e);
			}
		}

//	---------------------------------------------------------

	// Test HoTen
	@Test
	public void testHoTenNull() throws Exception {
		String HoTen = null;
		exception.expect(IllegalArgumentException.class);
		nhanVien.setHoTen(HoTen);
	}

	// Test HoTen
	@Test
	public void testHoTenRong() throws Exception {
		String HoTen = "";
		try {
			nhanVien.setHoTen(HoTen);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("hoTen không được null hoặc rỗng !", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test HoTen
	@Test
	public void testHoTenNgan() throws Exception {
		String HoTen = "Hi";
		try {
			nhanVien.setHoTen(HoTen);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("hoTen 3-50 ký tự và không chứa ký tự đặc biệt !", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test HoTen
	@Test
	public void testHoTenDai() throws Exception {
		String HoTen = "Bùi Quang Hiếu Bùi Quang Hiếu Bùi Quang Hiếu Bùi Quang Hiếu";
		try {
			nhanVien.setHoTen(HoTen);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("hoTen 3-50 ký tự và không chứa ký tự đặc biệt !", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test HoTen
	@Test
	public void testHoTenChuKyTuDacBiet() throws Exception {
		String HoTen = "Hiếu @";
		try {
			nhanVien.setHoTen(HoTen);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("hoTen 3-50 ký tự và không chứa ký tự đặc biệt !", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test HoTen
	@Test
	public void testHoTenDung() throws Exception {
		String HoTen = "Bùi Quang Hiếu";
		nhanVien.setHoTen(HoTen);
		assertEquals("Bùi Quang Hiếu", nhanVien.getHoTen());
	}

//			---------------------------------------------------------

	// Test Password
	@Test
	public void testPasswordNull() throws Exception {
		String Password = null;
		exception.expect(IllegalArgumentException.class);
		nhanVien.setMatkhau(Password);
	}

	// Test Password
	@Test
	public void testPasswordRong() throws Exception {
		String Password = "";
		try {
			nhanVien.setMatkhau(Password);
			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("matKhau không được null hoặc rỗng !", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test Password
	@Test
	public void testPasswordNgan() throws Exception {

		try {
			nhanVien.setMatkhau("123456");
			nhanVien.setMatkhau("1234567");

			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("matKhau trong khoảng 8-50 ký tự", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test Password
	@Test
	public void testPasswordDai() throws Exception {

		try {
			nhanVien.setMatkhau("12345123451234512345123451234512345123451234512345");
			nhanVien.setMatkhau("123451234512345123451234512345123451234512345123451");

			fail("Không bắt được ngoại lệ");
		} catch (IllegalArgumentException e) {
			assertEquals("matKhau trong khoảng 8-50 ký tự", e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	// Test Password
	@Test
	public void testPasswordDung() throws Exception {
		String Password = "hieubqph13812";
		nhanVien.setMatkhau(Password);
		assertEquals("hieubqph13812", nhanVien.getMatkhau());
	}

}