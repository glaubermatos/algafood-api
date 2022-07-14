package com.algaworks.glauber.algafood.infrastructure.service.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.filter.DailySalesFilter;
import com.algaworks.glauber.algafood.domain.model.dto.DailySales;
import com.algaworks.glauber.algafood.domain.service.DailySalesReportService;
import com.algaworks.glauber.algafood.domain.service.SalesQueryService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfDailySalesReportServiceImpl implements DailySalesReportService {

	@Autowired
	private SalesQueryService salesQueryService;
	
	@Override
	public byte[] emitDailySales(DailySalesFilter dailySalesFilter, String timeOffSet) {
		try {
			var inputStream = this.getClass().getResourceAsStream("/reports/daily-sales.jasper");
			
			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
			
			List<DailySales> dailySales = salesQueryService.findDailySales(dailySalesFilter, timeOffSet);
			List<VendaDiaria> vendasDiarias = convertCollectionModel(dailySales);
			
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
			
			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
		
			return JasperExportManager.exportReportToPdf(jasperPrint);
			
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}
	
	private List<VendaDiaria> convertCollectionModel(List<DailySales> dailySales) {
		
		List<VendaDiaria> vendasDiaria = new ArrayList<>();
		
		dailySales.forEach(sales -> {
			vendasDiaria.add(new VendaDiaria(
					sales.getDate(), 
					sales.getTotalSales(), 
					sales.getTotalInvoiced()
			));
		});
		
		
		return vendasDiaria;
	}

	//necessário criar essa classe por que os fields utilizados no relatório .jasper estão em portugues
	public class VendaDiaria {
		
		private Date data;
		private Long totalVendas;
		private BigDecimal totalFaturado;
		
		public VendaDiaria() {}
		
		public VendaDiaria(Date data, Long totalVendas, BigDecimal totalFaturado) {
			this.data = data;
			this.totalVendas = totalVendas;
			this.totalFaturado = totalFaturado;
		}
		public Date getData() {
			return data;
		}
		public void setData(Date data) {
			this.data = data;
		}
		public Long getTotalVendas() {
			return totalVendas;
		}
		public void setTotalVendas(Long totalVendas) {
			this.totalVendas = totalVendas;
		}
		public BigDecimal getTotalFaturado() {
			return totalFaturado;
		}
		public void setTotalFaturado(BigDecimal totalFaturado) {
			this.totalFaturado = totalFaturado;
		}
		
		
	}

}
