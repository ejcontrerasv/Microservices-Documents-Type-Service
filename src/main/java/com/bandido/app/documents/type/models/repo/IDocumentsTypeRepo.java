package com.bandido.app.documents.type.models.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bandido.app.documents.type.models.entity.DocumentsType;

public interface IDocumentsTypeRepo extends JpaRepository<DocumentsType, Integer>{

}
