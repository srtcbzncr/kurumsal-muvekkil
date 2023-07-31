package com.bzncrsrtc.kurumsalmuvekkil.controllers;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bzncrsrtc.kurumsalmuvekkil.mappers.RequestMapper;
import com.bzncrsrtc.kurumsalmuvekkil.mappers.ResponseMapper;
import com.bzncrsrtc.kurumsalmuvekkil.models.Court;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.requests.CreateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.requests.UpdateCourtRequest;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtDetailsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtStatiscticsResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtWithoutParentResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetFileWithoutCourtResponse;
import com.bzncrsrtc.kurumsalmuvekkil.responses.ResponseHandler;
import com.bzncrsrtc.kurumsalmuvekkil.services.CourtService;
import com.bzncrsrtc.kurumsalmuvekkil.responses.GetCourtStatiscticsResponse;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/court")
public class CourtController {

	private final CourtService courtService;
	private final ResponseMapper responseMapper;
	private final RequestMapper requestMapper;
	
	public CourtController(CourtService courtService, ResponseMapper responseMapper, RequestMapper requestMapper) {
		this.courtService = courtService;
		this.responseMapper = responseMapper;
		this.requestMapper = requestMapper;
	}
	
	@GetMapping("/stats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> statistics(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		int allCount = courtService.allCount(locale);
		int activeCount = courtService.activeCount(locale);
		int passiveCount = courtService.passiveCount(locale);
		int deletedCount = courtService.deletedCount(locale);
		
		GetCourtStatiscticsResponse response = new GetCourtStatiscticsResponse(allCount, activeCount, passiveCount, deletedCount);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}/stats")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> subStatistics(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		int allCountByParentId = courtService.allCountByParentId(id, locale);
		int activeCountByParentId = courtService.activeCountByParentId(id, locale);
		int passiveCountByParentId = courtService.passiveCountByParentId(id, locale);
		int deletedCountByParentId = courtService.deletedCountByParentId(id, locale);
		
		GetCourtStatiscticsResponse response = new GetCourtStatiscticsResponse(allCountByParentId, activeCountByParentId, passiveCountByParentId, deletedCountByParentId);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAll(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Court> courts = courtService.findAll(locale);
		List<GetCourtResponse> response = responseMapper.getCourtListResponse(courts);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/active")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LAWYER') or hasRole('ROLE_CLIENT')")
	public ResponseEntity<Object> findAllActive(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Court> activeCourts = courtService.findAllActive(locale);
		List<GetCourtResponse> response = responseMapper.getCourtListResponse(activeCourts);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/passive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllPassive(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Court> passiveCourts = courtService.findAllPassive(locale);
		List<GetCourtResponse> response = responseMapper.getCourtListResponse(passiveCourts);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/deleted")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllDeleted(@RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Court> deletedCourts = courtService.findAllDeleted(locale);
		List<GetCourtResponse> response = responseMapper.getCourtListResponse(deletedCourts);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}/subs/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllByParentId(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Court> courts = courtService.findAllByParentId(id, locale);
		List<GetCourtWithoutParentResponse> response = responseMapper.getCourtWithoutParentListResponse(courts);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}/subs/active")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LAWYER') or hasRole('ROLE_CLIENT')")
	public ResponseEntity<Object> findAllActiveByParentId(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Court> courts = courtService.findAllActiveByParentId(id, locale);
		List<GetCourtWithoutParentResponse> response = responseMapper.getCourtWithoutParentListResponse(courts);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}/subs/passive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllPassiveByParentId(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Court> courts = courtService.findAllPassiveByParentId(id, locale);
		List<GetCourtWithoutParentResponse> response = responseMapper.getCourtWithoutParentListResponse(courts);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}/subs/deleted")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> findAllDeletedByParentId(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		List<Court> courts = courtService.findAllDeletedByParentId(id, locale);
		List<GetCourtWithoutParentResponse> response = responseMapper.getCourtWithoutParentListResponse(courts);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LAWYER') or hasRole('ROLE_CLIENT')")
	public ResponseEntity<Object> findById(@PathVariable UUID id, @RequestHeader(name = "Accept-Language", required = false) String localeStr){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Court court = courtService.findById(id, locale);
		GetCourtDetailsResponse response = responseMapper.getCourtDetailsResponse(court);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> create(@RequestHeader(name = "Accept-Language", required = false) String localeStr, @Valid @RequestBody CreateCourtRequest createCourtRequest){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Court court = requestMapper.fromCreateCourtRequestToCourt(createCourtRequest);
		Court savedCourt = courtService.create(court, locale);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCourt.getId()).toUri();
		
		return ResponseHandler.generateResponse(location, HttpStatus.CREATED, null);
	}	
	
	@PutMapping("")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> update(@RequestHeader(name = "Accept-Language", required = false) String localeStr, @Valid @RequestBody UpdateCourtRequest updateCourtRequest){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Court court = requestMapper.fromUpdateCourtRequestToCourt(updateCourtRequest);
		Court updatedCourt = courtService.update(court, locale);
		GetCourtResponse response = responseMapper.getCourtResponse(updatedCourt);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PutMapping("/{id}/setActive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setActive(@RequestHeader(name = "Accept-Language", required = false) String localeStr, @PathVariable UUID id){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Court court = courtService.setActive(id, locale);
		GetCourtResponse response = responseMapper.getCourtResponse(court);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@PutMapping("/{id}/setPassive")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> setPassive(@RequestHeader(name = "Accept-Language", required = false) String localeStr, @PathVariable UUID id){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		Court court = courtService.setPassive(id, locale);
		GetCourtResponse response = responseMapper.getCourtResponse(court);
		
		return ResponseHandler.generateResponse(response, HttpStatus.OK, null);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Object> delete(@RequestHeader(name = "Accept-Language", required = false) String localeStr, @PathVariable UUID id){
		Locale locale = (localeStr != null && localeStr.equals("en")) ? new Locale("en") : new Locale("tr");
		
		courtService.delete(id, locale);
		
		return ResponseHandler.generateResponse(null, HttpStatus.OK, null);
	}
}
