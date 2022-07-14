package com.algaworks.glauber.algafood.api.controller;

import static com.algaworks.glauber.algafood.infrastructure.repository.specification.PurchaseSpecifications.usingFilter;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.PurchaseModelAssembler;
import com.algaworks.glauber.algafood.api.assembler.PurchaseSummaryModelAssembler;
import com.algaworks.glauber.algafood.api.model.PurchaseModel;
import com.algaworks.glauber.algafood.api.model.PurchaseSummaryModel;
import com.algaworks.glauber.algafood.api.model.input.PurchaseInpurDisassembler;
import com.algaworks.glauber.algafood.api.model.input.PurchaseInput;
import com.algaworks.glauber.algafood.core.data.PageableTranslator;
import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.filter.PurchaseFilter;
import com.algaworks.glauber.algafood.domain.model.Purchase;
import com.algaworks.glauber.algafood.domain.model.User;
import com.algaworks.glauber.algafood.domain.repository.PurchaseRepository;
import com.algaworks.glauber.algafood.domain.service.PurchaseRegistrationService;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
	
	@Autowired
	private PurchaseRegistrationService purchaseRegistrationService;
	
	@Autowired
	private PurchaseModelAssembler purchaseModelAssembler;
	
	@Autowired
	private PurchaseInpurDisassembler purchaseInpurDisassembler;
	
	@Autowired
	private PurchaseSummaryModelAssembler purchaseSummaryModelAssembler;
	
	@Autowired
	private PurchaseRepository purchaseRepository;

	@GetMapping
	public Page<PurchaseSummaryModel> filter(PurchaseFilter purchaseFilter, @PageableDefault(size = 10) Pageable pageable) {
		pageable = translatePageable(pageable);
		
		Page<Purchase> purchasesPage = purchaseRepository.findAll(usingFilter(purchaseFilter), pageable);
		
		List<PurchaseSummaryModel> purchaseSummaryModel = purchaseSummaryModelAssembler
				.toCollectionModel(purchasesPage.getContent());
		
		return new PageImpl<>(purchaseSummaryModel, pageable, purchasesPage.getTotalElements());
	}

//	@GetMapping
//	public MappingJacksonValue indexMappingJacksonValue(@RequestParam(required = false) String fields) {
//		List<Purchase> purchases = purchaseRepository.findAll();
//		List<PurchaseSummaryModel> purchasesModel = purchaseSummaryModelAssembler.toCollectionModel(purchases);
//		
//		MappingJacksonValue purchasesWrapper = new MappingJacksonValue(purchasesModel);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("purchaseFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if (StringUtils.isNotBlank(fields)) {
//			filterProvider.addFilter("purchaseFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
//		}
//		
//		purchasesWrapper.setFilters(filterProvider);
//		
//		return purchasesWrapper;
//	}
	
//	@GetMapping
//	public List<PurchaseSummaryModel> index() {
//		return purchaseSummaryModelAssembler.toCollectionModel(purchaseRepository.findAll());
//	}
	
	@GetMapping("/{purchaseCode}")
	public PurchaseModel show(@PathVariable String purchaseCode) {
		return purchaseModelAssembler
				.toModel(purchaseRegistrationService
						.findPurchaseByCodeOrElseThrow(purchaseCode));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PurchaseModel create(@RequestBody @Valid PurchaseInput purchaseInput) {
		try {
			Purchase purchase = purchaseInpurDisassembler.toDomainObject(purchaseInput);
			
			//TODO: pegar o usuário logado
			User client = new User();
			client.setId(1L);
			purchase.setClient(client);
			
			return purchaseModelAssembler.toModel(purchaseRegistrationService.save(purchase));
			
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e); 
		}
	}
	
	private Pageable translatePageable(Pageable apiPageable) {
		//esse mapeamento diz quais propriedades podem ser realizadas a ordenação
		//caso o client passe propriedades que não existe nessa lista será ignorado pelo
		//método translate
		var sortPropertyMapping = Map.of(
			"code", "code",
			"subTotal", "subTotal",
			"freightRate", "freightRate",
			"totalAmount", "totalAmount",
			"createdAt", "createdAt",
			"restaurant.name", "restaurant.name",
			"client.name", "client.name"
		);
		
		return PageableTranslator.translate(apiPageable, sortPropertyMapping);
	}
}
