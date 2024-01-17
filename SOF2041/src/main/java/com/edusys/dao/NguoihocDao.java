/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.NguoiHoc;
import com.edusys.utils.Jdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOANG HUU
 */
public class NguoihocDao extends dao<NguoiHoc, String> {
	final String INSERT_SQL = "INSERT INTO NguoiHoc(MaNH, HoTen, GioiTinh, NgaySinh, DienThoai, Email, GhiChu, MaNV) VALUES(?,?,?,?,?,?,?,?)";
	final String UPDATE_SQL = "UPDATE NguoiHoc SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, DienThoai = ?, Email = ?, GhiChu = ?, MaNV = ? WHERE MaNH = ?";
	final String DELETE_SQL = "DELETE FROM NguoiHoc WHERE MaNH = ?";
	final String SELECT_ALL_SQL = "SELECT * FROM NguoiHoc";
	final String SELECT_BY_ID_SQL = "SELECT * FROM NguoiHoc WHERE MaNH = ?";
	final String SELECT_Not_In_Course = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ? and MaNH NOT IN(SELECT MaNH FROM HocVien WHERE MaKH= ?)";

	public void insert(NguoiHoc entity) {
		Jdbc.update(INSERT_SQL, entity.getMaNH(), entity.getHoTen(), entity.isGioiTinh(),
				new java.sql.Date(entity.getNgaySinh().getTime()), entity.getDienThoai(), entity.getEmail(),
				entity.getGhiChu(), entity.getMaNV());
	}

	@Override
	public void update(NguoiHoc entity) {
		Jdbc.update(UPDATE_SQL, entity.getHoTen(), entity.isGioiTinh(),
				new java.sql.Date(entity.getNgaySinh().getTime()), entity.getDienThoai(), entity.getEmail(),
				entity.getGhiChu(), entity.getMaNV(), entity.getMaNH());
	}

	@Override
	public void delete(String id) {
		Jdbc.update(DELETE_SQL, id);
	}

	@Override
	public List<NguoiHoc> selectAll() {
		return this.selectBysql(SELECT_ALL_SQL);
	}

	@Override
	public NguoiHoc selectById(String id) {
		List<NguoiHoc> list = this.selectBysql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<NguoiHoc> selectBysql(String sql, Object... args) {
		List<NguoiHoc> list = new ArrayList<NguoiHoc>();
		try {
			ResultSet rs = Jdbc.query(sql, args);
			while (rs.next()) {
				NguoiHoc nh = new NguoiHoc();
				nh.setMaNH(rs.getString("MaNH"));
				nh.setHoTen(rs.getString("HoTen"));
				nh.setGioiTinh(rs.getBoolean("GioiTinh"));
				nh.setNgaySinh(rs.getDate("NgaySinh"));
				nh.setDienThoai(rs.getString("DienThoai"));
				nh.setEmail(rs.getString("Email"));
				nh.setGhiChu(rs.getString("GhiChu"));
				nh.setMaNV(rs.getString("MaNV"));
				nh.setNgayDK(rs.getDate("NgayDK"));
				list.add(nh);
			}
			rs.getStatement().getConnection().close();
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<NguoiHoc> selectByKeyword(String keyword) {
		String sql = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
		return selectBysql(sql, "%" + keyword + "%");
	}

	public List<NguoiHoc> selectNotInCourse(int maKH, String keyword) {
		return selectBysql(SELECT_Not_In_Course, "%" + keyword + "%", maKH);
	}

}
