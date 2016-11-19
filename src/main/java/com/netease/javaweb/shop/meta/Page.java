package com.netease.javaweb.shop.meta;



public class Page {
	
	public Page(){
		
	};
	
	
	public Page(int pagenumber, int count) {
		this.count = count;
		this.pagenumber = pagenumber;
	}


	private int count;
	private int pagenumber;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPagenumber() {
		return pagenumber;
	}
	public void setPagenumber(int pagenumber) {
		this.pagenumber = pagenumber;
	}


	@Override
	public String toString() {
		return "Page [count=" + count + ", pagenumber=" + pagenumber + "]";
	}
	
}
