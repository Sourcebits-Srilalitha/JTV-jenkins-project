package com.vault.jtv.security;

import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vault.util.MessageConstants;

@Service
public class ImageHelper {

	private Pattern pattern;	
	private Pattern docPattern;

	

	public ImageHelper() {
		pattern = Pattern.compile(MessageConstants.IMAGE_PATTERN);
		docPattern = Pattern.compile(MessageConstants.DOC_PATTERN);
	}

	public String randomImageName() {
		StringBuffer buffer = new StringBuffer();
		while (buffer.length() < 8) {
			buffer.append(uuidString());
		}
		// this part controls the length of the returned string
		return buffer.substring(0, 8);
	}

	private static String uuidString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * To validate image size and image type	 * 
	 * Checking with the IMAGE_PATTERN
	 * @param image
	 * @return 
	 */

	public String validateImage(final MultipartFile image) {
		if (image.getSize() > 20000000 || image.getSize() <= 0) {
			return MessageConstants.INVALID_IMG_SIZE;
		}
		if (pattern.matcher(image.getOriginalFilename().toLowerCase()).matches() == false) {
			return MessageConstants.INVALID_IMG_TYPE;
		}
		return MessageConstants.SUCCESS;
	}

	/**
	 * To validate doc size and doc type	 * 
	 * Checking with the DOC_PATTERN
	 * @param doc
	 * @return
	 */
	public String validateDocument(final MultipartFile doc) {
		if (doc.getSize() > 20000000 || doc.getSize() <= 0) {
			return MessageConstants.INVALID_DOC_SIZE;
		}
		if (docPattern.matcher(doc.getOriginalFilename().toLowerCase()).matches() == false) {
			return MessageConstants.INVALID_DOC_TYPE;
		}
		return MessageConstants.SUCCESS;
	}

}

