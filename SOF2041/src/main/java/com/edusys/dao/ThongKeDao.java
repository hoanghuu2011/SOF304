/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.utils.Jdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOANG HUU
 */
public class ThongKeDao {
	private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
		try {
			List<Object[]> list = new ArrayList<>();
			ResultSet rs = Jdbc.query(sql, args);
			while (rs.next()) {
				Object[] vals = new Object[cols.length];
				for (int i = 0; i < cols.length; i++) {
					vals[i] = rs.getObject(cols[i]);
				}
				list.add(vals);
			}
			rs.getStatement().getConnection().close();
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Object[]> getBangDiem(Integer makh) {
		String sql = "{CALL sp_BangDiem(?)}";
		String[] cols = { "MaNH", "HoTen", "Diem" };
		return this.getListOfArray(sql, cols, makh);
	}

	public List<Object[]> getNguoiHoc() {
		List<Object[]> list = new ArrayList<>();
		try {
			ResultSet rs = null;
			try {
				String sql = "{call sp_ThongKeNguoiHoc}";
				rs = Jdbc.query(sql);
				while (rs.next()) {
					Object[] model = { rs.getInt("Nam"), rs.getInt("SoLuong"), rs.getDate("DauTien"),
							rs.getDate("CuoiCung") };
					list.add(model);

				}
			} finally {
				rs.getStatement().getConnection().close();

			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);

		}
		return list;

	}

	/*
	 * public List<Object[]> getDiemChuyenDe(){ String sql =
	 * "{CALL sp_DiemChuyenDe}"; String[] cols =
	 * {"ChuyenDe","SoHV","ThapNhat","CaoNhat","TrungBinh"}; return
	 * this.getListOfArray(sql, cols); }
	 */
	public List<Object[]> getDiemTheoChuyenDe() {
		List<Object[]> list = new ArrayList<>();
		try {
			ResultSet rs = null;
			try {
				String sql = "{call sp_ThongKeDiem}";
				rs = Jdbc.query(sql);
				while (rs.next()) {
					Object[] model = { rs.getString("ChuyenDe"), rs.getInt("SoHV"), rs.getDouble("ThapNhat"),
							rs.getDouble("CaoNhat"), rs.getDouble("TrungBinh") };
					list.add(model);
				}
			} finally {
				rs.getStatement().getConnection().close();
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return list;
	}

	// public List<Object[]> getDoanhThu(int nam){
	// String sql = "{CALL sp_DoanhThu(?)}";
	// String[] cols = {"ChuyenDe", "SoKH", "SoHV", "DoanhThu", "ThapNhat",
	// "CaoNhat", "TrungBinh"};
	// return this.getListOfArray(sql, cols, nam);
	// }
	public List<Object[]> getDoanhThu(int nam) {
		List<Object[]> list = new ArrayList<>();
		try {
			ResultSet rs = null;
			try {
				String sql = "{call sp_ThongKeDoanhThu (?)}";
				rs = Jdbc.query(sql, nam);
				while (rs.next()) {
					Object[] model = { rs.getString("ChuyenDe"), rs.getInt("SoKH"), rs.getInt("SoHV"),
							rs.getDouble("DoanhThu"), rs.getDouble("ThapNhat"), rs.getDouble("CaoNhat"),
							rs.getDouble("TrungBinh") };
					list.add(model);
				}
			} finally {
				rs.getStatement().getConnection().close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}

}
