/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.entity.chuyenDe;
import com.edusys.dao.ChuyenDedao;
import com.edusys.utils.Auth;
import com.edusys.utils.MsgBox;
import com.edusys.utils.XImage;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOANG HUU
 */
public class ChuyenDeForm extends javax.swing.JDialog {

	ChuyenDedao dao = new ChuyenDedao();
	int row = -1;// hàng đc chọn hiện tại trên bảng
	JFileChooser fileChooser = new JFileChooser();

	/**
	 * Creates new form ChuyenDe
	 */
	public ChuyenDeForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		init();
	}

	private void init() {
		setIconImage(XImage.getAppIcon());
		setLocationRelativeTo(null);
		setTitle("Edusys Quản Lý Chuyên Đề ");
		this.fillTable();
		this.row = -1;
		this.updateStatus();
	}

	void chonAnh() {
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();// lấy file người dùng chọn
			XImage.save(file); // luu hinh vao thu muc icon1
			ImageIcon icon = XImage.read(file.getName()); // Doc hinh tu logos
			lblHinh.setIcon(icon);
			lblHinh.setToolTipText(file.getName()); // giu ten hinh trong tooltip
		}
	}

	void insert() {
		chuyenDe cd = getForm();

		try {
			dao.insert(cd);
			this.fillTable();
			this.clearForm();
			MsgBox.alert(this, "Thêm mới thành công!");
		} catch (Exception e) {
			MsgBox.alert(this, "Thêm mới thất bại!");
		}

	}

	void update() {
		chuyenDe cd = getForm();

		try {
			dao.update(cd);
			this.fillTable();
			MsgBox.alert(this, "Cập nhật thành công!");
		} catch (Exception e) {
			MsgBox.alert(this, "Cập nhật thất bại!");
		}

	}

	void delete() {

		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có quyền xóa chuyên đề!");
		} else {
			String macd = txtMaCD.getText();
			if (MsgBox.confirm(this, "Bạn có thực sự muốn xóa chuyên đề này?")) {
				try {
					dao.delete(macd);
					this.fillTable();
					this.clearForm();
					MsgBox.alert(this, "Xóa thành công!");
				} catch (Exception e) {
					MsgBox.alert(this, "Xóa thát bại!");
				}
			}
		}

	}

	void clearForm() {
		chuyenDe cd = new chuyenDe();
		this.setForm(cd);
		this.row = -1;
		this.updateStatus();
	}

	void edit() {
		String macd = (String) tblChuyenDe.getValueAt(this.row, 0);
		chuyenDe cd = dao.selectById(macd);
		this.setForm(cd);
		tabs.setSelectedIndex(0);
		this.updateStatus();
	}

	void first() {
		this.row = 0;
		this.edit();
	}

	void prev() {
		if (this.row > 0) {
			this.row--;
			this.edit();
		}
	}

	void next() {
		if (this.row < tblChuyenDe.getRowCount() - 1) {
			this.row++;
			this.edit();
		}
	}

	void last() {
		this.row = tblChuyenDe.getRowCount() - 1;
		this.edit();
	}

	void fillTable() {
		DefaultTableModel model = (DefaultTableModel) tblChuyenDe.getModel();
		model.setRowCount(0); // xoa tat ca cac hang
		try {
			List<chuyenDe> list = dao.selectAll(); // doc du lieu tu CSDL
			for (chuyenDe cd : list) {
				Object[] row = { cd.getMaCD(), cd.getTenCD(), cd.getHocPhi(), cd.getThoiLuong(), cd.getHinh() };
				model.addRow(row); // them 1 hang vao table
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
		}
	}

	void setForm(chuyenDe cd) {
		txtMaCD.setText(cd.getMaCD());
		txtTenCD.setText(cd.getTenCD());
		txtThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
		txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
		txtMoTa.setText(cd.getMoTa());

		if (cd.getHinh() != null) {
			lblHinh.setToolTipText(cd.getHinh());
			lblHinh.setIcon(XImage.read(cd.getHinh()));// đọc file trong thư mục icon1 theo tên file trả về ImageIcon
		} else {
			lblHinh.setToolTipText(null);
			lblHinh.setIcon(null);
		}
	}

	chuyenDe getForm() {
		chuyenDe cd = new chuyenDe();

		cd.setMaCD(txtMaCD.getText());
		cd.setTenCD(txtTenCD.getText());
		cd.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
		cd.setHocPhi(Double.valueOf(txtHocPhi.getText()));
		cd.setMoTa(txtMoTa.getText());
		cd.setHinh(lblHinh.getToolTipText());//// lấy tên hình
		return cd;
	}

	void updateStatus() {
		boolean edit = (this.row >= 0);
		boolean first = (this.row == 0);
		boolean last = (this.row == tblChuyenDe.getRowCount() - 1);
		// Trang thai form
		txtMaCD.setEditable(!edit);
		btnThem.setEnabled(!edit);
		btnSua.setEnabled(edit);
		btnXoa.setEnabled(edit);
		// Trang thai dieu huong
		btnFirst.setEnabled(edit && !first);
		btnPrev.setEnabled(edit && !first);
		btnNext.setEnabled(edit && !last);
		btnLast.setEnabled(edit && !last);
	}

	public boolean validateForm(boolean chk) {
		try {
			if (txtMaCD.getText().length() == 0) {
				MsgBox.alert(this, "Không được phép để trống mã chuyên đề!");
				txtMaCD.requestFocus();
				return false;
			} else if (txtTenCD.getText().length() == 0) {
				MsgBox.alert(this, "Không được phép để trống tên chuyên đề!");
				txtTenCD.requestFocus();
				return false;
			} else if (txtThoiLuong.getText().length() == 0) {
				MsgBox.alert(this, "Không được phép để trống thời lượng!");
				txtThoiLuong.requestFocus();
				return false;
			} else if (Integer.parseInt(txtThoiLuong.getText()) <= 0) {
				MsgBox.alert(this, "Thời lượng phải là số dương!");
				txtThoiLuong.requestFocus();
				return false;
			} else if (txtHocPhi.getText().length() == 0) {
				MsgBox.alert(this, "Không được phép để trống học phí!");
				txtHocPhi.requestFocus();
				return false;
			} else if (Double.parseDouble(txtHocPhi.getText()) <= 0) {
				MsgBox.alert(this, "Học phí phải lớn hơn 0!");
				txtHocPhi.requestFocus();
				return false;
			} else if (lblHinh.getIcon() == null) {
				MsgBox.alert(this, "Bạn chưa chọn hình cho chuyên đề!");
				lblHinh.requestFocus();
				return false;
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Bạn đã nhập sai định dạng!\r" + "Thời lượng hoặc học phí phải được nhập bằng số.");
			return false;
		}

		List<chuyenDe> list = dao.selectAll();
		if (chk) {
			for (chuyenDe cd : list) {
				if (txtMaCD.getText().equals(cd.getMaCD())) {
					MsgBox.alert(this, "Mã Chuyên đề đã tồn tại");
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel1 = new javax.swing.JLabel();
		tabs = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		lblHinh = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		txtMaCD = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		txtTenCD = new javax.swing.JTextField();
		jLabel5 = new javax.swing.JLabel();
		txtThoiLuong = new javax.swing.JTextField();
		txtHocPhi = new javax.swing.JTextField();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		txtMoTa = new javax.swing.JTextArea();
		jPanel4 = new javax.swing.JPanel();
		btnThem = new javax.swing.JButton();
		btnMoi = new javax.swing.JButton();
		btnXoa = new javax.swing.JButton();
		btnSua = new javax.swing.JButton();
		btnFirst = new javax.swing.JButton();
		btnPrev = new javax.swing.JButton();
		btnNext = new javax.swing.JButton();
		btnLast = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblChuyenDe = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 51, 51));
		jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");

		jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel2.setText("Hình Logo");

		jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 0, 51)));

		lblHinh.setForeground(new java.awt.Color(255, 51, 51));
		lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				lblHinhMouseClicked(evt);
			}
		});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE));

		jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel3.setText("Mã Chuyên Đề");

		txtMaCD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtMaCDActionPerformed(evt);
			}
		});

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel4.setText("Tên Chuyên Đề");

		jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel5.setText("Thời Lượng (Giờ)");

		jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel6.setText("Học Phí");

		jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel7.setText("Mô Tả Chuyên Đề");

		txtMoTa.setColumns(20);
		txtMoTa.setRows(5);
		jScrollPane1.setViewportView(txtMoTa);

		btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add.png"))); // NOI18N
		btnThem.setText("Thêm");
		btnThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThemActionPerformed(evt);
			}
		});

		btnMoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Refresh.png"))); // NOI18N
		btnMoi.setText("Mới");
		btnMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnMoiActionPerformed(evt);
			}
		});

		btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
		btnXoa.setText("Xóa");
		btnXoa.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXoaActionPerformed(evt);
			}
		});

		btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit.png"))); // NOI18N
		btnSua.setText("Sửa");
		btnSua.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSuaActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
				.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 0, Short.MAX_VALUE)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel4Layout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(jPanel4Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(18, 18, 18)
										.addGroup(jPanel4Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(btnMoi).addComponent(btnXoa))
										.addGap(454, 454, 454))));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 99, Short.MAX_VALUE)
				.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel4Layout.createSequentialGroup().addGap(2, 2, 2)
								.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnMoi))
								.addGap(18, 18, 18)
								.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnXoa).addComponent(btnSua))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));

		btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Button-First-icon-48.png"))); // NOI18N
		btnFirst.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFirstActionPerformed(evt);
			}
		});

		btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Fast-backward-icon-48.png"))); // NOI18N
		btnPrev.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPrevActionPerformed(evt);
			}
		});

		btnNext.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Fast-forward-icon-48.png"))); // NOI18N
		btnNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNextActionPerformed(evt);
			}
		});

		btnLast.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Button-Last-icon-48.png"))); // NOI18N
		btnLast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLastActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane1)
										.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel2)
														.addGap(128, 128, 128).addComponent(jLabel3))
												.addGroup(jPanel1Layout.createSequentialGroup().addGap(3, 3, 3)
														.addComponent(jLabel7))
												.addGroup(jPanel1Layout.createSequentialGroup()
														.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
																219, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(68, 68, 68)
														.addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE,
																73, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18)
														.addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE,
																73, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(27, 27, 27)
														.addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE,
																70, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(29, 29, 29).addComponent(btnLast,
																javax.swing.GroupLayout.PREFERRED_SIZE, 70,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGap(114, 114, Short.MAX_VALUE))
										.addGroup(jPanel1Layout.createSequentialGroup().addComponent(
												jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(jPanel1Layout.createSequentialGroup()
																.addGap(21, 21, 21)
																.addGroup(jPanel1Layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jLabel4).addComponent(jLabel5)
																		.addComponent(jLabel6))
																.addGap(0, 498, Short.MAX_VALUE))
														.addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(jPanel1Layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(txtMaCD)
																		.addComponent(txtTenCD,
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(txtThoiLuong,
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(txtHocPhi))))))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addComponent(txtMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel4)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel5)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel6)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel7)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(jPanel1Layout.createSequentialGroup()
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(23, 23, 23))
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
														.createSequentialGroup()
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGroup(jPanel1Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(btnNext,
																		javax.swing.GroupLayout.Alignment.TRAILING)
																.addComponent(btnLast,
																		javax.swing.GroupLayout.Alignment.TRAILING)
																.addComponent(btnPrev,
																		javax.swing.GroupLayout.Alignment.TRAILING))
														.addGap(51, 51, 51))))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
										.createSequentialGroup().addComponent(btnFirst).addGap(51, 51, 51)))));

		tabs.addTab("CẬP NHẬT", jPanel1);

		tblChuyenDe.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Mã CD", "Tên Chuyên Đề", "Học Phí", "Thời Lượng", "Hình" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tblChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblChuyenDeMouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(tblChuyenDe);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane2,
						javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane2,
						javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)));

		tabs.addTab("DANH SÁCH", jPanel3);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel1).addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 778,
										javax.swing.GroupLayout.PREFERRED_SIZE))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 511, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblHinhMouseClicked
		// TODO add your handling code here:
		chonAnh();
	}// GEN-LAST:event_lblHinhMouseClicked

	private void tblChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblChuyenDeMouseClicked
		// TODO add your handling code here:
		if (evt.getClickCount() == 2) {
			this.row = tblChuyenDe.getSelectedRow();
			this.edit();
		}
	}// GEN-LAST:event_tblChuyenDeMouseClicked

	private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
		// TODO add your handling code here:
		if (validateForm(true)) {
			insert();
		}
	}// GEN-LAST:event_btnThemActionPerformed

	private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnMoiActionPerformed
		// TODO add your handling code here:
		clearForm();
	}// GEN-LAST:event_btnMoiActionPerformed

	private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
		// TODO add your handling code here:
		delete();
	}// GEN-LAST:event_btnXoaActionPerformed

	private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSuaActionPerformed
		// TODO add your handling code here:
		if (validateForm(false)) {
			update();
		}
	}// GEN-LAST:event_btnSuaActionPerformed

	private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnFirstActionPerformed
		first();
	}// GEN-LAST:event_btnFirstActionPerformed

	private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPrevActionPerformed
		prev();
	}// GEN-LAST:event_btnPrevActionPerformed

	private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNextActionPerformed
		next();
	}// GEN-LAST:event_btnNextActionPerformed

	private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLastActionPerformed
		last();
	}// GEN-LAST:event_btnLastActionPerformed

	private void txtMaCDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaCDActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_txtMaCDActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ChuyenDeForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ChuyenDeForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ChuyenDeForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ChuyenDeForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ChuyenDeForm dialog = new ChuyenDeForm(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnFirst;
	private javax.swing.JButton btnLast;
	private javax.swing.JButton btnMoi;
	private javax.swing.JButton btnNext;
	private javax.swing.JButton btnPrev;
	private javax.swing.JButton btnSua;
	private javax.swing.JButton btnThem;
	private javax.swing.JButton btnXoa;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JLabel lblHinh;
	private javax.swing.JTabbedPane tabs;
	private javax.swing.JTable tblChuyenDe;
	private javax.swing.JTextField txtHocPhi;
	private javax.swing.JTextField txtMaCD;
	private javax.swing.JTextArea txtMoTa;
	private javax.swing.JTextField txtTenCD;
	private javax.swing.JTextField txtThoiLuong;
	// End of variables declaration//GEN-END:variables
}
