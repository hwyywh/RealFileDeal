
package com.deal;

public class TaxiInfo {
	private String TaxiID;
	private String DateTime;
	private String Long;
	private String Lat;
	private String Speed;
	private String Angle;
	private String Extend;
		
	public String getTaxiID() {
		return TaxiID;
	}

	public void setTaxiID(String taxiID) {
		TaxiID = taxiID;
	}

	public String getDateTime() {
		return DateTime;
	}

	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}

	public String getLong() {
		return Long;
	}

	public void setLong(String l) {
		Long = l;
	}

	public String getLat() {
		return Lat;
	}

	public void setLat(String lat) {
		Lat = lat;
	}

	public String getSpeed() {
		return Speed;
	}

	public void setSpeed(String speed) {
		Speed = speed;
	}

	public String getAngle() {
		return Angle;
	}

	public void setAngle(String angle) {
		Angle = angle;
	}

	public String getExtend() {
		return Extend;
	}

	public void setExtend(String extend) {
		Extend = extend;
	}






	//重写toString方法
	//当需要将一个对象输出到显示器时,通常要调用他的toString()方法,将对象的内容转换为字符串.java中的所有类默认都有一个toString()方法
	//默认情况下 System.out.println(对象名)或者System.out.println(对象名.toString())输出的是此对象的类名和此对象对应内存的首地址如果想自定义输出信息必须重写toString()方法
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("车辆ID："+TaxiID+"\n"+"时间："+DateTime+";"+"经度："+Long+";"+"纬度："+Lat+";"+"速度："+Speed+";"+"角度"+Angle+";"+"Extend"+Extend);
	}
	
	
	
	

}

