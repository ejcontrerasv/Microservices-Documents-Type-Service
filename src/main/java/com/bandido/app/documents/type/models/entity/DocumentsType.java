package com.bandido.app.documents.type.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Informacion asociada a los tipos de documentos")
@Entity
@Data
@Table(name = "documents_type")
public class DocumentsType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ApiModelProperty(notes = "El nombre del tipo de documento debe tener minimo 3 caracteres", required = true)
	@Size(min = 3, message = "El nombre del tipo de documento debe tener minimo 3 caracteres")
	@Column(nullable = false, length = 30)
	private String name;
	
	@ApiModelProperty(notes = "La descripcion debe tener minimo 5 caracteres", required = false)
	@Size(min =5, message = "La descripcion debe tener minimo 5 caracteres")
	@Column(nullable = true, length = 255)
	private String description;
	
	@JsonIgnoreProperties
	@ApiModelProperty(notes = "nombre del estado del typo de documento : status-service", required = true)
	@Column(name = "status_name", nullable = false, updatable = true)
	private String statusName;

}
