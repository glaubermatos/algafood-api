package com.algaworks.glauber.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public class DailySales {

	private Date date;
	private Long totalSales;
	private BigDecimal totalInvoiced;
	
	public DailySales() {}
	
	public DailySales(Date date, Long totalSales, BigDecimal totalInvoiced) {
		this.date = date;
		this.totalSales = totalSales;
		this.totalInvoiced = totalInvoiced;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}
	public BigDecimal getTotalInvoiced() {
		return totalInvoiced;
	}
	public void setTotalInvoiced(BigDecimal totalInvoiced) {
		this.totalInvoiced = totalInvoiced;
	}
}
