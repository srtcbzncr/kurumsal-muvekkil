package com.bzncrsrtc.kurumsalmuvekkil.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity(name="files")
public class File {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@ManyToOne()
	@JoinColumn(name="company_id", referencedColumnName="id")
	private Company company;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany(mappedBy="files")
	private List<Lawyer> lawyers;
	
	@ManyToMany(mappedBy="files")
	private List<Client> clients;
	
	@OneToMany(mappedBy="file", cascade=CascadeType.ALL)
	private List<Update> updates;
	
	@ManyToOne()
	@JoinColumn(name="court_id", referencedColumnName="id")
	private Court court;
	
	@Column(name="court_detail")
	private String courtDetail;
	
	@Column(name="deleted")
	private boolean deleted = false;
	
	@Column(name="active")
	private boolean active = true;
	
	@Column(name="created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@Column(name="deleted_at")
	private LocalDateTime deletedAt;
	
}
