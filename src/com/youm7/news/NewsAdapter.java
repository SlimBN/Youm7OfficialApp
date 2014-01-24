package com.youm7.news;

import java.util.ArrayList;









import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.youm7.news.NewsLoader.TaskCompletedListener;


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

public class NewsAdapter extends BaseAdapter implements TaskCompletedListener {

	private ArrayList<NewsItem> mNewslist;
	LayoutInflater mAdapterinflater;
	RelativeLayout mNewsBlock;
	static DisplayImageOptions dispoptions= new DisplayImageOptions.Builder()
	.cacheInMemory(true)
	.cacheOnDisc(true)
	.build(); 
	 static final String topurl="http://mobrss.youm7.com/rss/Service.svc/NewsTopStories";
		ImageLoader mUniversalimageloader;
	int mViewTypeCount;
NewsLoader testloader;
	public NewsAdapter( ArrayList<NewsItem> newslist,Context context, int viewTypeCount,ImageLoader Universalimageloader) {
		
		
		super();
		mViewTypeCount=viewTypeCount;
		mNewslist=newslist;
		mAdapterinflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mUniversalimageloader=Universalimageloader;
        mNewslist.add(new NewsItem());
        testloader=new NewsLoader();
        testloader.loadSection(topurl, this, 100);
        
        		
	}
@Override
public int getItemViewType(int position) {
	// TODO Auto-generated method stub
	return super.getItemViewType(position);
	
}

@Override
public int getViewTypeCount() {
	// TODO Auto-generated method stub
	return mViewTypeCount;
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
		((Youm7TextView) (mNewsBlock.findViewById(R.id.news_abstract_textview))).setText(mNewslist.get(position).NewsTitle);
		
		mUniversalimageloader.displayImage(mNewslist.get(position).NewsImgLink,  ( (ImageView) (mNewsBlock.findViewById(R.id.newsimageview))),dispoptions);
		return mNewsBlock;
		
		}
	else
		{
		((Youm7TextView) (convertView.findViewById(R.id.news_abstract_textview))).setText(mNewslist.get(position).NewsTitle);
		mUniversalimageloader.displayImage(mNewslist.get(position).NewsImgLink,  ( (ImageView) (convertView.findViewById(R.id.newsimageview))),dispoptions);
		return convertView;
	    }
		
		

	


}
	@Override
	public void OnTaskCompleted(ArrayList<NewsItem> result, int taskID) {
		mNewslist= result;
		notifyDataSetChanged();
		
	}
}