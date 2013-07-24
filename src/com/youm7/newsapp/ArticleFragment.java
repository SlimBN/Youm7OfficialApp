package com.youm7.newsapp;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.youm7.newsapp.NewsLoader.TaskCompletedListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArticleFragment extends Fragment implements TaskCompletedListener {

	NewsItem Article;
	
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	 String articleapi;
	ImageLoaderConfiguration mLoadconfig ;
    NewsLoader loadArticleBody;
   
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.article_layout,container, false);
	}


	public ArticleFragment() {
		
	
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		 super.onStart();
		 loadArticleBody.loadSection(articleapi+Article.NewsId, this, 1);
	    mloadImage.displayImage(Article.NewsImgLink, (ImageView) getView().findViewById(R.id.imageView1));
		((TextView) getView().findViewById(R.id.youmTextView1)).setText(Article.NewsTitle);
		 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    	Article=new NewsItem();
	       articleapi= getResources().getString(R.string.ArticleAPI);
	       Article.NewsTitle=getArguments().getString("newstitle");
	       Article.NewsImgLink=      getArguments().getString("newsimagelink");
	       Article.NewsId=getArguments().getString("newsid");
	        dispoptions= new DisplayImageOptions
	        		.Builder()
	        .cacheInMemory(true)
	        .cacheOnDisc(true)
	        .build();      
	        mLoadconfig= new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).build();
		     mloadImage=ImageLoader.getInstance();
		     mloadImage.init(mLoadconfig);
	        loadArticleBody=new NewsLoader();
	       
	}


	@Override
	public void OnTaskCompleted(ArrayList<NewsItem> result) {
		// TODO Auto-generated method stub
		Article=result.get(0);
	     ((TextView)	getView().findViewById(R.id.youmTextView2)).setText(Article.NewsContent);
		
	}

}
