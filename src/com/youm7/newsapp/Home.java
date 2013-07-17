package com.youm7.newsapp;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	ListView mRightMenu;
    String[] mSectionNames;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mSectionNames = getResources().getStringArray(R.array.section_names);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mRightMenu = (ListView) findViewById(R.id.left_drawer);
		mRightMenu.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_text_view, mSectionNames));
		mRightMenu.setOnItemClickListener(new DrawerItemClickListener() );
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	     getActionBar().setHomeButtonEnabled(true);
		   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	         mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
	                R.string.drawer_open , /* "open drawer" description */
	                R.string.drawer_close  /* "close drawer" description */
	                ) {

	            /** Called when a drawer has settled in a completely closed state. */
	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle("");
	            }

	            /** Called when a drawer has settled in a completely open state. */
	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle("choose");
	            }
	        };

	        // Set the drawer toggle as the DrawerListener
	        mDrawerLayout.setDrawerListener(mDrawerToggle);

	       
	      
	       
	}
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
		
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
        public NewsLoader fragmentloader;
        public NewsAdapter sectionadapter;
        ArrayList<NewsItem> sectionNews;
        ListView sectionscroller;
		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.news_category_layout,
					container, false);
			fragmentloader=new NewsLoader();
            fragmentloader.loadSection("http://mobrss.youm7.com/rss/service.svc/SelectForSpecifiedSection/SecID/65/page/1",this);
			sectionscroller=(ListView) rootView.findViewById(R.id.news_listview) ;
            return rootView;
		}
	
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
		}
		public void OndownloadFinished(ArrayList<NewsItem> DownloadedNews)
		{
			sectionNews=DownloadedNews;
			sectionadapter=new NewsAdapter(sectionNews, this.getActivity());
			sectionscroller.setAdapter(sectionadapter);
			
		}
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	       
	    }
	}

	/** Swaps fragments in the main content view */
	
	@Override
	public void setTitle(CharSequence title) {
	    
	  
	}
}
