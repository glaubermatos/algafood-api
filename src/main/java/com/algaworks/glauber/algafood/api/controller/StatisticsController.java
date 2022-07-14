package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.domain.filter.DailySalesFilter;
import com.algaworks.glauber.algafood.domain.model.dto.DailySales;
import com.algaworks.glauber.algafood.domain.service.DailySalesReportService;
import com.algaworks.glauber.algafood.domain.service.SalesQueryService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
	
	@Autowired
	private SalesQueryService salesQueryService;
	
	@Autowired
	private DailySalesReportService dailySalesReportService;

	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DailySales> findDailySales(DailySalesFilter dailySalesFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return salesQueryService.findDailySales(dailySalesFilter, timeOffset);
	}

	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> findDailySalesPdf(DailySalesFilter dailySalesFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		byte[] bytesPdf = dailySalesReportService.emitDailySales(dailySalesFilter, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
}
