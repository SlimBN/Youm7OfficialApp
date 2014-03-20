package com.youm7.news;


import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youm7.news.NewsLoader.TaskCompletedListener;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import android.widget.ImageView;


public class mogazPagerAdapter extends PagerAdapter implements TaskCompletedListener,OnClickListener,OnTouchListener,OnLongClickListener {
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
LayoutInflater mogazInflater;
	ImageLoader mUniversalimageloader;
	public mogazPagerAdapter(int ViewCount,Context context,ImageLoader universalimageloader,OnHomeArticleSelected topstory) {
		// TODO Auto-generated constructor stub
		mViewCount=ViewCount;
		mViewPagerInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
		mTopNews=new ArrayList<NewsItem>();
		topurl= context.getString(R.string.mogazservice);
		mUniversalimageloader=universalimageloader;
		TopStoryListener=topstory;
		mogazInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		UpdateTopStory();
	}

	public void UpdateTopStory(){
		mloadTopStory= new NewsLoader();
		mloadTopStory.loadSection(topurl, this,mViewCount);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if( mTopNews.size()!=0)
		return 200;
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
		
		ImageView temp =(ImageView) mogazInflater.inflate(R.layout.mogaz_image, null);
	
		  
	    
		 mUniversalimageloader.displayImage(mTopNews.get(position%mTopNews.size()).NewsImgLink.replace("/large/", "/medium/"),  temp, dispoptions);  
		 temp.setTag(mTopNews.get(position%mTopNews.size()));
	     temp.setOnClickListener(this);
	   //  temp.setOnTouchListener(this);
	  //   temp.setOnLongClickListener(this);
	  //   temp.setTag(mTopNews.get(position));*/
		 container.addView(temp);
		 return temp;
	     
	     
	
	}
@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}
   public NewsItem getItem(int position)
   {
	   try{
	   return mTopNews.get(position%mTopNews.size());
	   }
	   catch(ArithmeticException e)
	   {
		   e.printStackTrace();
		   return null;
	   }
   }
	@Override
	public void OnTaskCompleted(ArrayList<NewsItem> result, int taskID) {
		// TODO Auto-generated method stub
	if(result==null || result.size()==0)
		UpdateTopStory();
	else{
		mTopNews=result;
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
		
	
		 if(event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL)
		v.setAlpha(1);
		return false;
	}

	@Override
	public boolean onLongClick(View v) {
		
		
		
		
		v.setAlpha(0.7f);
		return false;
	}


}
