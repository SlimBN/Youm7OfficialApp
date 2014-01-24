package com.youm7.news;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class XmlParser {

	org.xmlpull.v1.XmlPullParser newsparser;
    URL SecApiLink;
    int eventtype;
    ArrayList<NewsItem> newslist;
   int mElementsCount;
    private static XmlPullParserFactory mParserGenerator;
	public XmlParser(String URL) throws XmlPullParserException, MalformedURLException {
		super();
		SecApiLink= new URL(URL);
		mParserGenerator=XmlPullParserFactory.newInstance();
		newsparser = mParserGenerator.newPullParser();
		newsparser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	    newslist=new ArrayList<NewsItem>();
	}
	public ArrayList<NewsItem> getnews (int elementscount) throws XmlPullParserException, IOException
	{
		mElementsCount=elementscount;
		init_Connection();
		Parse();
		return newslist;
		
	}
	private void init_Connection() throws XmlPullParserException, IOException{
		
		newsparser.setInput(SecApiLink.openStream(),"utf-8");
	}
	private void Parse() throws XmlPullParserException, IOException
	{
		eventtype=newsparser.getEventType();
	    String tagName;
	    NewsItem temp= new NewsItem();
		while (eventtype != XmlPullParser.END_DOCUMENT&&newslist.size()<mElementsCount){
			
			tagName=newsparser.getName();
			switch(eventtype){
			
			case XmlPullParser.START_TAG:
			
			if(tagName.equalsIgnoreCase("item"))
			{
				temp=new NewsItem();
				
			}
			
			else if(tagName.equalsIgnoreCase("title"))
			{
				if(newsparser.getDepth()<4)
				temp.NewsTitle=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("id"))
			{
				if(newsparser.getDepth()<4)
				temp.NewsId=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("date"))
			{
				temp.NewsDateString=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("mainimage"))
			{
				temp.NewsImgLink=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("link"))
			{
				temp.NewsLink=newsparser.nextText();
			
			}
			else if(tagName.equalsIgnoreCase("abstract"))
			{
				temp.NewsAbstract=newsparser.nextText();
				
			}
			else if(tagName.equalsIgnoreCase("body"))
			{
				temp.NewsContent=newsparser.nextText();
				temp.NewsContent.trim();
				
			}
			else if(tagName.equalsIgnoreCase("image"))
			{
				temp.smallImageLink=newsparser.nextText();
				
			}
			
			else if(tagName.equalsIgnoreCase("byline"))
			{
				temp.newsWriter=newsparser.nextText();
				
			}
			else if(tagName.equalsIgnoreCase("images"))
			{
				if(!newsparser.isEmptyElementTag())
				{
					temp.Has_Images=true;
					eventtype=newsparser.next();
					tagName=newsparser.getName();
					while (!tagName.equalsIgnoreCase("images"))
					{
						
						if(eventtype==XmlPullParser.START_TAG)
							temp.imgLinks.add(newsparser.nextText());
						eventtype=newsparser.next();
						tagName=newsparser.getName();
						}
					}
			}			
			else if(tagName.equalsIgnoreCase("videos"))
			{
				if(!newsparser.isEmptyElementTag())
				{
				temp.Has_videos=true;
			
				eventtype=newsparser.next();
				tagName=newsparser.getName();
				while (!tagName.equalsIgnoreCase("videos"))
				{
					if(eventtype==XmlPullParser.START_TAG)
						temp.videoLinks.add(newsparser.nextText());
					eventtype=newsparser.next();
					tagName=newsparser.getName();
				}
			}
				
			}
			else if(tagName.equalsIgnoreCase("numberofcomments"))
			{
				temp.numberofcomments=newsparser.nextText();
			}
			break;
			case XmlPullParser.END_TAG:
				 if(tagName.equalsIgnoreCase("item"))
					{
						newslist.add(temp);
				}
				 break;
			}
		
	       eventtype=newsparser.next();
	
		}
		
	}
}
