package niit.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class main extends Activity implements OnClickListener {
	Button btnNhapDiem;

	Button btnXetHocLuc;

	EditText edtDiemToan;

	EditText edtDiemLy;

	EditText edtDiemHoa;

	SinhVien sinhVien;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo);
		if (true) return;
		btnNhapDiem = (Button)findViewById(R.id.btnNhapDiem);
		btnNhapDiem.setOnClickListener(this);
		btnXetHocLuc = (Button)findViewById(R.id.btnXetHocLuc);
		btnXetHocLuc.setOnClickListener(this);
		edtDiemToan = (EditText)findViewById(R.id.edtDiemToan);
		edtDiemLy = (EditText)findViewById(R.id.edtDiemLy);
		edtDiemHoa = (EditText)findViewById(R.id.edtDiemHoa);
		sinhVien = new SinhVien();
		sinhVien.mssv = "0110";
		sinhVien.hoTen = "Lê Đức Huy";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnNhapDiem:
				nhapDiem();
				break;
			case R.id.btnXetHocLuc:
				xetHocLuc();
				break;
			default:
				break;
		}
	}

	public void nhapDiem() {
		float diemToan = Float.valueOf(edtDiemToan.getText().toString());
		float diemLy = Float.valueOf(edtDiemLy.getText().toString());
		float diemHoa = Float.valueOf(edtDiemHoa.getText().toString());
		sinhVien.diemToan = diemToan;
		sinhVien.diemLy = diemLy;
		sinhVien.diemHoa = diemHoa;
	}

	public void xetHocLuc() {
		String hocLuc = sinhVien.xetHocLuc();
		Toast toast = Toast.makeText(this, "Sinh viên " + sinhVien.hoTen + " xếp loại " + hocLuc, 3000);
		toast.show();
	}
}
