package com.pepsico.demo.order.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="custorder",schema="public")
public class CustOrder implements Serializable {

	@Id
	private String id;
	private String description;
	private Double val;

	public CustOrder() {

	}
	
	public CustOrder(String id, String desc, Double val) {
		super();
		this.id = id;
		this.description = desc;
		this.val = val;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return description;
	}
	
	public void setDesc(String desc) {
		this.description = desc;
	}
	public Double getVal() {
		return val;
	}
	public void setVal(Double val) {
		this.val = val;
	}
}
