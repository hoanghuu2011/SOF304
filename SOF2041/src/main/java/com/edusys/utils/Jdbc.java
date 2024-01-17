/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HOANG HUU
 */
public class Jdbc {
	private static String diver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String dburl = "jdbc:sqlserver://localhost;database=Polypro";
	private static String user = "sa";
	private static String pass = "123";

	static {
		try {
			Class.forName(diver);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
		Connection conn = DriverManager.getConnection(dburl, user, pass);
		PreparedStatement stmt;

		if (sql.trim().startsWith("{")) {
			stmt = conn.prepareCall(sql); // PROC
		} else {
			stmt = conn.prepareStatement(sql); // SQL
		}

		for (int i = 0; i < args.length; i++) {
			stmt.setObject(i + 1, args[i]);
		}
		return stmt;
	}

	public static ResultSet query(String sql, Object... args) throws SQLException {
		PreparedStatement stmt = Jdbc.getStmt(sql, args);
		return stmt.executeQuery();
	}

	public static Object value(String sql, Object... args) {
		try {
			ResultSet rs = Jdbc.query(sql, args);
			if (rs.next()) {
				return rs.getObject(0);
			}
			rs.getStatement().getConnection().close();
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int update(String sql, Object... args) {
		try {
			PreparedStatement stmt = Jdbc.getStmt(sql, args);
			try {
				return stmt.executeUpdate();
			} finally {
				stmt.getConnection().close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
