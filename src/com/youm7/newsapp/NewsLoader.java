package com.youm7.newsapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import com.youm7.newsapp.Home.DummySectionFragment;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

public  class NewsLoader {
	DummySectionFragment frag;
	private static boolean initialized=false;
	private static ArrayList<downloadtask> currentTasks=new ArrayList<NewsLoader.downloadtask>();
	public  void  loadSection(String url, DummySectionFragment calingFrag)  
	{
		currentTasks.add(new downloadtask());
		currentTasks.get(currentTasks.size()-1).execute(url);
		frag=calingFrag;
	}
	
	
private class downloadtask extends AsyncTask<String, Void, ArrayList<NewsItem>>
{


	@Override
	protected ArrayList<NewsItem> doInBackground(String... urls) {
		// TODO Auto-generated method stub
	try {
		return	new XmlParser(urls[0]).getnews();
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
		
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onCancelled(ArrayList<NewsItem> result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
	}

	@Override
	protected void onPostExecute(ArrayList<NewsItem> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		frag.OndownloadFinished(result);
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}


}
}
