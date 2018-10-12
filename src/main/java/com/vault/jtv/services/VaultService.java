package com.vault.jtv.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.vault.jtv.beans.ArchiveBean;
import com.vault.jtv.model.Collection;
import com.vault.jtv.model.CollectionItems;
import com.vault.jtv.model.GemopediaItem;
import com.vault.jtv.model.UserAccount;



public interface VaultService {
	public Set<Collection> getVaultByUserId(int userId) throws Exception;
	public List<CollectionItems> getOrderHistoryFromJTV(UserAccount user ,String token);
	public GemopediaItem getGemFromVaultItem(Integer itemId);		
	public List<Object> getArchivedItems (int userId);	
	public int restoreFromVault(int userId, String itemIDs);	
	
	
	public String deleteFromVault(int userId, Integer ItemId,Integer reasonId);

	public Object getItemDetails(int itemId);


	public Set<CollectionItems> getVaultItemsByUserId(int userId);

	public List<CollectionItems> getRecentlyAddedItemsByUserId(int userId);
	public String deleteFromVaultItems(int userId, Map<String, String> archivedItems);
	
	public CollectionItems addItems(CollectionItems collectionItems, int userId);
	
	public Object uploadImage(int itemId, List<MultipartFile> itemList, List<MultipartFile> appDocList,	List<MultipartFile> appPhotoList, int userId);

	
	
	public Object editItems(CollectionItems collectionItems, int userId);
	public String archiveFromVaultItems(int userId, List<ArchiveBean> archivedItems);
}