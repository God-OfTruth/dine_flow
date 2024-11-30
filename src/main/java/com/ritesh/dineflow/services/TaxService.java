package com.ritesh.dineflow.services;

import com.ritesh.dineflow.exceptions.ResourceAlreadyPresentException;
import com.ritesh.dineflow.exceptions.ResourceNotFoundException;
import com.ritesh.dineflow.models.Tax;
import com.ritesh.dineflow.repositories.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxService {

	@Autowired
	private TaxRepository taxRepository;

	public void createTaxEntry(Tax tax) {
		if (tax.getId() == null) {
			taxRepository.save(tax);
			return;
		}
		throw new ResourceAlreadyPresentException("Tax Input is Already Present");
	}

	public void updateTaxEntry(Tax tax) {
		if (tax.getId() == null) {
			throw new ResourceNotFoundException("Tax Entry Not Found");
		}
		taxRepository.save(tax);
	}

	public Tax getTaxEntryById(String id) {
		return taxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Tax Entry Found"));
	}

	public List<Tax> getAllTaxEntries(){
		return taxRepository.findAll();
	}
}
