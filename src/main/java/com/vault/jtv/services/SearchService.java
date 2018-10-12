package com.vault.jtv.services;

import java.util.Map;

import com.vault.jtv.beans.SearchBean;

public interface SearchService {
	
	Map<String, Object> globalSearchKey(SearchBean searchBean);

}