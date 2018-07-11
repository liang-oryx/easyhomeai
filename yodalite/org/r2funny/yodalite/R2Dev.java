package org.r2funny.yodalite;

public class R2Dev {
	private int		id;
	private String  name;
	private String  mac;
	private long 	regTime;
	private String	password;
	private String	token;
	private int		devType;
	private String  devPost;
	private String	devAddr;
	
	private long	uploadTime;		// 上传时间
	private String	uploadData;		// 上传的数据
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public long getRegTime() {
		return regTime;
	}
	public void setRegTime(long regTime) {
		this.regTime = regTime;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getDevType() {
		return devType;
	}
	public void setDevType(int devType) {
		this.devType = devType;
	}
	public String getDevPost() {
		return devPost;
	}
	public void setDevPost(String devPost) {
		this.devPost = devPost;
	}
	public String getDevAddr() {
		return devAddr;
	}
	public void setDevAddr(String devAddr) {
		this.devAddr = devAddr;
	}
	public long getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(long uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getUploadData() {
		return uploadData;
	}
	public void setUploadData(String uploadData) {
		this.uploadData = uploadData; 
	}  
	
}
