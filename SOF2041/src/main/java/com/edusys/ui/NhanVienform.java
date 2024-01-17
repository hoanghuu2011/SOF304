/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.entity.Nhanvien;
import com.edusys.dao.NhanvienDao;
import com.edusys.utils.Auth;
import com.edusys.utils.MsgBox;
import com.edusys.utils.XImage;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOANG HUU
 */
public class NhanVienform extends javax.swing.JDialog {

	NhanvienDao dao = new NhanvienDao();
	int row = -1;// hàng đc chọn hiện tại trên bảng

	/**
	 * Creates new form NhanVien
	 */
	public NhanVienform(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		init();
	}

	private void init() {
		setIconImage(XImage.getAppIcon());
		setLocationRelativeTo(null);
		setTitle("Edusys Quản Lý Nhân Viên ");
		fillTable();

	}

	void insert() {
		Nhanvien nv = getForm();
		String mk2 = new String(txtMatKhau2.getPassword());
		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có quyền thêm nhân viên!");
		} else {
			if (!mk2.equals(nv.getMatkhau())) {
				MsgBox.alert(this, "Xác nhận mật khẩu không đúng!");
				txtMatKhau2.requestFocus();
			} else {
				try {
					dao.insert(nv);
					this.fillTable();
					this.clearForm();
					MsgBox.alert(this, "Thêm mới thành công!");
				} catch (Exception e) {
					MsgBox.alert(this, "Thêm mới thất bại!");
				}
			}
		}

	}

	void update() {
		Nhanvien nv = getForm();
		String mk2 = new String(txtMatKhau2.getPassword());
		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có quyền sửa nhân viên!");
		} else {
			if (!mk2.equals(nv.getMatkhau())) {
				MsgBox.alert(this, "Xác nhận mật khẩu không đúng!");
			} else {
				try {
					dao.update(nv);
					this.fillTable();
					MsgBox.alert(this, "Cập nhật thành công!");
				} catch (Exception e) {
					MsgBox.alert(this, "Cập nhật thất bại!");
				}
			}
		}

	}

	void delete() {
		if (!Auth.isManager()) {
			MsgBox.alert(this, "Bạn không có quyền xóa nhân viên!");
		} else {
			String manv = txtMaNV.getText();
			if (manv.equals(Auth.user.getMaNV())) {
				MsgBox.alert(this, "Bạn không được xóa chính bạn!");
			} else if (MsgBox.confirm(this, "Bạn có thực sự muốn xóa nhân viên này?")) {
				try {
					dao.delete(manv);
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
		Nhanvien nv = new Nhanvien();
		this.setForm(nv);//// xóa trắng form
		this.row = -1;
		this.updateStatus();
	}

	void edit() {
		String manv = (String) tblNhanVien.getValueAt(this.row, 0);
		Nhanvien nv = dao.selectById(manv);// dùng maNV tìm ra đối tượng NhanVien
		this.setForm(nv);
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
		if (this.row < tblNhanVien.getRowCount() - 1) {
			this.row++;
			this.edit();
		}
	}

	void last() {
		this.row = tblNhanVien.getRowCount() - 1;
		this.edit();
	}

	void setForm(Nhanvien nv) {
		txtMaNV.setText(nv.getMaNV());
		txtHoTen.setText(nv.getHoTen());
		txtMatKhau.setText(nv.getMatkhau());
		txtMatKhau2.setText(nv.getMatkhau());
		rdoTruongPhong.setSelected(nv.isVaiTro());
		rdoNhanVien.setSelected(!nv.isVaiTro());
	}

	Nhanvien getForm() {// lấy thông tin trên form cho vào đt NhanVien
		Nhanvien nv = new Nhanvien();
		nv.setMaNV(txtMaNV.getText());
		nv.setHoTen(txtHoTen.getText());
		nv.setMatkhau(new String(txtMatKhau.getPassword()));
		nv.setVaiTro(rdoTruongPhong.isSelected());
		return nv;
	}

	void updateStatus() {
		boolean edit = (this.row >= 0);
		boolean first = (this.row == 0);
		boolean last = (this.row == tblNhanVien.getRowCount() - 1);
		// Trang thai form
		txtMaNV.setEditable(!edit);
		btnThem.setEnabled(!edit);
		btnSua.setEnabled(edit);
		btnXoa.setEnabled(edit);
		// Trang thai dieu huong
		btnFirst.setEnabled(edit && !first);
		btnPrev.setEnabled(edit && !first);
		btnNext.setEnabled(edit && !last);
		btnLast.setEnabled(edit && !last);
	}

	void fillTable() {
		DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
		model.setRowCount(0); // xoa tat ca cac hang
		try {
			List<Nhanvien> list = dao.selectAll(); //// lấy tất cả nhân viên trong CSDL đưa vào list
			for (Nhanvien nv : list) {
				Object[] row = { nv.getMaNV(), nv.getMatkhau().replaceAll(nv.getMatkhau(), "******"), nv.getHoTen(),
						nv.isVaiTro() ? "Trưởng phòng" : "Nhân Viên" };
				model.addRow(row); // them 1 hang vao table
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
		}
	}

	public boolean validateForm(boolean chk) {
		if (txtMaNV.getText().length() == 0) {
			MsgBox.alert(this, "Không được phép để trống mã nhân viên!");
			txtMaNV.requestFocus();
			return false;
		} else if (txtMatKhau.getText().length() == 0) {
			MsgBox.alert(this, "Không được phép để trống mật khẩu!");
			txtMatKhau.requestFocus();
			return false;
		} else if (txtMatKhau2.getText().length() == 0) {
			MsgBox.alert(this, "Bạn chưa xác nhận lại mật khẩu!");
			txtMatKhau2.requestFocus();
			return false;
		} else if (txtHoTen.getText().length() == 0) {
			MsgBox.alert(this, "Không được phép để trống họ tên!");
			txtHoTen.requestFocus();
			return false;
		}

		List<Nhanvien> list = dao.selectAll();
		if (chk) {
			for (Nhanvien cd : list) {
				if (txtMaNV.getText().equals(cd.getMaNV())) {
					MsgBox.alert(this, "Mã nhân viên đã tồn tại");
					txtMaNV.requestFocus();
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

		buttonGroup1 = new javax.swing.ButtonGroup();
		jLabel1 = new javax.swing.JLabel();
		tabs = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		txtMaNV = new javax.swing.JTextField();
		jLabel12 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		rdoTruongPhong = new javax.swing.JRadioButton();
		rdoNhanVien = new javax.swing.JRadioButton();
		jLabel4 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		txtHoTen = new javax.swing.JTextField();
		txtMatKhau = new javax.swing.JPasswordField();
		txtMatKhau2 = new javax.swing.JPasswordField();
		btnThem = new javax.swing.JButton();
		btnMoi = new javax.swing.JButton();
		btnXoa = new javax.swing.JButton();
		btnFirst = new javax.swing.JButton();
		btnPrev = new javax.swing.JButton();
		btnNext = new javax.swing.JButton();
		btnLast = new javax.swing.JButton();
		btnSua = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblNhanVien = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 51, 51));
		jLabel1.setText("QUẢN LÝ NHÂN VIÊN QUẢN TRỊ");

		jPanel1.setBackground(new java.awt.Color(233, 239, 239));

		jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel3.setText("Mã Nhân Viên");

		jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel12.setText("Mật Khẩu");

		jLabel2.setText("Vai Trò");

		buttonGroup1.add(rdoTruongPhong);
		rdoTruongPhong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		rdoTruongPhong.setSelected(true);
		rdoTruongPhong.setText("Trưởng Phòng");

		buttonGroup1.add(rdoNhanVien);
		rdoNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		rdoNhanVien.setText("Nhân Viên");

		jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel4.setText("Xác Nhận Mật Khẩu");

		jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel15.setText("Họ Và Tên");

		txtMatKhau2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtMatKhau2ActionPerformed(evt);
			}
		});

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

		btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit.png"))); // NOI18N
		btnSua.setText("Sửa");
		btnSua.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSuaActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
						.addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
								.addGap(3, 3, 3)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)))
						.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
								jPanel1Layout.createSequentialGroup().addComponent(rdoTruongPhong)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(rdoNhanVien))
						.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
								jPanel1Layout.createSequentialGroup()
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(18, 18, 18)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(btnMoi).addComponent(btnXoa))
										.addGap(73, 73, 73).addComponent(btnFirst)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(btnPrev).addGap(18, 18, 18).addComponent(btnNext)
										.addGap(18, 18, 18).addComponent(btnLast,
												javax.swing.GroupLayout.PREFERRED_SIZE, 78,
												javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(txtMatKhau2, javax.swing.GroupLayout.Alignment.LEADING,
								javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
						.addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtHoTen))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel12)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(txtMatKhau2, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel15)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jLabel2)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(67, 67, 67)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(btnFirst).addComponent(btnPrev).addComponent(btnNext)
												.addComponent(btnLast, javax.swing.GroupLayout.Alignment.TRAILING)))
								.addGroup(jPanel1Layout.createSequentialGroup()
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(rdoTruongPhong).addComponent(rdoNhanVien))
										.addGap(18, 18, 18)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(btnMoi))
										.addGap(18, 18, 18)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnXoa).addComponent(btnSua))))
						.addGap(430, 430, 430)));

		tabs.addTab("CẬP NHẬT", jPanel1);

		tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Mã NV", "Mật Khẩu", "Họ Tên", "Vai Trò" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblNhanVienMouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(tblNhanVien);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
						.addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 445,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		tabs.addTab("DANH SÁCH", jPanel3);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 255,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 738,
										javax.swing.GroupLayout.PREFERRED_SIZE))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
						.addGap(5, 5, 5).addComponent(jLabel1).addGap(18, 18, 18).addComponent(tabs,
								javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblNhanVienMouseClicked
		// TODO add your handling code here:
		if (evt.getClickCount() == 2) {
			this.row = tblNhanVien.getSelectedRow();
			this.edit();
		}
	}// GEN-LAST:event_tblNhanVienMouseClicked

	private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSuaActionPerformed
		// TODO add your handling code here:
		if (validateForm(false)) {
			update();
		}
	}// GEN-LAST:event_btnSuaActionPerformed

	private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLastActionPerformed
		last();
	}// GEN-LAST:event_btnLastActionPerformed

	private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNextActionPerformed
		next();
	}// GEN-LAST:event_btnNextActionPerformed

	private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPrevActionPerformed
		prev();
	}// GEN-LAST:event_btnPrevActionPerformed

	private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnFirstActionPerformed
		first();
	}// GEN-LAST:event_btnFirstActionPerformed

	private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
		// TODO add your handling code here:
		delete();
	}// GEN-LAST:event_btnXoaActionPerformed

	private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnMoiActionPerformed
		// TODO add your handling code here:
		clearForm();
	}// GEN-LAST:event_btnMoiActionPerformed

	private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
		// TODO add your handling code here:
		if (validateForm(true)) {
			insert();
		}
	}// GEN-LAST:event_btnThemActionPerformed

	private void txtMatKhau2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMatKhau2ActionPerformed

	}// GEN-LAST:event_txtMatKhau2ActionPerformed

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
			java.util.logging.Logger.getLogger(NhanVienform.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(NhanVienform.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(NhanVienform.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NhanVienform.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				NhanVienform dialog = new NhanVienform(new javax.swing.JFrame(), true);
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
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JRadioButton rdoNhanVien;
	private javax.swing.JRadioButton rdoTruongPhong;
	private javax.swing.JTabbedPane tabs;
	private javax.swing.JTable tblNhanVien;
	private javax.swing.JTextField txtHoTen;
	private javax.swing.JTextField txtMaNV;
	private javax.swing.JPasswordField txtMatKhau;
	private javax.swing.JPasswordField txtMatKhau2;
	// End of variables declaration//GEN-END:variables
}
