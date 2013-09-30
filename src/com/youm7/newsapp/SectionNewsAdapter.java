package com.youm7.newsapp;

import java.util.ArrayList;








import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.youm7.newsapp.NewsLoader.TaskCompletedListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SectionNewsAdapter extends BaseAdapter implements TaskCompletedListener {

	private   String SectionURL;
	private ArrayList<NewsItem> mNewslist;
	LayoutInflater mAdapterinflater;
	RelativeLayout mNewsBlock;
	static DisplayImageOptions dispoptions= new DisplayImageOptions.Builder()
	.showStubImage(R.drawable.loader_sec)
	.cacheInMemory(true)
	.cacheOnDisc(true)
	.build(); 
	 NewsLoader sectionloader;
		ImageLoader mUniversalimageloader;
	int mViewTypeCount;
    OnHomeArticleSelected RefreshFinishedlistener;
    boolean is_loading;
    int page=0;
	public SectionNewsAdapter( ArrayList<NewsItem> newslist,Context context, int viewTypeCount,ImageLoader Universalimageloader,String sectionurl, OnHomeArticleSelected refreshlistener) {
		
		
		super();
		this.SectionURL=sectionurl;
		mNewslist=newslist;
		mAdapterinflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mUniversalimageloader=Universalimageloader;
        mViewTypeCount=viewTypeCount;
        RefreshFinishedlistener=refreshlistener;
        
	}
	public void UpdateSection(){
		sectionloader= new NewsLoader();
		
        sectionloader.loadSection(SectionURL, this, 100,0);
       
        is_loading=true;
	}
	public void getOldNews(){
        sectionloader= new NewsLoader();
		page=(getCount()/25)+1;
        sectionloader.loadSection(SectionURL+"&Page="+page, this, 100,1);
       
        is_loading=true;
	}
@Override
public int getItemViewType(int position) {
	// TODO Auto-generated method stub
	if (position==0)
		return 0;
	else return 1;
	
}
@Override
public int getViewTypeCount() {
	// TODO Auto-generated method stub
	return mViewTypeCount;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mNewslist!=null)
			return mNewslist.size();
		else
			return 0;
		
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mNewslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(convertView==null)
		{
			if(getItemViewType(position)==0)
			{
		  mNewsBlock= (RelativeLayout) mAdapterinflater.inflate(R.layout.news_block_layout_head, null);
		((Youm7TextView) (mNewsBlock.findViewById(R.id.news_abstract_textview))).setText(mNewslist.get(position).NewsAbstract);
		((Youm7TextView) (mNewsBlock.findViewById(R.id.news_title_textview))).setText(mNewslist.get(position).NewsTitle);
		
		mUniversalimageloader.displayImage(mNewslist.get(position).NewsImgLink.replace("/large/", "/medium/"),  ( (ImageView) (mNewsBlock.findViewById(R.id.newsimageview))),dispoptions);
	 	return mNewsBlock;
			}
			else 
			{
				 mNewsBlock= (RelativeLayout) mAdapterinflater.inflate(R.layout.news_block_layout, null);
					((Youm7TextView) (mNewsBlock.findViewById(R.id.news_title_textview))).setText(mNewslist.get(position).NewsTitle);
					((Youm7TextView) (mNewsBlock.findViewById(R.id.news_abstract_textview))).setText(mNewslist.get(position).NewsAbstract);
					mUniversalimageloader.displayImage(mNewslist.get(position).NewsImgLink.replace("/large/", "/small/"),  ( (ImageView) (mNewsBlock.findViewById(R.id.newsimageview))),dispoptions);
					return mNewsBlock;
			}
			
		
		}
	else
		{
		((Youm7TextView) (convertView.findViewById(R.id.news_abstract_textview))).setText(mNewslist.get(position).NewsAbstract);
		((Youm7TextView) (convertView.findViewById(R.id.news_title_textview))).setText(mNewslist.get(position).NewsTitle);
		if(position==0)
		mUniversalimageloader.displayImage(mNewslist.get(position).NewsImgLink.replace("/large/", "/medium/"),  ( (ImageView) (convertView.findViewById(R.id.newsimageview))),dispoptions);
		else
			mUniversalimageloader.displayImage(mNewslist.get(position).NewsImgLink.replace("/large/", "/small/"),  ( (ImageView) (convertView.findViewById(R.id.newsimageview))),dispoptions);	
		return convertView;
	    }
		
		

	
}
	@Override
	public void OnTaskCompleted(ArrayList<NewsItem> result, int taskID) {
		if(taskID==0)
		{
		if(result!= null){
		mNewslist= result;
		notifyDataSetChanged();
		is_loading=false;
		RefreshFinishedlistener.RefreshFinished();
		}
		else
			UpdateSection();
	}
	
	else{
		if(result!= null){
			mNewslist.addAll(result);
			notifyDataSetChanged();
			is_loading=false;
			RefreshFinishedlistener.RefreshFinished();
			}
			else
				UpdateSection();
		}
			
		}
}