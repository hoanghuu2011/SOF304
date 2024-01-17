/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.HocVien;
import com.edusys.utils.Jdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOANG HUU
 */
public class HocVienDao extends dao<HocVien, Integer> {
	final String INSERT_SQL = "INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?,?,?)";
	final String UPDATE_SQL = "UPDATE HocVien SET MaKH = ?, MaNH = ?, Diem = ? WHERE MaHV = ?";
	final String DELETE_SQL = "DELETE FROM HocVien WHERE MaHV = ?";
	final String SELECT_ALL_SQL = "SELECT * FROM HocVien";
	final String SELECT_BY_ID_SQL = "SELECT * FROM HocVien WHERE MaHV = ?";
	final String SELECT_BY_MA_KH_SQL = "SELECT * FROM HocVien WHERE MaKH = ?";

	@Override
	public void insert(HocVien entity) {
		Jdbc.update(INSERT_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem());
	}

	@Override
	public void update(HocVien entity) {
		Jdbc.update(UPDATE_SQL, entity.getMaKH(), entity.getMaNH(), entity.getDiem(), entity.getMaHV());
	}

	@Override
	public void delete(Integer id) {
		Jdbc.update(DELETE_SQL, id);
	}

	@Override
	public List<HocVien> selectAll() {
		return this.selectBysql(SELECT_ALL_SQL);

	}

	@Override
	public HocVien selectById(Integer id) {
		List<HocVien> list = this.selectBysql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<HocVien> selectBysql(String sql, Object... args) {
		List<HocVien> list = new ArrayList<HocVien>();
		try {
			ResultSet rs = Jdbc.query(sql, args);
			while (rs.next()) {
				HocVien hv = new HocVien();
				hv.setMaHV(rs.getInt("MaHV"));
				hv.setMaKH(rs.getInt("MaKH"));
				hv.setMaNH(rs.getString("MaNH"));
				hv.setDiem(rs.getDouble("Diem"));
				list.add(hv);
			}
			rs.getStatement().getConnection().close();
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<HocVien> selectByKhoaHoc(int maKH) {
		return selectBysql(SELECT_BY_MA_KH_SQL, maKH);
	}

}