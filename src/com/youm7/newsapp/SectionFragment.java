package com.youm7.newsapp;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SectionFragment extends Fragment implements OnItemClickListener{
	
	ListView mNewsScroll;
	ArrayList<NewsItem> sectionNewsList;
	ImageLoader mImageloader;
	String mSecTitle;
	String mSecID;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	SectionNewsAdapter newsAdapter;
	ImageLoaderConfiguration mLoadconfig ;
	OnArticleSelectedListener CallActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.news_category_layout,
				container, false);
		
	
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mSecTitle=getArguments().getString("SecTitle");
		mSecID=getArguments().getString("SecID");
		  dispoptions= new DisplayImageOptions
	        		.Builder()
	        .cacheInMemory(true)
	        .cacheOnDisc(true)
	        .build();      
	        mLoadconfig= new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).build();
		     mloadImage=ImageLoader.getInstance();
		        mloadImage.init(mLoadconfig);
		       newsAdapter=new SectionNewsAdapter(sectionNewsList, getActivity().getApplicationContext(), 2, mloadImage,ConstructURL(mSecID));
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		mNewsScroll=(ListView) getView().findViewById(R.id.news_listview);
		mNewsScroll.setAdapter(newsAdapter);
	    mNewsScroll.setOnItemClickListener(this);
		super.onStart();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ArticleFragment frag=new ArticleFragment();
		NewsItem ArticleToOpen=(NewsItem) newsAdapter.getItem(arg2);
		Bundle details=new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		frag.setArguments(details);
		
		CallActivity.OnArticleSelected(frag,this,true);
		
	}
public interface OnArticleSelectedListener{
	public void OnArticleSelected(Fragment frag, Fragment LeavingFrsg, boolean AddToBS);
}
@Override
public void onAttach(Activity activity) {
	// TODO Auto-generated method stub
	super.onAttach(activity);
	CallActivity=(OnArticleSelectedListener) activity;
}
private String ConstructURL(String SecID)
{
	String template=  getActivity().getApplicationContext().getResources().getString(R.string.sectionapi);
	return template.replace("{SecID}", SecID);
	
	
}
}
