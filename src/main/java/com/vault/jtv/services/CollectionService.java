package com.vault.jtv.services;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.vault.jtv.beans.GlobalResponse;
import com.vault.jtv.model.ArchiveQuestionnaire;
import com.vault.jtv.model.Collection;
import com.vault.jtv.model.CollectionItems;
import com.vault.jtv.model.UserAccount;

public interface CollectionService {

	public GlobalResponse getAllCollection();

	public List<ArchiveQuestionnaire> getQuestionnaireList(String name);

	public List<Collection> findByUserId(int userId);

	public Collection save(Collection collection);

	public Collection getCollectionById(int collectionId);

	public Collection getCollectionByName(String collectionName);

	public int updateAsInactive(List<Integer> idList, String inactive);

	public Map<String, Object> getuserCollectionSummary(UserAccount user, String token);

	public int addItemsToCollection(String collectionId, String itemsId, int userId);

	public List<CollectionItems> getCustomCollectionById(int collectionId);

	public Object createCollection(Collection col, MultipartFile file, int userId);

	public Object editCollection(Collection col, MultipartFile file, int userId, String itemsId, String publicId);

	public Object deleteCollection(int collectionId);

	public Object addCollectionsItem(String collectionId, int itemId, int userId);

}