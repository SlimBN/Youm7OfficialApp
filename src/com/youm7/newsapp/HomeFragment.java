package com.youm7.newsapp;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class HomeFragment extends Fragment {

	ArrayList<NewsItem> mtestnews;
	RelativeLayout mNewsSectionsHolder;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	SmartViewPager mViewPager;
	ImageLoaderConfiguration mLoadconfig ;
    ArrayList<NewsItem> collectedsections;
    myPagerAdapter TopstoryAdapter;
    NewsLoaderMixed mixednewsloader;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_home_dummy,container, false);
	}


	public HomeFragment() {
		
	
	}
	@Override
	public void onStart() {
		
		// TODO Auto-generated method stub
		 View v = getView();
		mViewPager= (SmartViewPager) getView().findViewById(R.id.TopStoryPager);
		mViewPager.setAdapter(TopstoryAdapter);
	    mixednewsloader=new NewsLoaderMixed(getView().findViewById(R.id.sections_holder), getActivity().getApplicationContext(), mloadImage);
		
	    super.onStart();
		 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mtestnews= new ArrayList<NewsItem>();
	      collectedsections=new ArrayList<NewsItem>();
	       
	        dispoptions= new DisplayImageOptions
	        		.Builder()
	        .cacheInMemory(true)
	        .cacheOnDisc(true)
	        .build();      
	        mLoadconfig= new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).build();
		     mloadImage=ImageLoader.getInstance();
		        mloadImage.init(mLoadconfig);
	        TopstoryAdapter=new myPagerAdapter(4,getActivity().getApplicationContext(),mloadImage);
	       
	}

}
