package com.bandido.app.documents.type.models.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bandido.app.documents.type.dto.Status;
import com.bandido.app.documents.type.exception.ModeloNotFoundException;
import com.bandido.app.documents.type.models.entity.DocumentsType;
import com.bandido.app.documents.type.models.repo.IDocumentsTypeRepo;
import com.bandido.app.documents.type.models.service.IDocumentsTypeService;

@Service("serviceRestTemplate")
public class DocumentsTypeServiceImpl implements IDocumentsTypeService {

	@Autowired
	private IDocumentsTypeRepo repo;
	
	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public DocumentsType registrar(DocumentsType obj) {
		Integer statusToCreate = 1;
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", statusToCreate.toString());
		Status status = clienteRest.getForObject("http://status-service/app/status/listar/{id}", Status.class, pathVariables);
		
		if (status.getId() == null) {
			throw new ModeloNotFoundException("NO SE ENCONTRO STATUS CON ID : [" + statusToCreate + "]");
		}
		
		obj.setStatusName(status.getName());
		return repo.save(obj);
	}
	
	@Override
	public DocumentsType registrarFallBack(DocumentsType obj) {
		obj.setStatusName("FallBack");
		return repo.save(obj);
	}

	@Override
	public DocumentsType modificar(DocumentsType obj) {
		Integer statusToModified = 5;
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", statusToModified.toString());
		Status status = clienteRest.getForObject("http://status-service/app/status/listar/{id}", Status.class, pathVariables);
		
		if (status.getId() == null) {
			throw new ModeloNotFoundException("NO SE ENCONTRO STATUS CON ID : [" + statusToModified + "]");
		}
		
		obj.setStatusName(status.getName());
		return repo.save(obj);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DocumentsType> listar() {
		return (List<DocumentsType>) repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public DocumentsType leerPorId(Integer id) {
		Optional<DocumentsType> op = repo.findById(id);
		return op.isPresent() ? op.get() : new DocumentsType(); 
	}

	@Override
	public boolean eliminar(Integer id) {
		repo.deleteById(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DocumentsType> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

}
