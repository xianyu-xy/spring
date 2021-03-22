package com.vue.pojo;

public class Blog {
	private int blogid;
	private String date;
	private String bloguser;
	private String blogtitle;
	private String blogcontent;
	private String huifushu;
	public int getBlogid() {
		return blogid;
	}
	public void setBlogid(int blogid) {
		this.blogid = blogid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBloguser() {
		return bloguser;
	}
	public void setBloguser(String bloguser) {
		this.bloguser = bloguser;
	}
	public String getBlogtitle() {
		return blogtitle;
	}
	public void setBlogtitle(String blogtitle) {
		this.blogtitle = blogtitle;
	}
	public String getBlogcontent() {
		return blogcontent;
	}
	public void setBlogcontent(String blogcontent) {
		this.blogcontent = blogcontent;
	}
	public String getHuifushu() {
		return huifushu;
	}
	public void setHuifushu(String huifushu) {
		this.huifushu = huifushu;
	}
	@Override
	public String toString() {
		return "Blog [blogid=" + blogid + ", date=" + date + ", bloguser=" + bloguser + ", blogtitle=" + blogtitle
				+ ", blogcontent=" + blogcontent + ", huifushu=" + huifushu + "]";
	}
	

}
