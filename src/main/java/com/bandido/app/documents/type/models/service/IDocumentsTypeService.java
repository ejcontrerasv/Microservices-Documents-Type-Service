package com.bandido.app.documents.type.models.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bandido.app.documents.type.models.entity.DocumentsType;

public interface IDocumentsTypeService extends ICRUD<DocumentsType> {
	
	Page<DocumentsType> listarPageable(Pageable pageable);
}
