package com.project.consorcio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.consorcio.entity.TipoMedicamento;

import com.project.consorcio.repository.TipoMedicamentoRepository;

@Service
public class TipoMedicamentoServices {
	
	@Autowired
	private TipoMedicamentoRepository repo;
	

	public List<TipoMedicamento> listarTodos(){  //Solo se va a necesitar el combo
		return repo.findAll();
	}
	

}
