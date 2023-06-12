package com.bzncrsrtc.kurumsalmuvekkil.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.FileNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.FileRepository;

@Service
public class FileService {

	private FileRepository fileRepository;
	private MessageSource messageSource;
	private Logger logger = LoggerFactory.getLogger(FileService.class);
	
	public FileService(FileRepository fileRepository, MessageSource messageSource) {
		this.fileRepository = fileRepository;
		this.messageSource = messageSource;
	}
	
	public List<File> findAll(Locale locale){
		return fileRepository.findAllByDeletedAndActive(false, true);
	}
	
	public List<File> findAllDeleted(Locale locale){
		return fileRepository.findAllByDeleted(true);
	}
	
	public List<File> findAllPassive(Locale locale){
		return fileRepository.findAllByDeletedAndActive(false, false);
	}
	
	public File findById(UUID id, Locale locale) {
		Optional<File> file = fileRepository.findByIdAndDeletedAndActive(id, false, true);
		
		if(file.isEmpty()) {
			throw new FileNotFoundException(messageSource.getMessage("file.not.found.message", null, locale));
		}
		
		return file.get();
	}
	
	public File create(File file, Locale locale) {
		File savedFile = fileRepository.save(file);
		return savedFile;
	}
	
	public void update(File file, Locale locale) {
		if(!fileRepository.existsByIdAndDeleted(file.getId(), false)) {
			throw new FileNotFoundException(messageSource.getMessage("file.not.found.message", null, locale));
		}
		
		fileRepository.save(file);
	}
	
	public void delete(UUID id, Locale locale) {
		Optional<File> optionalFile = fileRepository.findByIdAndDeleted(id, false);
		
		if(optionalFile.isEmpty()) {
			throw new FileNotFoundException(messageSource.getMessage("file.not.found.message", null, locale));
		}
		
		File file = optionalFile.get();
		file.setDeleted(true);
		file.setDeletedAt(LocalDateTime.now());
		
		fileRepository.save(file);
	}
	
}
