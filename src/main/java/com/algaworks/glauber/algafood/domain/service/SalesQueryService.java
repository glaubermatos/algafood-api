package com.algaworks.glauber.algafood.domain.service;

import java.util.List;

import com.algaworks.glauber.algafood.domain.filter.DailySalesFilter;
import com.algaworks.glauber.algafood.domain.model.dto.DailySales;

public interface SalesQueryService {

	List<DailySales> findDailySales(DailySalesFilter dailySalesFilter, String timeOffSet);
}
