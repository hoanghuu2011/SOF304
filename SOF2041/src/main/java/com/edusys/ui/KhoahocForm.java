/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.edusys.ui;

import com.edusys.dao.ChuyenDedao;
import com.edusys.dao.KhoahocDao;
import com.edusys.entity.KhoaHoc;
import com.edusys.entity.chuyenDe;
import com.edusys.utils.Auth;
import com.edusys.utils.MsgBox;
import com.edusys.utils.XDate;
import com.edusys.utils.XImage;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOANG HUU
 */
public class KhoahocForm extends javax.swing.JDialog {

	KhoahocDao khdao = new KhoahocDao();
	ChuyenDedao cddao = new ChuyenDedao();
	int row = -1;

	/**
	 * Creates new form KhoahocForm
	 */
	public KhoahocForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		init();
	}

	private void init() {
		setIconImage(XImage.getAppIcon());
		setLocationRelativeTo(null);
		setTitle("Edusys Quản Lý Khóa Học ");
		this.fillComboBoxChuyenDe();
	}

	void insert() {
		KhoaHoc kh = getForm();
		kh.setNgayTao(new Date());

		try {
			khdao.insert(kh);
			this.fillTable();
			this.clearForm();
			MsgBox.alert(this, "Thêm mới thành công!");
		} catch (Exception e) {
			MsgBox.alert(this, "Thêm mới thất bại!" + e);
		}

	}

	void update() {
		KhoaHoc kh = getForm();
		Integer makh = (Integer) tblKhoaHoc.getValueAt(tblKhoaHoc.getSelectedRow(), 0);
		kh.setMaKH(makh);

		try {
			khdao.update(kh);
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
			if (MsgBox.confirm(this, "Bạn có thực sự muốn xóa khóa học này?")) {
				try {
					int makh = (int) tblKhoaHoc.getValueAt(this.row, 0);
					khdao.delete(makh);
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
		KhoaHoc kh = new KhoaHoc();
		chuyenDe cd = (chuyenDe) cboChuyenDe.getSelectedItem();

		kh.setMaCD(cd.getMaCD());
		kh.setMaNV(Auth.user.getMaNV());
		kh.setNgayTao(XDate.now());
		kh.setNgayKG(XDate.addDays(XDate.now(), 30));

		this.setForm(kh);
		this.row = -1;
		this.updateStatus();
	}

	void edit() {
		try {
			Integer makh = (Integer) tblKhoaHoc.getValueAt(this.row, 0);
			KhoaHoc kh = khdao.selectById(makh);
			if (kh != null) {
				this.setForm(kh);
				this.updateStatus();
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vẫn dữ liệu!");
		}
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
		if (this.row < tblKhoaHoc.getRowCount() - 1) {
			this.row++;
			this.edit();
		}
	}

	void last() {
		this.row = tblKhoaHoc.getRowCount() - 1;
		this.edit();
	}

	void fillComboBoxChuyenDe() {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
		model.removeAllElements();//// xóa toàn bộ item
		List<chuyenDe> list = cddao.selectAll();
		for (chuyenDe cd : list) {
			model.addElement(cd);
		}
	}

	void fillTable() {
		DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
		model.setRowCount(0); // xoa tat ca cac hang
		try {
			chuyenDe cd = (chuyenDe) cboChuyenDe.getSelectedItem();
			List<KhoaHoc> list = khdao.selectByChuyenDe(cd.getMaCD());
			for (KhoaHoc kh : list) {
				Object[] row = { kh.getMaKH(), kh.getThoiLuong(), kh.getHocPhi(), XDate.toString(kh.getNgayKG()),
						kh.getMaNV(), XDate.toString(kh.getNgayTao()) };
				model.addRow(row); // them 1 hang vao table
			}
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
		}
	}

	void chonChuyenDe() {
		chuyenDe cd = (chuyenDe) cboChuyenDe.getSelectedItem();

		txtThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
		txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
		txtChuyenDe.setText(cd.getTenCD());
		txtGhiChu.setText(cd.getTenCD());
//        
		this.fillTable();
		this.row = -1;
		tabs.setSelectedIndex(1);
		this.updateStatus();
	}

	void setForm(KhoaHoc kh) {
		List<chuyenDe> list = cddao.selectAll();
		for (chuyenDe cd : list) {
			if (cd.getMaCD().equals(kh.getMaCD())) {
				txtChuyenDe.setText(cd.getTenCD());
				txtHocPhi.setText(Double.toString(cd.getHocPhi()));
				txtThoiLuong.setText(Integer.toString(cd.getThoiLuong()));
				txtNguoiTao.setText(kh.getMaNV());
				txtNgayTao.setText(XDate.toString(kh.getNgayTao()));
				txtNgayKG.setText(XDate.toString(kh.getNgayKG()));
				txtGhiChu.setText(kh.getGhiChu());
				return;
			}
		}

	}

	KhoaHoc getForm() {
		KhoaHoc kh = new KhoaHoc();
		chuyenDe cd = (chuyenDe) cboChuyenDe.getSelectedItem();

		kh.setMaCD(cd.getMaCD());
		kh.setNgayKG(XDate.toDate(txtNgayKG.getText()));
		kh.setHocPhi(Double.valueOf(txtHocPhi.getText()));
		kh.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
		kh.setMaNV(Auth.user.getMaNV());
		kh.setNgayTao(XDate.toDate(txtNgayTao.getText()));
		kh.setGhiChu(txtGhiChu.getText());
		return kh;
	}

	void updateStatus() {
		boolean add = (this.row < 0);
		boolean edit = (this.row >= 0);
		boolean first = (this.row == 0);
		boolean last = (this.row == tblKhoaHoc.getRowCount() - 1);
		// Trang thai form
		txtChuyenDe.setEditable(!edit);
		txtHocPhi.setEditable(edit);
		txtThoiLuong.setEditable(edit);
		btnThem.setEnabled(!edit && add);
		btnSua.setEnabled(edit);
		btnXoa.setEnabled(edit);
		// Trang thai dieu huong
		btnFirst.setEnabled(edit && !first);
		btnPrev.setEnabled(edit && !first);
		btnNext.setEnabled(edit && !last);
		btnLast.setEnabled(edit && !last);
	}

	void openHocVien() {

		HocVienForm frame = new HocVienForm(null, true);
		frame.setVisible(true);

	}
	// void openHocVien() {
	// Integer id = Integer.valueOf(cboChuyenDe.getToolTipText());
	// new HocVienForm(id).setVisible(true);
	// }

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
		jPanel6 = new javax.swing.JPanel();
		cboChuyenDe = new javax.swing.JComboBox<>();
		tabs = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		txtChuyenDe = new javax.swing.JTextField();
		jLabel7 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		txtGhiChu = new javax.swing.JTextArea();
		jPanel4 = new javax.swing.JPanel();
		btnThem = new javax.swing.JButton();
		btnSua = new javax.swing.JButton();
		btnXoa = new javax.swing.JButton();
		btnMoi = new javax.swing.JButton();
		btnHocvien = new javax.swing.JButton();
		jLabel10 = new javax.swing.JLabel();
		txtHocPhi = new javax.swing.JTextField();
		jLabel11 = new javax.swing.JLabel();
		txtNguoiTao = new javax.swing.JTextField();
		jLabel12 = new javax.swing.JLabel();
		txtNgayKG = new javax.swing.JTextField();
		jLabel13 = new javax.swing.JLabel();
		txtThoiLuong = new javax.swing.JTextField();
		jLabel14 = new javax.swing.JLabel();
		txtNgayTao = new javax.swing.JTextField();
		btnLast = new javax.swing.JButton();
		btnNext = new javax.swing.JButton();
		btnPrev = new javax.swing.JButton();
		btnFirst = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblKhoaHoc = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(0, 51, 255));
		jLabel1.setText("CHUYÊN ĐỀ");

		cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboChuyenDeActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup().addContainerGap()
						.addComponent(cboChuyenDe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup().addContainerGap()
						.addComponent(cboChuyenDe, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
						.addContainerGap()));

		jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel3.setText("Chuyên Đề");

		txtChuyenDe.setEditable(false);
		txtChuyenDe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
		txtChuyenDe.setForeground(new java.awt.Color(255, 0, 0));
		txtChuyenDe.setEnabled(false);

		jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel7.setText("Ghi Chú");

		txtGhiChu.setColumns(20);
		txtGhiChu.setRows(5);
		jScrollPane1.setViewportView(txtGhiChu);

		btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add.png"))); // NOI18N
		btnThem.setText("Thêm");
		btnThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThemActionPerformed(evt);
			}
		});

		btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit.png"))); // NOI18N
		btnSua.setText("Sửa");
		btnSua.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSuaActionPerformed(evt);
			}
		});

		btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
		btnXoa.setText("Xóa");
		btnXoa.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXoaActionPerformed(evt);
			}
		});

		btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Refresh.png"))); // NOI18N
		btnMoi.setText("Mới");
		btnMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnMoiActionPerformed(evt);
			}
		});

		btnHocvien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Clien list.png"))); // NOI18N
		btnHocvien.setText("Học Viên");
		btnHocvien.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHocvienActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup().addComponent(btnThem).addGap(16, 16, 16)
						.addComponent(btnSua).addGap(18, 18, 18).addComponent(btnMoi).addGap(18, 18, 18)
						.addComponent(btnXoa).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(btnHocvien)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnThem).addComponent(btnMoi).addComponent(btnSua).addComponent(btnXoa)
								.addComponent(btnHocvien))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel10.setText("Học Phí");

		txtHocPhi.setEditable(false);

		jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel11.setText("Người Tạo");

		txtNguoiTao.setEditable(false);

		jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel12.setText("Khai Giảng");

		jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel13.setText("Thời Lượng (Giờ)");

		txtThoiLuong.setEditable(false);

		jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jLabel14.setText("Ngày Tạo");

		btnLast.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Button-Last-icon-48.png"))); // NOI18N
		btnLast.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLastActionPerformed(evt);
			}
		});

		btnNext.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Fast-forward-icon-48.png"))); // NOI18N
		btnNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNextActionPerformed(evt);
			}
		});

		btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
		btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Fast-backward-icon-48.png"))); // NOI18N
		btnPrev.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnPrevActionPerformed(evt);
			}
		});

		btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
		btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Button-First-icon-48.png"))); // NOI18N
		btnFirst.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnFirstActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(3, 3, 3)
												.addGroup(jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel11).addComponent(jLabel10)
														.addComponent(jLabel3).addComponent(jLabel7))))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel13).addComponent(jLabel12)
										.addComponent(txtNgayKG, javax.swing.GroupLayout.PREFERRED_SIZE, 350,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel14).addComponent(txtNgayTao,
												javax.swing.GroupLayout.PREFERRED_SIZE, 350,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(
								jPanel1Layout.createSequentialGroup()
										.addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 73,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 73,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(27, 27, 27)
										.addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(29, 29, 29).addComponent(btnLast,
												javax.swing.GroupLayout.PREFERRED_SIZE, 70,
												javax.swing.GroupLayout.PREFERRED_SIZE)))));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 19,
												Short.MAX_VALUE)
										.addComponent(jLabel12))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(txtChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtNgayKG, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 19,
												Short.MAX_VALUE)
										.addComponent(jLabel13))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 19,
												Short.MAX_VALUE)
										.addComponent(jLabel14))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18).addComponent(jLabel7)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel1Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))));

		tabs.addTab("CẬP NHẬT", jPanel1);

		tblKhoaHoc.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Mã KH", "Thời Lượng", "Học Phí", "Khai Giảng", "Tạo Bởi", "Ngày Tạo" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tblKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblKhoaHocMouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(tblKhoaHoc);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
						.addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
						.addContainerGap()));

		tabs.addTab("DANH SÁCH", jPanel3);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(tabs))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(tabs)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboChuyenDeActionPerformed
		// TODO add your handling code here:
		chonChuyenDe();
	}// GEN-LAST:event_cboChuyenDeActionPerformed

	private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
		// TODO add your handling code here:
		insert();
	}// GEN-LAST:event_btnThemActionPerformed

	private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSuaActionPerformed
		// TODO add your handling code here:
		update();
	}// GEN-LAST:event_btnSuaActionPerformed

	private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaActionPerformed
		// TODO add your handling code here:
		delete();
	}// GEN-LAST:event_btnXoaActionPerformed

	private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnMoiActionPerformed
		// TODO add your handling code here:
		clearForm();
	}// GEN-LAST:event_btnMoiActionPerformed

	private void tblKhoaHocMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblKhoaHocMouseClicked
		// TODO add your handling code here:
		if (evt.getClickCount() == 2) {
			this.row = tblKhoaHoc.rowAtPoint(evt.getPoint());
			if (this.row >= 0) {
				this.edit();
				tabs.setSelectedIndex(0);
			}
		}
	}// GEN-LAST:event_tblKhoaHocMouseClicked

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

	private void btnHocvienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHocvienActionPerformed
		openHocVien();
	}// GEN-LAST:event_btnHocvienActionPerformed

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
			java.util.logging.Logger.getLogger(KhoahocForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(KhoahocForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(KhoahocForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(KhoahocForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				KhoahocForm dialog = new KhoahocForm(new javax.swing.JFrame(), true);
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
	private javax.swing.JButton btnHocvien;
	private javax.swing.JButton btnLast;
	private javax.swing.JButton btnMoi;
	private javax.swing.JButton btnNext;
	private javax.swing.JButton btnPrev;
	private javax.swing.JButton btnSua;
	private javax.swing.JButton btnThem;
	private javax.swing.JButton btnXoa;
	private javax.swing.JComboBox<String> cboChuyenDe;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTabbedPane tabs;
	private javax.swing.JTable tblKhoaHoc;
	private javax.swing.JTextField txtChuyenDe;
	private javax.swing.JTextArea txtGhiChu;
	private javax.swing.JTextField txtHocPhi;
	private javax.swing.JTextField txtNgayKG;
	private javax.swing.JTextField txtNgayTao;
	private javax.swing.JTextField txtNguoiTao;
	private javax.swing.JTextField txtThoiLuong;
	// End of variables declaration//GEN-END:variables
}
