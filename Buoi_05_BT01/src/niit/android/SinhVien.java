package niit.android;

public class SinhVien {
	public String mssv;

	public String hoTen;

	public float diemToan = 0;

	public float diemLy = 0;

	public float diemHoa = 0;

	public String xetHocLuc() {
		float diemTrungBinh = tinhDiemTrungBinh();
		String hocLuc = "";
		if (diemTrungBinh < 5)
			hocLuc = "Yếu";
		else if (diemTrungBinh < 6.5)
			hocLuc = "Trung bình";
		else if (diemTrungBinh < 8)
			hocLuc = "Khá";
		else if (diemTrungBinh < 9)
			hocLuc = "Giỏi";
		else
			hocLuc = "Xuất sắc";
		return hocLuc;
	}

	public float tinhDiemTrungBinh() {
		float diemTrungBinh = 0;
		diemTrungBinh = (float) ( (diemToan + diemLy + diemHoa) / 3.0);
		return diemTrungBinh;
	}

	public void nhapDiem(float diemToanIn, float diemLyIn, float diemHoaIn) {
		this.diemToan = diemToanIn;
		this.diemLy = diemLyIn;
		this.diemHoa = diemHoaIn;
	}
}
