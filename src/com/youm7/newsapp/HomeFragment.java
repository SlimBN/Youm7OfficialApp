package com.youm7.newsapp;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.youm7.newsapp.SectionFragment.OnArticleSelectedListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class HomeFragment extends Fragment implements OnHomeArticleSelected ,OnClickListener{

	
	RelativeLayout mNewsSectionsHolder;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	SmartViewPager mViewPager;
	ImageLoaderConfiguration mLoadconfig ;
    ArrayList<NewsItem> collectedsections;
    myPagerAdapter TopstoryAdapter;
    NewsLoaderMixed mixednewsloader;
    OnArticleSelectedListener CallActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v =  inflater.inflate(R.layout.fragment_home_dummy,container, false);
		mViewPager= (SmartViewPager) v.findViewById(R.id.TopStoryPager);
		mViewPager.setAdapter(TopstoryAdapter);
		mixednewsloader=new NewsLoaderMixed(v.findViewById(R.id.sections_holder), getActivity().getApplicationContext(), mloadImage,this);
			return v;
	}


	public HomeFragment() {
		
	
	}
	@Override
	public void onStart() {
		
		// TODO Auto-generated method stub
		
	  
		
	    super.onStart();
		 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	    collectedsections=new ArrayList<NewsItem>();
	       
	    dispoptions= new DisplayImageOptions
	        		.Builder()
	        .cacheInMemory(true)
	        .cacheOnDisc(true)
	        .build();
	    mLoadconfig= new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).build();
		mloadImage=ImageLoader.getInstance();
		mloadImage.init(mLoadconfig);
	    TopstoryAdapter=new myPagerAdapter(4,getActivity().getApplicationContext(),mloadImage,this);
	       
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		CallActivity=(OnArticleSelectedListener) activity;
	}


	@Override
	public void HoMeSelected(NewsItem item) {
		// TODO Auto-generated method stub
		ArticleFragment frag=new ArticleFragment();
		NewsItem ArticleToOpen=(NewsItem) item;
		Bundle details=new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		frag.setArguments(details);
		
		CallActivity.OnArticleSelected(frag,this,true);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ArticleFragment frag=new ArticleFragment();
		NewsItem ArticleToOpen=(NewsItem)TopstoryAdapter.getItem( mViewPager.getCurrentItem());
		Bundle details=new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		frag.setArguments(details);
		Log.d("home clicked ", "true");
		CallActivity.OnArticleSelected(frag,this,true);
	}
	
	

}
