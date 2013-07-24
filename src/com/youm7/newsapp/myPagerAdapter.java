package com.youm7.newsapp;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youm7.newsapp.NewsLoader.TaskCompletedListener;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class myPagerAdapter extends PagerAdapter implements TaskCompletedListener {
int mViewCount;
LayoutInflater mViewPagerInflater;
NewsLoader mloadTopStory;
ArrayList<NewsItem> mTopNews;
Context context;
static DisplayImageOptions dispoptions= new DisplayImageOptions.Builder()
.cacheInMemory(true)
.cacheOnDisc(true)
.build(); 
static String topurl="http://mobrss.youm7.com/rss/Service.svc/NewsTopStories";
	ImageLoader mUniversalimageloader;
	public myPagerAdapter(int ViewCount,Context context,ImageLoader universalimageloader) {
		// TODO Auto-generated constructor stub
		mViewCount=ViewCount;
		mViewPagerInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
		mTopNews=new ArrayList<NewsItem>();
		mloadTopStory= new NewsLoader();
		mloadTopStory.loadSection(topurl, this,mViewCount);
		mUniversalimageloader=universalimageloader;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mTopNews!=null)
		return mTopNews.size();
		return 0;
	}
    
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
		
	}
	
	@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView((View) object);
		}
	@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
		RelativeLayout temp =(RelativeLayout) mViewPagerInflater.inflate(R.layout.top_story_layout,null);
		temp.setTag(position);
		 ((ViewPager) container).addView(temp);
	     ((TextView)  temp.findViewById(R.id.toptextview)).setText(mTopNews.get(position).NewsTitle);
		mUniversalimageloader.displayImage(mTopNews.get(position).NewsImgLink, (ImageView) temp.findViewById(R.id.topstoryimgview), dispoptions);  
	  
	  
	    
		return temp;
	     
	     
	
	}

	@Override
	public void OnTaskCompleted(ArrayList<NewsItem> result) {
		// TODO Auto-generated method stub
		mTopNews=result;
		notifyDataSetChanged();
		
	}


}
