package com.bzncrsrtc.kurumsalmuvekkil.rules;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.bzncrsrtc.kurumsalmuvekkil.exceptions.FileNotFoundException;
import com.bzncrsrtc.kurumsalmuvekkil.exceptions.ThereIsNoFileException;
import com.bzncrsrtc.kurumsalmuvekkil.models.File;
import com.bzncrsrtc.kurumsalmuvekkil.repositories.FileRepository;

@Component
public class FileRules {

	private final FileRepository fileRepository;
	private final MessageSource messageSource;
	
	public FileRules(FileRepository fileRepository, MessageSource messageSource) {
		this.fileRepository = fileRepository;
		this.messageSource = messageSource;
	}

	public void isNull(Optional<File> file, Locale locale) {
		if(file.isEmpty()) {
			throw new FileNotFoundException(messageSource.getMessage("file.not.found.message", null, locale));
		}
	}
	
	public void isNull(File file, Locale locale) {
		if(file == null) {
			throw new FileNotFoundException(messageSource.getMessage("file.not.found.message", null, locale));
		}
	}
	
	public void isNull(List<File> files, Locale locale) {
		if(files.size() == 0) {
			throw new ThereIsNoFileException(messageSource.getMessage("there.is.no.file.message", null, locale));
		}
	}

	public void isThere(UUID id, Locale locale) {
		if(id == null || fileRepository.existsById(id)) {
			throw new FileNotFoundException(messageSource.getMessage("file.not.found.message", null, locale));
		}
	}
	
	public void isDeleted(File file, Locale locale) {
		if(file.isDeleted()) {
			throw new FileNotFoundException(messageSource.getMessage("file.not.found.message", null, locale));
		}
	}
	
	public void isPassive(File file, Locale locale) {
		if(!file.isActive()) {
			throw new FileNotFoundException(messageSource.getMessage("file.not.found.message", null, locale));
		}
	}
	
}
