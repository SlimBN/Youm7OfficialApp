package com.youm7.newsapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SectionFragment extends Fragment {
	
	ListView mNewsScroll;
	ArrayList<NewsItem> sectionNewsList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.news_category_layout,
				container, false);
		
	
		return rootView;
	}

}
