/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.dao;

import com.edusys.entity.KhoaHoc;
import com.edusys.utils.Jdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOANG HUU
 */
public class KhoahocDao extends dao<KhoaHoc, Integer> {

	String INSERT_SQL = "INSERT INTO KhoaHoc(MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao) VALUES(?,?,?,?,?,?,GETDATE())";
	String UPDATE_SQL = "UPDATE KhoaHoc SET MaCD = ?, HocPhi = ?, ThoiLuong = ?, NgayKG = ?, GhiChu = ?, MaNV = ? WHERE MaKH = ?";
	String DELETE_SQL = "DELETE FROM KhoaHoc WHERE MaKH = ?";
	String SELECT_ALL_SQL = "SELECT * FROM KhoaHoc";
	String SELECT_BY_ID_SQL = "SELECT * FROM KhoaHoc WHERE MaKH = ?";
	String SELECT_BY_MA_CD_SQL = "SELECT * FROM KhoaHoc WHERE MaCD= ?";

	@Override
	public void insert(KhoaHoc entity) {
		Jdbc.update(INSERT_SQL, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(),
				new java.sql.Date(entity.getNgayKG().getTime()), entity.getGhiChu(), entity.getMaNV());
	}

	@Override
	public void update(KhoaHoc entity) {
		Jdbc.update(UPDATE_SQL, entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(),
				new java.sql.Date(entity.getNgayKG().getTime()), entity.getGhiChu(), entity.getMaNV(),
				entity.getMaKH());
	}

	@Override
	public void delete(Integer id) {
		Jdbc.update(DELETE_SQL, id);
	}

	@Override
	public List<KhoaHoc> selectAll() {
		return this.selectBysql(SELECT_ALL_SQL);
	}

	@Override
	public KhoaHoc selectById(Integer id) {
		List<KhoaHoc> list = selectBysql(SELECT_BY_ID_SQL, id);
		if (list.isEmpty()) {
			return null;
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<KhoaHoc> selectBysql(String sql, Object... args) {
		List<KhoaHoc> list = new ArrayList<KhoaHoc>();
		try {
			ResultSet rs = Jdbc.query(sql, args);
			while (rs.next()) {
				KhoaHoc kh = new KhoaHoc();
				kh.setMaKH(rs.getInt("MaKH"));
				kh.setMaCD(rs.getString("MaCD"));
				kh.setHocPhi(rs.getDouble("HocPhi"));
				kh.setThoiLuong(rs.getInt("ThoiLuong"));
				kh.setNgayKG(rs.getDate("NgayKG"));
				kh.setMaNV(rs.getString("MaNV"));
				kh.setNgayTao(rs.getDate("NgayTao"));
				list.add(kh);
			}
			rs.getStatement().getConnection().close();
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<KhoaHoc> selectByChuyenDe(String maCD) {
		return selectBysql(SELECT_BY_MA_CD_SQL, maCD);
	}

	public List<Integer> selectYears() {
		String sql = "SELECT DISTINCT year(NgayKG) as YEAR FROM KhoaHoc ORDER BY YEAR DESC";
		List<Integer> list = new ArrayList<>();
		try {
			ResultSet rs = Jdbc.query(sql);
			while (rs.next()) {
				list.add(rs.getInt(1));
			}
			rs.getStatement().getConnection().close();
			return list;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
