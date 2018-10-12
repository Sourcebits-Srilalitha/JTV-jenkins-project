package com.vault.jtv.schedulers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import com.vault.util.MessageConstants;

@Configuration
public class Schedulers {


	@Autowired
	JtvShowcaseContent jtvShowcaseContent;
	
	@Autowired
	JtvVideoContent jtvVideoContent;	
	
	@Autowired
	JtvGemopediaContent jtvGemopediaContent;
	
	@Autowired
	JtvArticleIndex jtvArticleIndex;
	
	private static Logger logger = LoggerFactory.getLogger(Schedulers.class);

	
	//@Scheduled(cron="${Showcase.Scheduler}")
	public void getShowcaseJson(){			
		try {
			logger.debug(MessageConstants.SHOWCASE_SCHEDULER+new Date());
			jtvShowcaseContent.getShowcaseContent();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	//@Scheduled(cron="${Video.Scheduler}")
	public void getVideosJson(){			
		try {
			logger.debug(MessageConstants.VIDEO_SCHEDULER+new Date());
			jtvVideoContent.getVideoContent();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	
	//@Scheduled(cron="${Gemopedia.Scheduler}")	
	public void getGemopediaJson(){			
		try {
			logger.debug(MessageConstants.GEMOPEDIA_SCHEDULER+new Date());
			jtvGemopediaContent.getGemopediaContent();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
	
	//@Scheduled(cron="${Article.Scheduler}")	
	public void getArticleIndexJson(){
		//read json index file loop over contentid check actions required on content id call the jtvarticlecontent.getArticle(this is the content id from the json file)		
		try {
			logger.debug(MessageConstants.ARTICLE_SCHEDULER+new Date());
			jtvArticleIndex.getArticleIndex();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}
}
