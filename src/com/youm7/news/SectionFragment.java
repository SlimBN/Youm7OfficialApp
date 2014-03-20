package com.youm7.news;

import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SectionFragment extends Fragment implements OnItemClickListener,OnHomeArticleSelected{
	
	PullToRefreshListView mNewsScroll;
	ArrayList<NewsItem> sectionNewsList;
	TextView loadmore;
	ImageLoader mImageloader;
	String mSecTitle;
	String mSecID;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	SectionNewsAdapter newsAdapter;
	ImageLoaderConfiguration mLoadconfig ;
	OnArticleSelectedListener CallActivity;
	ProgressBar secPB;
	View footer;
	String page;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.news_category_layout,
				container, false);
	    footer= inflater.inflate(R.layout.sec_footer_view	, null);
	  
	    loadmore= (TextView) footer.findViewById(R.id.loadmore);
	    AdView adView = (AdView)rootView.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
		mNewsScroll=(PullToRefreshListView) rootView.findViewById(R.id.news_listview);
		mNewsScroll.getRefreshableView().addFooterView(footer,null,true);
		
		mNewsScroll.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.Refreshing_label));
		mNewsScroll.getLoadingLayoutProxy().setPullLabel(getString(R.string.pull_label));
		mNewsScroll.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.release_label));
		mNewsScroll.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					newsAdapter.UpdateSection();
					
				}
			});
		mNewsScroll.setAdapter(newsAdapter);
	    mNewsScroll.setOnItemClickListener(this);
	 
	      ((TextView) rootView.findViewById(R.id.News_category_title)).setText(mSecTitle);
	       secPB=(ProgressBar) rootView.findViewById(R.id.loadmorePB);
	       ((ActionBarActivity)( getActivity())).getSupportActionBar().getCustomView().findViewById(R.id.share_icon).setVisibility(View.GONE);
	       ((ActionBarActivity)( getActivity())).getSupportActionBar().getCustomView().findViewById(R.id.fullsite).setOnClickListener(new OnClickListener() {
	   		
	   		@Override
	   		public void onClick(View v) {
	   			// TODO Auto-generated method stub
	   			Intent i = new Intent(Intent.ACTION_VIEW, 
	   				       Uri.parse("http://www.youm7.com/NewsSection.asp?SecID=" + mSecID));
	   				startActivity(i);
	   			
	   		}
	   	});
	       if (!newsAdapter.is_loading)
	       {
	       secPB.setVisibility(View.GONE);
	       mNewsScroll.getRefreshableView().addFooterView(footer);
	       mNewsScroll.onRefreshComplete();
	      
	       }
	       else{
	    	   secPB.setVisibility(View.VISIBLE);
	    	   loadmore.setText("جارى تحميل الاخبار");
	       }

	       loadmore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newsAdapter.getOldNews();
				secPB.setVisibility(View.VISIBLE);
				
			}
		});
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
		    newsAdapter=new SectionNewsAdapter(sectionNewsList, getActivity().getApplicationContext(), 2, mloadImage,ConstructURL(mSecID), this);
		    newsAdapter.UpdateSection();
		    setRetainInstance(true);
	}
@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		ArticleFragment frag=new ArticleFragment();
		NewsItem ArticleToOpen=(NewsItem) newsAdapter.getItem(arg2-1);
		Bundle details=new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		frag.setArguments(details);
		
		CallActivity.onSecArticleSelected(frag,mSecID,true);
		
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
	return template+= SecID;
	
	
}

@Override
public void HomeSelected(NewsItem item) {
	// TODO Auto-generated method stub
	
}

@Override
public void RefreshFinished() {
	// TODO Auto-generated method stub
	mNewsScroll.onRefreshComplete();
	mNewsScroll.getRefreshableView().addFooterView(footer);
	secPB.setVisibility(View.GONE);
	loadmore.setText("اضغط لتحمبل المزيد من الاخبار");
}



}
