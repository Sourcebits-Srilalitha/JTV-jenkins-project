package com.vault.jtv.controllers;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vault.jtv.beans.SearchBean;
import com.vault.jtv.services.SearchService;
import com.vault.util.Constants;
import com.vault.util.MessageConstants;

@RequestMapping(value = Constants.REQMAP_API)
@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;


	private static Logger logger = LoggerFactory.getLogger(SearchController.class);

	@RequestMapping(value = Constants.REQMAP_SEARCH, method = RequestMethod.POST)
	public ResponseEntity<Object> globalSearch(@RequestBody SearchBean searchBean, HttpServletRequest request)
			throws Exception {
		logger.info(MessageConstants.GLOBAL_SEARCH);
		Map<String, Object> finalMap = null;
		if (null != searchBean && null != searchBean.getSearchKey() && searchBean.getSearchKey().trim().length() > 0) {
			finalMap = searchService.globalSearchKey(searchBean);
			if (null != finalMap) {
				return ResponseEntity.ok(finalMap);
			} else {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "-" + MessageConstants.INTERNAL_SERVER_ERROR,
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST.value() + "-" + MessageConstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
		}
	}
}
