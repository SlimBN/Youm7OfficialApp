package com.youm7.newsapp;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.youm7.newsapp.NewsLoader.TaskCompletedListener;
import com.youm7.newsapp.SectionFragment.OnArticleSelectedListener;


public class NewsLoaderMixed implements  TaskCompletedListener,OnClickListener{

	OnHomeArticleSelected HomeFrag;
	
	private volatile  SparseArrayCompat<NewsItem>  mixednews;
	NewsLoader mLoader;
	LinearLayout RightColumn;
	LinearLayout LeftColumn;
	LinearLayout holder;
	private Context context;
	private int NumberOfSections;
	int i=0;
	private ImageLoader mImageloader;
	private LayoutInflater inflater;
	private String[] mSectionNames;
	String[] mSectionIds;
	volatile int j=0;
	OnArticleSelectedListener Articleclicklistener;
	public NewsLoaderMixed(
			View holder, Context context ,ImageLoader loader,Fragment Frag) {
		super();
		mImageloader=loader;
		HomeFrag=(OnHomeArticleSelected) Frag;
		this.holder = (LinearLayout) holder;
		RightColumn=(LinearLayout) holder.findViewById(R.id.rightcolumnView);
		LeftColumn=(LinearLayout) holder.findViewById(R.id.leftcolumnView);
		this.context = context;
		mLoader= new NewsLoader();
		mSectionIds=context.getResources().getStringArray(R.array.home_sectionID);
		mSectionNames= context.getResources().getStringArray(R.array.home_sectionNames);
		NumberOfSections=mSectionIds.length;
		mixednews=new SparseArrayCompat<NewsItem>();
		inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for(i=0;i<NumberOfSections;i++)
		{
		mLoader=new NewsLoader();
		mLoader.loadSection(ConstructURL(mSectionIds[i]), this, 2,i);
		}
	}
	
	@Override
	public  synchronized void OnTaskCompleted(ArrayList<NewsItem> result, int taskID) {
	
	mixednews.put(taskID*2,result.get(0));
	mixednews.put(taskID*2+1,result.get(1));
	j++;
	if(j==NumberOfSections)
	{
		FillLayout();
		
	}
	
		
		
	}
	private void FillLayout() {
		// TODO Auto-generated method stub
		//fill the right comlumn
		int j= mixednews.size();
		for(int i=0;i<j;i+=4)
		{
			RelativeLayout temp=	(RelativeLayout) inflater.inflate(R.layout.homenewssection, null);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setText(mixednews.get(i).NewsTitle);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setTag(mixednews.get(i));
			((TextView)(temp.findViewById(R.id.SecTitleTV))).setText(mSectionNames[i/2]);
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setText(mixednews.get(i+1).NewsTitle);
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setTag(mixednews.get(i+1));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setOnClickListener(this);;
		    mImageloader.displayImage(mixednews.get(i).NewsImgLink,(ImageView) temp.findViewById(R.id.secimg));
			RightColumn.addView(temp);
		}
		// fill left column
		for(int i=2;i<j;i+=4)
		{
			RelativeLayout temp=	(RelativeLayout) inflater.inflate(R.layout.homenewssection, null);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setText(mixednews.get(i).NewsTitle);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setTag(mixednews.get(i));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setText(mixednews.get(i+1).NewsTitle);
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setTag(mixednews.get(i+1));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.SecTitleTV))).setText(mSectionNames[i/2]);
		
		    mImageloader.displayImage(mixednews.get(i).NewsImgLink,(ImageView) temp.findViewById(R.id.secimg));
		    LeftColumn.addView(temp);
			
		}
		
	}
	

private String ConstructURL(String SecID)
{
	String template=  context.getResources().getString(R.string.sectionapi);
	return template.replace("{SecID}", SecID);
	
	
}



@Override
public void onClick(View v) {
	
	HomeFrag.HoMeSelected((NewsItem) v.getTag());
	
}

}
