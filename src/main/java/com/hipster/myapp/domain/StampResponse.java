package com.hipster.myapp.domain;

public class StampResponse {
	private String status;
	private String temporary_rd;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTemporary_rd() {
		return temporary_rd;
	}
	public void setTemporary_rd(String temporary_rd) {
		this.temporary_rd = temporary_rd;
	}
	
	@Override
	public String toString() {
		return "{status:" + status + ", temporary_rd:" + temporary_rd + "}";
	}
	
	
	

}
