package com.youm7.news;

import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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


public class HomeFragment extends Fragment implements OnHomeArticleSelected,
		OnClickListener, OnRefreshListener<SmartViewPager> {

	PullToRefreshScrollView pullToRefreshView;
	RelativeLayout mNewsSectionsHolder;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	ViewPager mViewPager;
	ImageLoaderConfiguration mLoadconfig;
	ArrayList<NewsItem> collectedsections;
	myPagerAdapter TopstoryAdapter;
	MixedNewsAdapter mixednewsloader;
	OnArticleSelectedListener CallActivity;
	LayoutInflater HomeViewInflater;
	View FragmentView;
	int RefreshCounter = 3;
	private boolean FirstRun = false;
	PagerContainer mogazcontainer;
	SmartViewPager mogazpager;
	mogazPagerAdapter mogazadapter;
	IconPageIndicator topindicator;
	Youm7TextView mogazTitle;
	PullToRefreshViewPager mRefViewpager;
	boolean isloading;
	View loadingimg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		FragmentView = inflater.inflate(R.layout.fragment_home_dummy, null);
		//ads loading
		AdView adView = (AdView)FragmentView.findViewById(R.id.adView);
	    adView.loadAd(new AdRequest());
		mRefViewpager = (PullToRefreshViewPager) FragmentView
				.findViewById(R.id.TopStoryPager);
		mViewPager = mRefViewpager.getRefreshableView();
		mViewPager.setAdapter(TopstoryAdapter);
		loadingimg = FragmentView.findViewById(R.id.loadingimg);
		mRefViewpager.setOnRefreshListener(this);
		mRefViewpager.setMode(Mode.BOTH);
		mRefViewpager.getLoadingLayoutProxy().setPullLabel(null);
		mRefViewpager.getLoadingLayoutProxy().setRefreshingLabel(null);
		mRefViewpager.getLoadingLayoutProxy().setReleaseLabel(null);
		topindicator = (IconPageIndicator) FragmentView
				.findViewById(R.id.idicator);
		pullToRefreshView = (PullToRefreshScrollView) FragmentView
				.findViewById(R.id.HomePagelayout);
		pullToRefreshView.getLoadingLayoutProxy().setRefreshingLabel(
				getString(R.string.Refreshing_label));
		pullToRefreshView.getLoadingLayoutProxy().setPullLabel(
				getString(R.string.pull_label));
		pullToRefreshView.getLoadingLayoutProxy().setReleaseLabel(
				getString(R.string.release_label));
		mogazcontainer = (PagerContainer) FragmentView
				.findViewById(R.id.mogaz_container);
		mogazTitle = (Youm7TextView) FragmentView.findViewById(R.id.mogazTitle);
		mogazpager = (SmartViewPager) mogazcontainer.getViewPager();
		mogazpager.setAdapter(mogazadapter);
		mogazpager.setClipChildren(false);

		topindicator.setViewPager(mViewPager);
		topindicator.notifyDataSetChanged();
		mogazpager.setOffscreenPageLimit(4);
		mogazpager.setPageMargin(15);
		mixednewsloader.setAdapter(
				FragmentView.findViewById(R.id.sections_holder),
				FragmentView.findViewById(R.id.featuresRL));
		mogazpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				try {
					mogazTitle.setText(mogazadapter.getItem(arg0).NewsTitle);
				} catch (NullPointerException nullex) {
					nullex.printStackTrace();
				}
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
		mViewPager.setCurrentItem(TopstoryAdapter.getCount() - 1, true);

		// end of mogaz part
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						// Do work to refresh the list here.
						mixednewsloader.UpdateSections();
						TopstoryAdapter.UpdateTopStory();
						mogazadapter.UpdateTopStory();
						RefreshCounter = 3;
						isloading = true;

					}
				});
		((ActionBarActivity) (getActivity())).getSupportActionBar()
				.getCustomView().findViewById(R.id.fullsite)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(Intent.ACTION_VIEW, Uri
								.parse("http://www.youm7.com/"));
						startActivity(i);

					}
				});
		if (this.isloading == false) {

			if (loadingimg != null) {
				((ViewGroup) loadingimg.getParent()).removeView(loadingimg);
				loadingimg = null;
			}
		}
		return FragmentView;
	}

	public HomeFragment() {
	}

	@Override
	public void onStart() {

		// TODO Auto-generated method stu
		super.onStart();
		((ActionBarActivity) (getActivity())).getSupportActionBar()
				.getCustomView().findViewById(R.id.share_icon)
				.setVisibility(View.GONE);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		dispoptions = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(true).build();
		// If hardware acceleration is enabled, you should also remove
		// clipping on the pager for its children.
		mLoadconfig = new ImageLoaderConfiguration.Builder(this.getActivity()
				.getApplicationContext()).build();
		mloadImage = ImageLoader.getInstance();
		mloadImage.init(mLoadconfig);
		TopstoryAdapter = new myPagerAdapter(4, this.getActivity()
				.getApplicationContext(), mloadImage, this);
		mogazadapter = new mogazPagerAdapter(100, this.getActivity()
				.getApplicationContext(), mloadImage, this);
		mixednewsloader = new MixedNewsAdapter(this.getActivity()
				.getApplicationContext(), mloadImage, this);
		isloading = true;
		setRetainInstance(true);

		Log.e("mainfrag", "created");

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		((ActionBarActivity) getActivity()).getSupportActionBar()
				.getCustomView().findViewById(R.id.fullsite)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(Intent.ACTION_VIEW, Uri
								.parse("http://www.youm7.com"));
						startActivity(i);

					}
				});
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		CallActivity = (OnArticleSelectedListener) activity;

	}

	@Override
	public void HomeSelected(NewsItem item) {
		// TODO Auto-generated method stub
		ArticleFragment frag = new ArticleFragment();
		NewsItem ArticleToOpen = (NewsItem) item;
		Bundle details = new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		details.putString("newsdate", ArticleToOpen.NewsDateString);
		details.putString("newswriter", ArticleToOpen.newsWriter);
		frag.setArguments(details);

		CallActivity.OnHomeArticleSelected(frag, this, true);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ArticleFragment frag = new ArticleFragment();
		NewsItem ArticleToOpen = (NewsItem) TopstoryAdapter.getItem(mViewPager
				.getCurrentItem());
		Bundle details = new Bundle();
		details.putString("newstitle", ArticleToOpen.NewsTitle);
		details.putString("newsimagelink", ArticleToOpen.NewsImgLink);
		details.putString("newsid", ArticleToOpen.NewsId);
		details.putString("newsdate", ArticleToOpen.NewsDateString);
		details.putString("newswriter", ArticleToOpen.newsWriter);
		frag.setArguments(details);
		Log.d("home clicked ", "true");
		CallActivity.OnHomeArticleSelected(frag, this, true);
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
		Log.e("mainfrag", "Resume");

	}

	public void updateContext(OnArticleSelectedListener activity) {
		CallActivity = activity;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		Log.e("mainfrag", "destroyed");
	}

	@Override
	public void RefreshFinished() {
		// TODO Auto-generated method stub
		RefreshCounter--;

		if (RefreshCounter == 0) {
			if (loadingimg != null) {
				((ViewGroup) loadingimg.getParent()).removeView(loadingimg);
				loadingimg = null;
				//loadingimg.setVisibility(View.GONE);
			}
			try {
				pullToRefreshView.onRefreshComplete();

			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mViewPager.setAdapter(TopstoryAdapter);

			mogazpager.setAdapter(mogazadapter);

			mogazpager.setCurrentItem(100, false);
			mViewPager.setCurrentItem(TopstoryAdapter.getCount() - 1, true);
			topindicator.notifyDataSetChanged();
			RefreshCounter = 3;
			FirstRun = true;
			CallActivity.OnHomeUpdateFinished();
			isloading = false;
		
		}

	}

	@Override
	public void onRefresh(PullToRefreshBase<SmartViewPager> refreshView) {
		// TODO Auto-generated method stub
		refreshView.onRefreshComplete();
	}

}
