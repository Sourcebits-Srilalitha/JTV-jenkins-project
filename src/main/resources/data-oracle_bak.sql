INSERT INTO video_types(title ,active ,CREATED_ON ,MODIFIED_ON) VALUES('Gemstone Spotlight','Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO video_types(title ,active ,CREATED_ON ,MODIFIED_ON) VALUES('Mining' ,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO video_types(title ,active ,CREATED_ON ,MODIFIED_ON) VALUES('Pearls', 'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO video_types(title ,active ,CREATED_ON ,MODIFIED_ON) VALUES('Just Gems','Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (1,'Emerald Gemstone Spotlight','The king of gems, emerald, is a beauty known for its inner glow, unmistakable green and historical importance','https://youtu.be/Tu4JF_k23Yo','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (1,'Ruby Gemstone Spotlight','The sole birthstone for July, ruby is the brightest and boldest of all birthstones. Called ratnaraj, meaning the King of Gems by ancient Hindus, ruby''s association with the blood of life has earned it powerful praise and high esteem since antiquity','https://youtu.be/ihFY_3gpki4','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (1,'Sunstone Gemstone Spotlight','Sunstone is fairly new to the United States market causing it''s unique and amazing history to often be overlooked. Learn more about this truly rare and remarkable gem today!','https://youtu.be/SmL3XTpVYgI','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (1,'Tsavorite Gemstone Spotlight','Natural and untreated, tsavorite garnet green is unlike any other green you have ever seen. Tsavorite is only available at a limited number of locales in the entire world','https://youtu.be/p8oYKuj7l-E','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (1,'Sapphire Gemstone Spotlight','Once proposed to be the national gemstone of the US, tourmaline is found all over the world. Tourmaline is the most colorful of all gemstones because, it passed through a rainbow on its journey to Earth and brought all the colors of the rainbow with it','https://youtu.be/lZ1pDLwoELY','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (1,'Opal Gemstone Spotlight','Opal is known for its majestic plays of color and unique mining locales. Learn more about opal and find the one that catches your eye.','https://youtu.be/ojuIuP3tS80','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (2,'Tourmaline Mining in Brazil','JTV travels to Brazil on the hunt for tourmaline!','https://youtu.be/HtUQKMn-0VM','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (2,'Going Down a Tanzanite Mine','JTV''s Director of Photography, Ryan Acree takes you 70 meters down into the mines of Tanzania in search of tanzanite','https://youtu.be/Yd76w7Ult7g','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (3,'What Are Baroque Pearls?','Jewelry Television Pearl expert, Mark Brown, discusses Baroque Pearls','https://youtu.be/MvDdmGQKFfs','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into videos(video_type_id,title,description,link,active,CREATED_ON ,MODIFIED_ON) values (4,'History of Birthstones','Join Gem Expert and Rockhound, Renata as she shares the lengthy and interesting history of birthstones. Hint: they may have shown up in the Bible','https://youtu.be/XjbMPvTm_yI','Y',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
	

INSERT INTO article_types(title ,active ,CREATED_ON ,MODIFIED_ON) VALUES('Birthstones','Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO article_types(title ,active ,CREATED_ON ,MODIFIED_ON) VALUES('Gemstone Collecting','Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO article_types(title ,active ,CREATED_ON ,MODIFIED_ON) VALUES('Gemstone Education','Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO article_types(title ,active ,CREATED_ON ,MODIFIED_ON) VALUES('Gemstone Setting','Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

/*
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Amethyst: February''s Birthstone',1,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'ID1')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Aquamarine: March''s Birthstone',1,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'ID2')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Citrine: November''s Birthstone',1,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'ID4')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Emerald: May''s Birthstone',1,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'ID6')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Garnet Varieties: January''s Birthstone',1,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'ID9')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Peridot: August''s Birthstone',1,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'ID5')

INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Ruby: July''s Birthstone',1,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID3') 
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Gemstones: The Original Collectible',2,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID7')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Gemstone Collecting Origins',2,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID8')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Gemstone Identification: How to Identify Gemstones',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID10')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('The Exciting World of Fine Mineral Specimens',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID11')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Assembled Gems',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID12')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Gemstone Beauty: The Science Behind the Sparkle and Shine',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID13')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Gemstone Origins',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID14')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Gemstone Shape vs. Gemstone Cut',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID15')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Gemstone Characteristics: Gemstone Weight vs, Gemstone Size',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID16')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Natural or Artificial?',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID17')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Optical Properties',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID18')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Phenomenal Gems',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID19')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Rare Gemstone Collecting for Everyone: Good, Better, Best',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID20')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Why We See Color',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID21')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('Diamond Grading: Understanding the Four C''s',3,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID22')
INSERT INTO articles(title ,series,active ,CREATED_ON ,MODIFIED_ON,content_id) VALUES('How-To: Set Gemstones in Jewelry Settings',4,'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ID23')


INSERT INTO article_Images(imageurl ,is_hero_image,article_id) VALUES('http://demandware.edgesuite.net/aaby_prd/on/demandware.static/-/Sites-jtv-Library/default/dw140e1a87/page%20images/Learning%20Library/Article_Index/February_Birthstone/%20article_amethyst_parcel_md1.jpg','y',1)
INSERT INTO article_Images(imageurl ,is_hero_image,article_id) VALUES('http://demandware.edgesuite.net/aaby_prd/on/demandware.static/-/Sites-jtv-Library/default/dw4bec7b9d/page%20images/Learning%20Library/Article_Index/march_birthstone/article_%20aquamarine_lg1.jpg','y',2)
INSERT INTO article_Images(imageurl ,is_hero_image,article_id) VALUES('http://demandware.edgesuite.net/aaby_prd/on/demandware.static/-/Sites-jtv-Library/default/dw71db8512/page%20images/Learning%20Library/Article_Index/November_Birthstone/article_citrine_md1.jpg','y',3)
INSERT INTO article_Images(imageurl ,is_hero_image,article_id) VALUES('http://demandware.edgesuite.net/aaby_prd/on/demandware.static/-/Sites-jtv-Library/default/dw27e9dde7/page%20images/Learning%20Library/Article_Index/May_Birthstone/article_emeraldheart_md1.jpg','y',4)
INSERT INTO article_Images(imageurl ,is_hero_image,article_id) VALUES('http://demandware.edgesuite.net/aaby_prd/on/demandware.static/-/Sites-jtv-Library/default/dw9a32a4a6/page%20images/Learning%20Library/Article_Index/january_birthstone/article_garnet6_md1.jpg','y',5)
INSERT INTO article_Images(imageurl ,is_hero_image,article_id) VALUES('http://demandware.edgesuite.net/aaby_prd/on/demandware.static/-/Sites-jtv-Library/default/dw767d00a5/page%20images/Learning%20Library/Article_Index/August_Birthstone/article_peridot_lg1.jpg','y',6)
*/

INSERT INTO users(username,first_name,last_name,enterprise_user_id,customer_id,email,CREATED_ON,MODIFIED_ON) VALUES('test','firsttest','lasttest',123456,201,'test@test.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO users(username,first_name,last_name,enterprise_user_id,customer_id,email,CREATED_ON,MODIFIED_ON) VALUES('abc','firsttest','lasttest',111,202,'test@test.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO users(username,first_name,last_name,enterprise_user_id,customer_id,email,CREATED_ON,MODIFIED_ON) VALUES('User2','User','Two',102,203,'user@two.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO users(username,first_name,last_name,enterprise_user_id,customer_id,email,CREATED_ON,MODIFIED_ON) VALUES('User3','User','Three',103,204,'user@three.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


/*CollectionItem*/
/*INSERT INTO collection_items(item_name,description,collection_id,variety,species,sku,date_of_purchase,purchase_price,brand,color,cut,shape,size,dimensions,country_of_origin,composition,treatment,phenomenon,visible,deleted) VALUES ('Item_1', 'pink stone', 3, 'AMETHYST','Quartz','hjue678j80',CURRENT_TIMESTAMP,200.00,'Moissanite Fire','White','Princess','Square Cushion','1.50','9x7 Mm','United States','natural','no treatment','none', 'Y', 'N')
INSERT INTO collection_items(item_name,description,collection_id,variety,species,sku,date_of_purchase,purchase_price,brand,color,cut,shape,size,dimensions,country_of_origin,composition,treatment,phenomenon,visible,deleted) VALUES ('Item_2', 'Metal Stone', 3,'Moissanite','Synthetic','1474923',CURRENT_TIMESTAMP,399.99,'Moissanite Fire','White','Brilliant','Square Cushion','1.49','7 Mm','United States','Synthetic','no treatment','none', 'Y', 'N')
INSERT INTO collection_items(item_name,description,collection_id,variety,species,sku,date_of_purchase,purchase_price,brand,color,cut,shape,size,dimensions,country_of_origin,composition,treatment,phenomenon,visible,deleted) VALUES ('Item_1', 'pink stone', 4, 'AMETHYST','Quartz','hjue678j80',CURRENT_TIMESTAMP,200.00,'Moissanite Fire','White','Princess','Square Cushion','1.50','9x7 Mm','United States','natural','no treatment','none', 'Y', 'N')
INSERT INTO collection_items(item_name,description,collection_id,variety,species,sku,date_of_purchase,purchase_price,brand,color,cut,shape,size,dimensions,country_of_origin,composition,treatment,phenomenon,visible,deleted) VALUES ('Item_2', 'Metal Stone', 4,'Moissanite','Synthetic','1474923',CURRENT_TIMESTAMP,399.99,'Moissanite Fire','White','Brilliant','Square Cushion','1.49','7 Mm','United States','Synthetic','no treatment','none', 'Y', 'N')
*/