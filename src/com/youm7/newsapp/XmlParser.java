package com.youm7.newsapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlParser {

	org.xmlpull.v1.XmlPullParser newsparser;
    URL SecApiLink;
    int eventtype;
    ArrayList<NewsItem> newslist;
    private static XmlPullParserFactory mParserGenerator;
	public XmlParser(String URL) throws XmlPullParserException, MalformedURLException {
		super();
		SecApiLink= new URL(URL);
		mParserGenerator=XmlPullParserFactory.newInstance();
		newsparser = mParserGenerator.newPullParser();
		newsparser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	    newslist=new ArrayList<NewsItem>();
	}
	public ArrayList<NewsItem> getnews() throws XmlPullParserException, IOException
	{
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
	    int position=0;
		while (eventtype != XmlPullParser.END_DOCUMENT){
			switch(eventtype){
			
			case XmlPullParser.START_TAG:
				String tagName=newsparser.getName();
			if(tagName.equalsIgnoreCase("item"))
			{
				newslist.add(new NewsItem());
			}
			else if(tagName.equalsIgnoreCase("title"))
			{
				newslist.get(position).NewsTitle=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("id"))
			{
				newslist.get(position).NewsId=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("pubdate"))
			{
				newslist.get(position).NewsDateString=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("mainimage"))
			{
				newslist.get(position).NewsImgLink=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("abstract"))
			{
				newslist.get(position).NewsAbstract=newsparser.nextText();
				position++;
			}
			break;
			}
	eventtype=newsparser.next();
	
		}
	}
}
