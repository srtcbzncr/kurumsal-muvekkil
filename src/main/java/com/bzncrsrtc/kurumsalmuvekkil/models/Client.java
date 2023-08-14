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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name="clients")
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@NonNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@NonNull
	@Column(name="identification_number", unique=true, nullable=false)
	private String identificationNumber;
	
	@NonNull
	@Column(name="first_name", nullable=false)
	private String firstName;
	
	@NonNull
	@Column(name="last_name", nullable=false)
	private String lastName;
	
	@NonNull
	@Column(name="phone", unique=true, nullable=false)
	private String phone;
	
	@ManyToMany()
    @JoinTable(
        name = "client_file", 
        joinColumns = { @JoinColumn(name = "client_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "file_id") }
    )
	private List<File> files;
	
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
