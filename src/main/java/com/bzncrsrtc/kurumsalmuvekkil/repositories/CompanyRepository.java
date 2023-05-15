package com.bzncrsrtc.kurumsalmuvekkil.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bzncrsrtc.kurumsalmuvekkil.models.Company;

public interface CompanyRepository extends JpaRepository<Company, UUID>{

}
