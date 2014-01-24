package com.youm7.news;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Collections;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.viewpagerindicator.IconPagerAdapter;
import com.youm7.news.NewsLoader.TaskCompletedListener;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class myPagerAdapter extends PagerAdapter implements TaskCompletedListener,OnClickListener,OnTouchListener,OnLongClickListener,IconPagerAdapter {
int mViewCount;
LayoutInflater mViewPagerInflater;
NewsLoader mloadTopStory;

ArrayList<NewsItem> mTopNews;
Context context;
static DisplayImageOptions dispoptions= new DisplayImageOptions.Builder()
.showStubImage(R.drawable.loader_top)
.cacheInMemory(true)
.cacheOnDisc(true)
.build(); 
OnHomeArticleSelected TopStoryListener;
static String topurl;
	ImageLoader mUniversalimageloader;
	public myPagerAdapter(int ViewCount,Context context,ImageLoader universalimageloader,OnHomeArticleSelected topstory) {
		// TODO Auto-generated constructor stub
		mViewCount=ViewCount;
		mViewPagerInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
		mTopNews=new ArrayList<NewsItem>();
		topurl=context.getString(R.string.TopService);
		mUniversalimageloader=universalimageloader;
		TopStoryListener=topstory;
		UpdateTopStory();
	}

	public void UpdateTopStory(){
		mloadTopStory= new NewsLoader();
		mloadTopStory.loadSection(topurl, this,mViewCount);
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
		 temp.findViewById(R.id.topstoryimgview).setTag(mTopNews.get(position));
	     temp.setOnClickListener(this);
	     temp.setOnTouchListener(this);
	     temp.setOnLongClickListener(this);
	     temp.setTag(mTopNews.get(position));
	     Log.e("topheight", Integer.toString(temp.findViewById(R.id.topstoryimgview).getHeight())); 
		 return temp;
	     
	     
	
	}
	
@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}
   public NewsItem getItem(int position)
   {
	   return mTopNews.get(mTopNews.size()-1-position);
   }
	@Override
	public void OnTaskCompleted(ArrayList<NewsItem> result, int taskID) {
		// TODO Auto-generated method stub
		if(result==null || result.size()==0)
			UpdateTopStory();
		else{
		mTopNews=result;
		
		Collections.reverse(mTopNews);
		notifyDataSetChanged();
		TopStoryListener.RefreshFinished();
		
		}
	}


	@Override
	public void onClick(View v) {
	    
		TopStoryListener.HomeSelected((NewsItem) v.getTag());
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
	
		 /*if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL)
		v.setAlpha(1);*/
		return false;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		v.setAlpha(0.7f);
		return false;
	}

	@Override
	public int getIconResId(int index) {
		
	return	R.drawable.indicator_icons;
	
	}


}
