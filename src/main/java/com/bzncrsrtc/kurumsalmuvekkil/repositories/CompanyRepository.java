package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{

}
