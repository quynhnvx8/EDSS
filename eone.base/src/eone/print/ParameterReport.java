package eone.print;

public class ParameterReport {
	private boolean isBold;
	private String tableTemp;
	public String getTableTemp() {
		return tableTemp;
	}
	public void setTableTemp(String tableTemp) {
		this.tableTemp = tableTemp;
	}
	public boolean isBold() {
		return isBold;
	}
	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}
	public boolean isItalic() {
		return isItalic;
	}
	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}
	public boolean isBoldItalic() {
		return isBoldItalic;
	}
	public void setBoldItalic(boolean isBoldItalic) {
		this.isBoldItalic = isBoldItalic;
	}
	public int getBorder() {
		return border;
	}
	public void setBorder(int border) {
		this.border = border;
	}
	
	private boolean isHeader;
	private String result1;
	private String result2;
	private String result3;
	private String result4;
	private String result5;
	private String result6;
	private String result7;
	private String result8;
	private String result9;
	
	public boolean isHeader() {
		return isHeader;
	}
	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}
	public String getResult1() {
		return result1;
	}
	public void setResult1(String result1) {
		this.result1 = result1;
	}
	public String getResult2() {
		return result2;
	}
	public void setResult2(String result2) {
		this.result2 = result2;
	}
	public String getResult3() {
		return result3;
	}
	public void setResult3(String result3) {
		this.result3 = result3;
	}
	public String getResult4() {
		return result4;
	}
	public void setResult4(String result4) {
		this.result4 = result4;
	}
	public String getResult5() {
		return result5;
	}
	public void setResult5(String result5) {
		this.result5 = result5;
	}
	public String getResult6() {
		return result6;
	}
	public void setResult6(String result6) {
		this.result6 = result6;
	}
	public String getResult7() {
		return result7;
	}
	public void setResult7(String result7) {
		this.result7 = result7;
	}
	public String getResult8() {
		return result8;
	}
	public void setResult8(String result8) {
		this.result8 = result8;
	}
	public String getResult9() {
		return result9;
	}
	public void setResult9(String result9) {
		this.result9 = result9;
	}

	private boolean isItalic;
	private boolean isBoldItalic;
	private int border;
	
	public ParameterReport() {
		
	}
	public ParameterReport(boolean isBold, boolean isItalic, boolean isBoldItalic, int border) {
		setBold(isBold);
		setItalic(isItalic);
		setBoldItalic(isBoldItalic);
		setBorder(border);
	}
}
