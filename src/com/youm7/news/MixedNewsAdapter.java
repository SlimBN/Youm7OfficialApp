package com.youm7.news;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youm7.news.NewsLoader.TaskCompletedListener;




public class MixedNewsAdapter implements  TaskCompletedListener,OnClickListener{

	OnHomeArticleSelected HomeFrag;
	
	private volatile  ArrayList<NewsItem>  mixednews;
	NewsLoader mLoader;
	LinearLayout RightColumn;
	LinearLayout LeftColumn;
	LinearLayout holder;
	RelativeLayout featuresHolder;
	List<NewsItem> featurenews;
	private Context context;
	private int NumberOfSections;
	int i=0;
	private ImageLoader mImageloader;
	private LayoutInflater inflater;
	private String[] mSectionNames;
	String mSectionIds;
	volatile int j=0;
	OnArticleSelectedListener Articleclicklistener;
	static DisplayImageOptions dispoptions= new DisplayImageOptions.Builder()
	.cacheInMemory(true)
	.cacheOnDisc(true)
	.build(); 
	public MixedNewsAdapter(
			Context context, ImageLoader loader ,Fragment Frag) {
		super();
		mImageloader=loader;
		HomeFrag=(OnHomeArticleSelected) Frag;
		
		this.context = context;
		mLoader= new NewsLoader();
		mSectionIds=context.getResources().getString(R.string.homesectionnews);
		mSectionNames= context.getResources().getStringArray(R.array.home_sectionNames);
		featurenews =new ArrayList<NewsItem>();
		mixednews=new ArrayList<NewsItem>();
		inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		UpdateSections();
	}
	public void setAdapter(View holder,View features)
	{
		if(holder!=null)
		{
		this.holder = (LinearLayout) holder;
		RightColumn=(LinearLayout) holder.findViewById(R.id.rightcolumnView);
		LeftColumn=(LinearLayout) holder.findViewById(R.id.leftcolumnView);
	    featuresHolder =(RelativeLayout) features;
	
		if(mixednews.size()>0 && this.holder.getChildCount()<3)
		
			FillLayout();
			
		}
		else{
			
			try {
				FillLayout();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void UpdateSections()
	{
		
		
		mLoader=new NewsLoader();
		mLoader.loadSection(mSectionIds, this, 100);
		
		
	}
	@Override
	public  synchronized void OnTaskCompleted(ArrayList<NewsItem> result, int taskID) {
		if (result!=null)
		{
		
     	mixednews= result;
	    
		setAdapter(null,null);
		HomeFrag.RefreshFinished();
		}
		else 
			UpdateSections();
	
	
		
		
	}
	private void FillLayout() {
		// TODO Auto-generated method stub
		//fill the right comlumn
		int i=0;
		((TextView)(featuresHolder.findViewById(R.id.mainarticleTV))).setText(mixednews.get(i).NewsTitle);
		((TextView)(featuresHolder.findViewById(R.id.mainarticleTV))).setOnClickListener(this);
		((TextView)(featuresHolder.findViewById(R.id.mainarticleTV))).setTag(mixednews.get(i));
		ImageView tempimg=(ImageView) featuresHolder.findViewById(R.id.secimg1);
	    mImageloader.displayImage(mixednews.get(i).smallImageLink.replace("/large/", "/small/"),tempimg,dispoptions);
	    tempimg.setTag(mixednews.get(i));
	    tempimg.setOnClickListener(this);
		((TextView)(featuresHolder.findViewById(R.id.secondarticleTV))).setText(mixednews.get(1).NewsTitle);
		((TextView)(featuresHolder.findViewById(R.id.secondarticleTV))).setTag(mixednews.get(1));
		((TextView)(featuresHolder.findViewById(R.id.secondarticleTV))).setOnClickListener(this);
		 tempimg=(ImageView) featuresHolder.findViewById(R.id.secimg2);
	    mImageloader.displayImage(mixednews.get(1).smallImageLink.replace("/large/", "/small/"),tempimg,dispoptions);
	    tempimg.setTag(mixednews.get(1));
	    tempimg.setOnClickListener(this);
		((TextView)(featuresHolder.findViewById(R.id.thirdarticleTV))).setText(mixednews.get(2).NewsTitle);
		((TextView)(featuresHolder.findViewById(R.id.thirdarticleTV))).setTag(mixednews.get(2));
		((TextView)(featuresHolder.findViewById(R.id.thirdarticleTV))).setOnClickListener(this);
		 tempimg=(ImageView) featuresHolder.findViewById(R.id.secimg3);
	    mImageloader.displayImage(mixednews.get(2).smallImageLink.replace("/large/", "/small/"),tempimg,dispoptions);
	    tempimg.setTag(mixednews.get(2));
	    tempimg.setOnClickListener(this);
		int j= 55;
		//RightColumn.removeAllViews();
		//LeftColumn.removeAllViews();
		
		RelativeLayout temp;
		int z=0;
		for( i=5;i<j;i+=10)
		{   
			if((temp=(RelativeLayout) RightColumn.getChildAt(z))!=null){
				// temp= (RelativeLayout) RightColumn.getChildAt(z);
			}
			else{
				 temp=	(RelativeLayout) inflater.inflate(R.layout.homenewssection, null);
		         RightColumn.addView(temp);
			}
			
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setText(mixednews.get(i).NewsTitle);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setTag(mixednews.get(i));
			((TextView)(temp.findViewById(R.id.SecTitleTV))).setText(mSectionNames[i/5-1]);
			
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setText(mixednews.get(i+1).NewsTitle);
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setTag(mixednews.get(i+1));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setText(mixednews.get(i+2).NewsTitle);
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setTag(mixednews.get(i+2));
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setText(mixednews.get(i+3).NewsTitle);
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setTag(mixednews.get(i+3));
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setText(mixednews.get(i+4).NewsTitle);
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setTag(mixednews.get(i+4));
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setOnClickListener(this);
			tempimg=(ResizableImageView) temp.findViewById(R.id.secimg);
			
			
		    mImageloader.displayImage(mixednews.get(i).smallImageLink.replace("/large/", "/medium/"),tempimg,dispoptions);
		    tempimg.setTag(mixednews.get(i));
		    tempimg.setOnClickListener(this);
		    z++;
			
		}
		// fill left column
		z=0;
		for( i=10;i<j;i+=10)
		{
			if((temp=(RelativeLayout) LeftColumn.getChildAt(i/10-1))!=null){
				// temp= (RelativeLayout) LeftColumn.getChildAt(i/10-1);
			}
			else{
				 temp=	(RelativeLayout) inflater.inflate(R.layout.homenewssection, null);
		         LeftColumn.addView(temp);
			}
			
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setText(mixednews.get(i).NewsTitle);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.mainarticleTV))).setTag(mixednews.get(i));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setText(mixednews.get(i+1).NewsTitle);
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setTag(mixednews.get(i+1));
			((TextView)(temp.findViewById(R.id.secondarticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setText(mixednews.get(i+2).NewsTitle);
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setTag(mixednews.get(i+2));
			((TextView)(temp.findViewById(R.id.thirdarticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setText(mixednews.get(i+3).NewsTitle);
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setTag(mixednews.get(i+3));
			((TextView)(temp.findViewById(R.id.fourtharticleTV))).setOnClickListener(this);;
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setText(mixednews.get(i+4).NewsTitle);
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setTag(mixednews.get(i+4));
			((TextView)(temp.findViewById(R.id.fiftharticleTV))).setOnClickListener(this);
			((TextView)(temp.findViewById(R.id.SecTitleTV))).setText(mSectionNames[i/5-1]);
		    tempimg=(ImageView) temp.findViewById(R.id.secimg);
		   
		    mImageloader.displayImage(mixednews.get(i).smallImageLink.replace("/large/", "/medium/"),tempimg,dispoptions);
		    tempimg.setTag(mixednews.get(i));
		    tempimg.setOnClickListener(this);
		   // LeftColumn.addView(temp);
			z++;
		}
		
	}
	

private String ConstructURL(String SecID)
{
	String template=  context.getResources().getString(R.string.sectionapi);
	return template.replace("{SecID}", SecID);
	
	
}



@Override
public void onClick(View v) {
	
	HomeFrag.HomeSelected((NewsItem) v.getTag());
	
}

}
