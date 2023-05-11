package com.bzncrsrtc.kurumsalmuvekkil.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

@Entity(name="clients")
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@Column(name="identification_number")
	private String identificationNumber;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "client_file", 
        joinColumns = { @JoinColumn(name = "client_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "file_id") }
    )
	private List<File> files;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;

}
