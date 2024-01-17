/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edusys.entity;

/**
 *
 * @author HOANG HUU
 */
public class chuyenDe {
	private String MaCD;
	private String TenCD;
	private double HocPhi;
	private int ThoiLuong;
	private String Hinh;
	private String MoTa;

	public String getMaCD() {
		return MaCD;
	}

	public void setMaCD(String MaCD) {
		this.MaCD = MaCD;
	}

	public String getTenCD() {
		return TenCD;
	}

	public void setTenCD(String TenCD) {
		this.TenCD = TenCD;
	}

	public double getHocPhi() {
		return HocPhi;
	}

	public void setHocPhi(double HocPhi) {
		this.HocPhi = HocPhi;
	}

	public int getThoiLuong() {
		return ThoiLuong;
	}

	public void setThoiLuong(int ThoiLuong) {
		this.ThoiLuong = ThoiLuong;
	}

	public String getHinh() {
		return Hinh;
	}

	public void setHinh(String Hinh) {
		this.Hinh = Hinh;
	}

	public String getMoTa() {
		return MoTa;
	}

	public void setMoTa(String MoTa) {
		this.MoTa = MoTa;
	}

	public boolean equals(Object obj) {
		chuyenDe other = (chuyenDe) obj;
		return other.getMaCD().equals(this.getMaCD());
	}
}
