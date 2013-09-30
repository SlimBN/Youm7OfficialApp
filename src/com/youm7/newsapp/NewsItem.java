package com.youm7.newsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsItem implements Serializable
	{
/**
	 * 
	 */

		//news details
		String NewsId;
		String NewsTitle;
		String NewsContent;
		String NewsImgLink;
		String NewsLink;
		String MedImgLink;
		String smallImageLink;
		String newsWriter;
		String numberofcomments;
		List<String> imgLinks;
		List<String> videoLinks;
		Date NewsDate;
		String NewsDateString;
		String NewsAbstract;
		boolean Has_Images;
		boolean Has_videos;
		int img_count;
		int vid_count;
		//app utils
		boolean is_latest;
		boolean is_read;
		boolean cached;
		boolean starred;
		boolean imagedownloaded;
	public NewsItem()
		{
		imgLinks=new ArrayList<String>();
		videoLinks=new ArrayList<String>();
		}
		
	}
