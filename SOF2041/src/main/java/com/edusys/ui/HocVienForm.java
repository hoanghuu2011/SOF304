/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.ChuyenDedao;
import com.edusys.dao.HocVienDao;
import com.edusys.dao.KhoahocDao;
import com.edusys.dao.NguoihocDao;
import com.edusys.entity.HocVien;
import com.edusys.entity.KhoaHoc;
import com.edusys.entity.NguoiHoc;
import com.edusys.entity.chuyenDe;
import com.edusys.utils.Auth;
import com.edusys.utils.MsgBox;
import com.edusys.utils.XImage;
import com.sun.source.tree.BreakTree;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOANG HUU
 */
public class HocVienForm extends javax.swing.JDialog {

	ChuyenDedao cddao = new ChuyenDedao();
	KhoahocDao khdao = new KhoahocDao();
	NguoihocDao nhdao = new NguoihocDao();
	HocVienDao hvdao = new HocVienDao();

	/**
	 * Creates new form HocVienForm
	 */
	public HocVienForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		init();
	}

	private void init() {
		setIconImage(XImage.getAppIcon());
		setLocationRelativeTo(null);
		setTitle("Edusys Quản Lý Học Viên ");
		fillComboBoxChuyenDe();

	}

	void fillComboBoxChuyenDe() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
		model.removeAllElements();// xóa toàn bộ item
		List<chuyenDe> list = cddao.selectAll();
		for (chuyenDe cd : list) {
			model.addElement(cd);
		}
		fillComboBoxKhoaHoc();
	}

	void fillComboBoxKhoaHoc() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
		model.removeAllElements();
		chuyenDe Cde = (chuyenDe) cboChuyenDe.getSelectedItem();
		if (Cde != null) {
			List<KhoaHoc> list = khdao.selectByChuyenDe(Cde.getMaCD());
			for (KhoaHoc kh : list) {
				model.addElement(kh);
			}
			fillTableHocVien();
		}
	}

	void fillTableHocVien() {
		DefaultTableModel model = (DefaultTableModel) tblHocVien.getModel();
		model.setRowCount(0);
		KhoaHoc khoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
		if (khoc != null) {
			List<HocVien> list = hvdao.selectByKhoaHoc(khoc.getMaKH());
			for (int i = 0; i < list.size(); i++) {
				HocVien hv = list.get(i);
				String hoten = nhdao.selectById(hv.getMaNH()).getHoTen();
				model.addRow(new Object[] { i + 1, hv.getMaHV(), hv.getMaNH(), hoten, hv.getDiem() });
			}
			fillTableNguoiHoc();
		}
	}

	void fillTableNguoiHoc() {
		DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
		model.setRowCount(0);
		KhoaHoc khoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
		String keyword = txtTimKiem.getText();
		List<NguoiHoc> list = nhdao.selectNotInCourse(khoc.getMaKH(), keyword);
		for (NguoiHoc nh : list) {
			model.addRow(new Object[] { nh.getMaNH(), nh.getHoTen(), nh.isGioiTinh() ? "Nam" : "Nữ", nh.getNgaySinh(),
					nh.getDienThoai(), nh.getEmail() });
		}
	}

	void addHocVien() {
		try {
			KhoaHoc khoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
			for (int row : tblNguoiHoc.getSelectedRows()) {
				HocVien hv = new HocVien();
				hv.setMaKH(khoc.getMaKH());
				hv.setMaNH((String) tblNguoiHoc.getValueAt(row, 0));
				hv.setDiem(0);
				hvdao.insert(hv);
			}
			fillTableHocVien();
			MsgBox.alert(this, "Thêm học viên thành công!");
			tabs.setSelectedIndex(0);
		} catch (Exception e) {
			MsgBox.alert(this, "Thêm học viên thất bại!");
		}

	}

	void removeHocVien() {
		try {
			if (!Auth.isManager()) {
				MsgBox.alert(this, "Bạn không có quyền xóa học viên!");
			} else {
				if (MsgBox.confirm(this, "Bạn có muốn xóa các học viên được chọn?")) {
					for (int row : tblHocVien.getSelectedRows()) {
						int mahv = (Integer) tblHocVien.getValueAt(row, 1);
						hvdao.delete(mahv);
					}
					fillTableHocVien();
					MsgBox.alert(this, "Xóa học viên thành công!");
				}
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Xóa học viên thất bại!");
		}

	}

	void updateDiem() {

		KhoaHoc khoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
		for (int i = 0; i < tblHocVien.getRowCount(); i++) {
			int maHV = (Integer) tblHocVien.getValueAt(i, 1);
			String manh = (String) tblHocVien.getValueAt(i, 2);
			Double diem = Double.parseDouble(tblHocVien.getValueAt(i, 4).toString());
			HocVien hv = hvdao.selectById(maHV);
			hv.setMaHV(maHV);
			hv.setMaKH(khoc.getMaKH());
			hv.setMaNH(manh);
			hv.setDiem(diem);
			hvdao.update(hv);
		}
		// fillTableHocVien();
		MsgBox.alert(this, "Cập nhật điểm thành công!");

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

		jPanel10 = new javax.swing.JPanel();
		cboKhoaHoc = new javax.swing.JComboBox<>();
		tabs = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		tblHocVien = new javax.swing.JTable();
		btnSuaDiem = new javax.swing.JButton();
		btnXoaHV = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblNguoiHoc = new javax.swing.JTable();
		jPanel6 = new javax.swing.JPanel();
		txtTimKiem = new javax.swing.JTextField();
		btnThemHV = new javax.swing.JButton();
		jPanel7 = new javax.swing.JPanel();
		cboChuyenDe = new javax.swing.JComboBox<>();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
				"KHÓA HỌC", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
		jPanel10.setPreferredSize(new java.awt.Dimension(500, 81));

		cboKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboKhoaHocActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
		jPanel10.setLayout(jPanel10Layout);
		jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel10Layout.createSequentialGroup().addContainerGap()
						.addComponent(cboKhoaHoc, 0, 477, Short.MAX_VALUE).addContainerGap()));
		jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel10Layout.createSequentialGroup().addContainerGap()
						.addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		tblHocVien.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "TT", "Mã Học Viên", "Mã Người Học", "Họ Tên", "Điểm" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane3.setViewportView(tblHocVien);

		btnSuaDiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit.png"))); // NOI18N
		btnSuaDiem.setText("Cập Nhật Điểm");
		btnSuaDiem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSuaDiemActionPerformed(evt);
			}
		});

		btnXoaHV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
		btnXoaHV.setText("Xóa Khỏi Khóa Học");
		btnXoaHV.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXoaHVActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap(484, Short.MAX_VALUE)
						.addComponent(btnXoaHV).addGap(18, 18, 18).addComponent(btnSuaDiem).addGap(15, 15, 15))
				.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jScrollPane3,
						javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(4, 4, 4)
						.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 505,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnSuaDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnXoaHV, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(32, 32, 32)));

		tabs.addTab("HỌC VIÊN", jPanel1);

		tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Mã NH", "Họ Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Email" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		jScrollPane2.setViewportView(tblNguoiHoc);

		jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
				"Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

		txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtTimKiemActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(
				jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout
						.createSequentialGroup().addContainerGap().addComponent(txtTimKiem).addContainerGap()));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup().addContainerGap()
						.addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		btnThemHV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add.png"))); // NOI18N
		btnThemHV.setText("Thêm Vào Khóa Học");
		btnThemHV.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThemHVActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
								.addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
												.addComponent(btnThemHV, javax.swing.GroupLayout.PREFERRED_SIZE, 169,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel3Layout.createSequentialGroup().addContainerGap()
								.addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18).addComponent(btnThemHV, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(94, 94, 94)));

		tabs.addTab("NGƯỜI HỌC", jPanel3);

		jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
				"CHUYÊN ĐỀ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

		cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboChuyenDeActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel7Layout.createSequentialGroup().addContainerGap()
						.addComponent(cboChuyenDe, 0, 249, Short.MAX_VALUE).addContainerGap()));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel7Layout.createSequentialGroup().addContainerGap()
						.addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 513,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(tabs))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
								.addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 619, Short.MAX_VALUE)
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboKhoaHocActionPerformed
		// TODO add your handling code here:
		fillTableHocVien();
	}// GEN-LAST:event_cboKhoaHocActionPerformed

	private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtTimKiemActionPerformed
		// TODO add your handling code here:
		fillTableNguoiHoc();
	}// GEN-LAST:event_txtTimKiemActionPerformed

	private void btnThemHVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemHVActionPerformed
		// TODO add your handling code here:
		addHocVien();
	}// GEN-LAST:event_btnThemHVActionPerformed

	private void btnSuaDiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSuaDiemActionPerformed

		double diem = 0;
		if (diem < 0 || diem > 10) {
			JOptionPane.showMessageDialog(this, "vui long kiem tra lai!");

		}

		updateDiem();

	}// GEN-LAST:event_btnSuaDiemActionPerformed

	private void btnXoaHVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaHVActionPerformed
		// TODO add your handling code here:
		removeHocVien();
	}// GEN-LAST:event_btnXoaHVActionPerformed

	private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboChuyenDeActionPerformed
		// TODO add your handling code here:
		fillComboBoxKhoaHoc();
	}// GEN-LAST:event_cboChuyenDeActionPerformed

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
			java.util.logging.Logger.getLogger(HocVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(HocVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(HocVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(HocVienForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				HocVienForm dialog = new HocVienForm(new javax.swing.JFrame(), true);
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
	private javax.swing.JButton btnSuaDiem;
	private javax.swing.JButton btnThemHV;
	private javax.swing.JButton btnXoaHV;
	private javax.swing.JComboBox<String> cboChuyenDe;
	private javax.swing.JComboBox<String> cboKhoaHoc;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JTabbedPane tabs;
	private javax.swing.JTable tblHocVien;
	private javax.swing.JTable tblNguoiHoc;
	private javax.swing.JTextField txtTimKiem;
	// End of variables declaration//GEN-END:variables
}