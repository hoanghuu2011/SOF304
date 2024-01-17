/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.Nhanvien;
import com.edusys.utils.Jdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOANG HUU
 */
public class NhanvienDao extends dao<Nhanvien, String> {
	final String INSERT_SQL = "INSERT INTO NhanVien(MaNV,MatKhau,HoTen,VaiTro) values(?,?,?,?)";
	final String UPDATE_SQL = "UPDATE NhanVien set MatKhau= ?, HoTen = ?,VaiTro = ? where MaNV = ?";
	final String DELETE_SQL = "Delete from NhanVien where MaNV = ?";
	final String SELECT_ALL_SQL = "SELECT * from NhanVien";
	final String SELRCT_By_ID_SQL = "SELECT * from NhanVien where MaNV= ?";

	@Override
	public void insert(Nhanvien entity) {
		Jdbc.update(INSERT_SQL, entity.getMaNV(), entity.getMatkhau(), entity.getHoTen(), entity.isVaiTro());
	}

	@Override
	public void update(Nhanvien entity) {
		Jdbc.update(UPDATE_SQL, entity.getMatkhau(), entity.getHoTen(), entity.isVaiTro(), entity.getMaNV());
	}

	@Override
	public void delete(String id) {
		Jdbc.update(DELETE_SQL, id);
	}

	@Override
	public List<Nhanvien> selectAll() {
		return selectBysql(SELECT_ALL_SQL);
	}

	@Override
	public Nhanvien selectById(String id) {
		List<Nhanvien> list = selectBysql(SELRCT_By_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<Nhanvien> selectBysql(String sql, Object... args) {
		List<Nhanvien> list = new ArrayList<Nhanvien>();
		try {
			ResultSet rs = Jdbc.query(sql, args);
			while (rs.next()) {
				Nhanvien nv = new Nhanvien();
				nv.setMaNV(rs.getString("MaNV"));
				nv.setMatkhau(rs.getString("MatKhau"));
				nv.setHoTen(rs.getString("HoTen"));
				nv.setVaiTro(rs.getBoolean("VaiTro"));
				list.add(nv);
			}
			rs.getStatement().getConnection().close();
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
