package com.bzncrsrtc.kurumsalmuvekkil.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name="files")
public class File {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@ManyToOne()
	@JoinColumn(name="company_id", referencedColumnName="id")
	private Company company;
	
	@ManyToMany(mappedBy="files")
	private Lawyer lawyer;
	
	@ManyToMany(mappedBy="files")
	private Client client;
	
	@OneToMany(mappedBy="file")
	private List<Update> updates;
	
	@ManyToOne()
	@JoinColumn(name="court_id", referencedColumnName="id")
	private Court court;
	
	@Column(name="court_detail")
	private String courtDetail;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
}
