package com.tiangua.fast.db.model;

public class User {

	/**
	 * 用户id
	 */
	public static final String UID = "uid";

	/**
	 * 用户名
	 */
	public static final String NAME = "u_name";

	/**
	 * 用户密码
	 */
	public static final String PASS = "u_pass";

	/**
	 * 头像的下载地址
	 */
	public static final String LOGOURL = "u_pic";

	/**
	 * 邮箱
	 */
	public static final String EMAIL = "u_email";

	/**
	 * 手机号码
	 */
	public static final String PHONENUMBER = "u_phone";

	/**
	 * 汽车币
	 */
	public static final String CARCOIN = "credits";

	/**
	 * 汽车信息
	 */
	public static final String MYCAR = "car_info";

	/**
	 * 汽车品牌
	 */
	public static final String CAR_BRAND = "car_brand";

	/**
	 * 汽车型号
	 */
	public static final String CAR_TYPE = "car_series";

	/**
	 * 汽车价格
	 */
	public static final String CAR_PRICE = "car_price";

	/**
	 * 汽车行驶公里数
	 */
	public static final String CAR_RUN_KM = "km";

	/**
	 * 汽车颜色
	 */
	public static final String CAR_COLOR = "car_color";

	/**
	 * 汽车购买年份
	 */
	public static final String CAR_BUY_YEAR = "buy_year";

	
	private int _id = -1;
	
	private int uid = -1;

	private String u_name = null;

	private String u_pass = null;

	private String u_pic = null;

	private String u_email = null;

	private String u_phone = null;

	private String credits = null;

	private String car_brand = null; // 车品牌

	private String car_series = null; // 车的型号

	private double car_price = 0.0; // 车的价格

	private float km = 0f; // 车的行驶公里数

	private String carColor = null; // 车的颜色

	private long buy_year = 0l; // 购车年份

	private boolean isTrue = false;

	
	
	public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_pass() {
		return u_pass;
	}

	public void setU_pass(String u_pass) {
		this.u_pass = u_pass;
	}

	public String getU_pic() {
		return u_pic;
	}

	public void setU_pic(String u_pic) {
		this.u_pic = u_pic;
	}

	public String getU_email() {
		return u_email;
	}

	public void setU_email(String u_email) {
		this.u_email = u_email;
	}

	public String getU_phone() {
		return u_phone;
	}

	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getCar_brand() {
		return car_brand;
	}

	public void setCar_brand(String car_brand) {
		this.car_brand = car_brand;
	}

	public String getCar_series() {
		return car_series;
	}

	public void setCar_series(String car_series) {
		this.car_series = car_series;
	}

	public double getCar_price() {
		return car_price;
	}

	public void setCar_price(double car_price) {
		this.car_price = car_price;
	}

	public float getKm() {
		return km;
	}

	public void setKm(float km) {
		this.km = km;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public long getBuy_year() {
		return buy_year;
	}

	public void setBuy_year(long buy_year) {
		this.buy_year = buy_year;
	}
	
	

	public boolean isTrue() {
		return isTrue;
	}

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
