package com.bzncrsrtc.kurumsalmuvekkil.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name="updates")
public class Update {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@ManyToOne()
	private File file;
	
	@ManyToOne()
	private Lawyer lawyer;
	
	@Column(name="state")
	private String state;
	
	@Column(name="content")
	private String content;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
}
