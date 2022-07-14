package com.algaworks.glauber.algafood.domain.service;

import com.algaworks.glauber.algafood.domain.filter.DailySalesFilter;

public interface DailySalesReportService {

	byte[] emitDailySales(DailySalesFilter dailySalesFilter, String timeOffSet);
}
