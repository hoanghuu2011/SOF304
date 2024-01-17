/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author HOANG HUU
 */
public class Nhanvien {
	private String maNV;
	private String hoTen;
	private String matkhau;
	private boolean VaiTro;

	public Nhanvien() {
	}

	public Nhanvien(String maNV, String hoTen, String matkhau, boolean VaiTro) {
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.matkhau = matkhau;
		this.VaiTro = VaiTro;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getMatkhau() {
		return matkhau;
	}

	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}

	public boolean isVaiTro() {
		return VaiTro;
	}

	public void setVaiTro(boolean VaiTro) {
		this.VaiTro = VaiTro;
	}

	@Override
	public String toString() {
		return this.hoTen;
	}
}
