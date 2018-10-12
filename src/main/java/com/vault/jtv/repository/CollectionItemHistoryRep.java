package com.vault.jtv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vault.jtv.model.CollectionItemHistory;

@RepositoryRestResource(exported = false)
public interface CollectionItemHistoryRep extends JpaRepository<CollectionItemHistory, Integer>{	
	
}