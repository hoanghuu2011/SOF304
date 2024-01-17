/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.chuyenDe;
import com.edusys.utils.Jdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOANG HUU
 */
public class ChuyenDedao extends dao<chuyenDe, String> {
	final String INSERT_SQL = "INSERT INTO ChuyenDe(MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES(?,?,?,?,?,?)";
	final String UPDATE_SQL = "UPDATE ChuyenDe SET TenCD = ?, HocPhi = ?, ThoiLuong = ?, Hinh = ?, MoTa = ? WHERE MaCD = ?";
	final String DELETE_SQL = "DELETE FROM ChuyenDe WHERE MaCD = ?";
	final String SELECT_ALL_SQL = "SELECT * FROM ChuyenDe";
	final String SELECT_BY_ID_SQL = "SELECT * FROM ChuyenDe WHERE MaCD = ?";

	@Override
	public void insert(chuyenDe entity) {
		Jdbc.update(INSERT_SQL, entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(),
				entity.getHinh(), entity.getMoTa());
	}

	@Override
	public void update(chuyenDe entity) {
		Jdbc.update(UPDATE_SQL, entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(),
				entity.getMoTa(), entity.getMaCD());
	}

	@Override
	public void delete(String id) {
		Jdbc.update(DELETE_SQL, id);
	}

	@Override
	public List<chuyenDe> selectAll() {
		return this.selectBysql(SELECT_ALL_SQL);
	}

	@Override
	public chuyenDe selectById(String id) {
		List<chuyenDe> list = this.selectBysql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<chuyenDe> selectBysql(String sql, Object... args) {
		List<chuyenDe> list = new ArrayList<chuyenDe>();
		try {
			ResultSet rs = Jdbc.query(sql, args);
			while (rs.next()) {
				chuyenDe cd = new chuyenDe();
				cd.setMaCD(rs.getString("MaCD"));
				cd.setTenCD(rs.getString("TenCD"));
				cd.setHocPhi(rs.getDouble("HocPhi"));
				cd.setThoiLuong(rs.getInt("ThoiLuong"));
				cd.setHinh(rs.getString("Hinh"));
				cd.setMoTa(rs.getString("MoTa"));
				list.add(cd);
			}
			rs.getStatement().getConnection().close();
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
