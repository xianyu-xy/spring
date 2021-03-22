package com.vue.pojo;

public class Pinglun {
	private int plid;
	private String pldate;
	private String pluser;
	private String contenttitle;
	private String plcontent;
	private int faid;
	private Blog blog;
	public int getPlid() {
		return plid;
	}
	public void setPlid(int plid) {
		this.plid = plid;
	}
	public String getPldate() {
		return pldate;
	}
	public void setPldate(String pldate) {
		this.pldate = pldate;
	}
	public String getPluser() {
		return pluser;
	}
	public void setPluser(String pluser) {
		this.pluser = pluser;
	}
	public String getContenttitle() {
		return contenttitle;
	}
	public void setContenttitle(String contenttitle) {
		this.contenttitle = contenttitle;
	}
	public String getPlcontent() {
		return plcontent;
	}
	public void setPlcontent(String plcontent) {
		this.plcontent = plcontent;
	}
	public int getFaid() {
		return faid;
	}
	public void setFaid(int faid) {
		this.faid = faid;
	}
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	@Override
	public String toString() {
		return "Pinglun [plid=" + plid + ", pldate=" + pldate + ", pluser=" + pluser + ", contenttitle=" + contenttitle
				+ ", plcontent=" + plcontent + ", faid=" + faid + ", blog=" + blog + "]";
	}
	
	
}