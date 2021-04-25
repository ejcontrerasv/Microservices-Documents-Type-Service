package com.bandido.app.documents.type.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bandido.app.documents.type.exception.ModeloNotFoundException;
import com.bandido.app.documents.type.models.entity.DocumentsType;
import com.bandido.app.documents.type.models.service.IDocumentsTypeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/app/documents/type")
public class DocumentsTypeController {
	
	@Autowired
	@Qualifier(value = "serviceRestTemplate")
	private IDocumentsTypeService service;
	
	@HystrixCommand(fallbackMethod = "registrarFallBack", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10") })
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody DocumentsType document){
		DocumentsType doc = service.registrar(document);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(doc.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	public ResponseEntity<Object> registrarFallBack(DocumentsType obj) {
		log.info("Ocurrio una falla o latencia al consultar el servicio : status-service");
		DocumentsType doc = service.registrarFallBack(obj);
		return new ResponseEntity<Object>(doc, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<DocumentsType> modificar(@Valid @RequestBody DocumentsType docuemnt){
		DocumentsType doc = service.modificar(docuemnt);
		return new ResponseEntity<DocumentsType>(doc, HttpStatus.OK);
	}
	
	
	@GetMapping("/listarPageable")
	public ResponseEntity<Page<DocumentsType>> listarPageable(Pageable pageable){
		Page<DocumentsType> docs = service.listarPageable(pageable);
		return new ResponseEntity<Page<DocumentsType>>(docs, HttpStatus.OK);
	}
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<DocumentsType> ListarPorId(@PathVariable("id") Integer id) {
		DocumentsType doc = service.leerPorId(id);
		return new ResponseEntity<DocumentsType>(doc, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id){
		DocumentsType doc = service.leerPorId(id);
		if (doc.getId() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: [" + id +"]");
		}
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
