package com.bzncrsrtc.kurumsalmuvekkil.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name="courts")
public class Court {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	private Court parent;
	
	@OneToMany(mappedBy="parent")
	private List<Court> subs;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="court")
	private List<File> files;

}
