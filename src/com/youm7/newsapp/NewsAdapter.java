package com.youm7.newsapp;

import java.util.ArrayList;






import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

public class NewsAdapter extends BaseAdapter {

	private ArrayList<NewsItem> mNewslist;
	LayoutInflater mAdapterinflater;
	RelativeLayout mNewsBlock;
	ImageLoaderConfiguration mLoadconfig;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	public NewsAdapter( ArrayList<NewsItem> newslist,Context context) {
		
		super();
		mNewslist=newslist;
		mAdapterinflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLoadconfig= new ImageLoaderConfiguration.Builder(context).build();
        mloadImage=ImageLoader.getInstance();
        mloadImage.init(mLoadconfig);
        dispoptions= new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .build();
        
        
        		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return mNewslist.size();
		
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
		  mNewsBlock= (RelativeLayout) mAdapterinflater.inflate(R.layout.news_block_layout, null);
		((Youm7TextView) (mNewsBlock.findViewById(R.id.newstitletextview))).setText(mNewslist.get(position).NewsTitle);
		
		mloadImage.displayImage(mNewslist.get(position).NewsImgLink,  ( (ImageView) (mNewsBlock.findViewById(R.id.newsimageview))),dispoptions);
		return mNewsBlock;
		
		}
	else
		{
		((Youm7TextView) (convertView.findViewById(R.id.newstitletextview))).setText(mNewslist.get(position).NewsTitle);
		mloadImage.displayImage(mNewslist.get(position).NewsImgLink,  ( (ImageView) (convertView.findViewById(R.id.newsimageview))),dispoptions);
		return convertView;
	    }
		
		

	


}
}