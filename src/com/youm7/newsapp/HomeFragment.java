package com.youm7.newsapp;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import android.support.v7.app.ActionBarActivity;








import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.viewpagerindicator.IconPageIndicator;
public class HomeFragment extends Fragment implements OnHomeArticleSelected ,OnClickListener, OnRefreshListener<SmartViewPager>{

	PullToRefreshScrollView pullToRefreshView;
	RelativeLayout mNewsSectionsHolder;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	ViewPager mViewPager;
	ImageLoaderConfiguration mLoadconfig ;
    ArrayList<NewsItem> collectedsections;
    myPagerAdapter TopstoryAdapter;
    MixedNewsAdapter mixednewsloader;
    OnArticleSelectedListener CallActivity;
    LayoutInflater HomeViewInflater;
    View FragmentView;
    int RefreshCounter=3;
    private static boolean FirstRun=false;
    PagerContainer mogazcontainer;
    SmartViewPager mogazpager;
    mogazPagerAdapter mogazadapter;
    IconPageIndicator topindicator;
    Youm7TextView mogazTitle;
    PullToRefreshViewPager mRefViewpager;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	       
		
		FragmentView =  inflater.inflate(R.layout.fragment_home_dummy,null);
		mRefViewpager=(PullToRefreshViewPager) FragmentView.findViewById(R.id.TopStoryPager);
		mViewPager= mRefViewpager.getRefreshableView();
		mViewPager.setAdapter(TopstoryAdapter);
		mRefViewpager.setOnRefreshListener(this);
		mRefViewpager.setMode(Mode.BOTH);
		mRefViewpager.getLoadingLayoutProxy().setPullLabel(null);
		mRefViewpager.getLoadingLayoutProxy().setRefreshingLabel(null);
		mRefViewpager.getLoadingLayoutProxy().setReleaseLabel(null);
		topindicator=(IconPageIndicator) FragmentView.findViewById(R.id.idicator);
		
		pullToRefreshView = (PullToRefreshScrollView) FragmentView;	
		pullToRefreshView.getLoadingLayoutProxy().setRefreshingLabel(getString(R.string.Refreshing_label));
		pullToRefreshView.getLoadingLayoutProxy().setPullLabel(getString(R.string.pull_label));
		pullToRefreshView.getLoadingLayoutProxy().setReleaseLabel(getString(R.string.release_label));
		mogazcontainer = (PagerContainer) FragmentView.findViewById(R.id.mogaz_container);
		mogazTitle= (Youm7TextView) FragmentView.findViewById(R.id.mogazTitle);
		mogazpager = (SmartViewPager) mogazcontainer.getViewPager();
	    mogazpager.setClipChildren(false);
		mogazpager.setAdapter(mogazadapter);
		topindicator.setViewPager(mViewPager);
		topindicator.notifyDataSetChanged();
	    mogazpager.setOffscreenPageLimit(4);
	    mogazpager.setPageMargin(15);
	       
	       mixednewsloader.setAdapter(FragmentView.findViewById(R.id.sections_holder));
	       mogazpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				mogazTitle.setText(mogazadapter.getItem(arg0).NewsTitle);
				mogazcontainer.onPageSelected(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				mogazcontainer.onPageScrolled(arg0, arg1, arg2);
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				mogazcontainer.onPageScrollStateChanged(arg0);
			}
		});
	       mogazpager.setCurrentItem(100, false);
	       mViewPager.setCurrentItem(TopstoryAdapter.getCount()-1,true);
	     
	        //end of mogaz part
			pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
			    @Override
			    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
			        // Do work to refresh the list here.
			    	mixednewsloader.UpdateSections();
			    	TopstoryAdapter.UpdateTopStory();
			    	RefreshCounter=2;
			        
			    }
			});
			return FragmentView;
	}


	public HomeFragment(Context context, FragmentActivity activity) {
		CallActivity=(OnArticleSelectedListener) activity;
		dispoptions= new DisplayImageOptions
        		.Builder()
        .cacheInMemory(true)
        .cacheOnDisc(true)
        .build();
		
 
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
       
        mLoadconfig= new ImageLoaderConfiguration.Builder(context).build();
	    mloadImage=ImageLoader.getInstance();
	    mloadImage.init(mLoadconfig);
	    TopstoryAdapter=new myPagerAdapter(4,context,mloadImage,this);
		
		mixednewsloader=new MixedNewsAdapter(context, mloadImage, this);
		
		
		
		
		
		//mogaz part
		
		 
      
        mogazadapter = new mogazPagerAdapter(100, context, mloadImage, this);
       
        
       
	
	}
	@Override
	public void onStart() {
		
		// TODO Auto-generated method stu
	    super.onStart();
	    ((ActionBarActivity)( getActivity())).getSupportActionBar().getCustomView().findViewById(R.id.share_icon).setVisibility(View.INVISIBLE);
		 
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	  
	       
	    
	  
	       
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		
	}


	@Override
	public void HomeSelected(NewsItem item) {
		// TODO Auto-generated method stub
		ArticleFragment frag=new ArticleFragment();
		NewsItem ArticleToOpen=(NewsItem) item;
		Bundle details=new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		frag.setArguments(details);
		
		CallActivity.OnHomeArticleSelected(frag,this,true);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ArticleFragment frag=new ArticleFragment();
		NewsItem ArticleToOpen=(NewsItem)TopstoryAdapter.getItem( mViewPager.getCurrentItem());
		Bundle details=new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		frag.setArguments(details);
		Log.d("home clicked ", "true");
		CallActivity.OnHomeArticleSelected(frag,this,true);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e("mainfrag", "paused");
		
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
 		super.onSaveInstanceState(outState);
		Log.e("mainfrag", "onsaveinsstancestate");
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("mainfrag", "stopped");
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	@Override
	public void RefreshFinished() {
		// TODO Auto-generated method stub
		RefreshCounter--;
		if(RefreshCounter==0 && !FirstRun)
		{
			
			
			try {
				pullToRefreshView.onRefreshComplete();
				
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CallActivity.OnHomeUpdateFinished();
			RefreshCounter=3;
			FirstRun=true;
		}
		else if(RefreshCounter==0 && FirstRun)
		{
			mViewPager.setAdapter(TopstoryAdapter);
			mViewPager.setCurrentItem(TopstoryAdapter.getCount()-1);
			mogazpager.setAdapter(mogazadapter);
			mogazpager.setCurrentItem(100);
			
			pullToRefreshView.onRefreshComplete();
			RefreshCounter=3;
			
		}
		
			
	}


	

	@Override
	public void onRefresh(PullToRefreshBase<SmartViewPager> refreshView) {
		// TODO Auto-generated method stub
		refreshView.onRefreshComplete();
	}
	
	

}
