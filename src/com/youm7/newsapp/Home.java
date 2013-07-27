package com.youm7.newsapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.youm7.newsapp.SectionFragment.OnArticleSelectedListener;

public class Home extends FragmentActivity implements OnArticleSelectedListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	 DrawerLayout mDrawerLayout;
    FragmentManager Fm;
    ListView mRightMenu;
    String[] mSectionNames;
    public  ImageLoaderConfiguration mLoadconfig;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	FragmentManager MainfragmentManager;
	FragmentTransaction trans;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	
    ActionBarDrawerToggle mDrawerToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
        getActionBar();
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setLogo(getResources().getDrawable(R.drawable.ic_logo));
		getActionBar().setDisplayUseLogoEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		
		// Create the adapter that will return a fragment for each of the three
		mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
		mRightMenu= (ListView) findViewById(R.id.drawer_listview);
	    mLoadconfig= new ImageLoaderConfiguration.Builder(this).build();
	    mloadImage=ImageLoader.getInstance();
	    mloadImage.init(mLoadconfig);
	    mRightMenu.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_text_view,getResources().getStringArray(R.array.section_names)));
	    MainfragmentManager= getSupportFragmentManager();
	    mRightMenu.setOnItemClickListener(new DrawerItemClickListener());       
	       
	      
	       
	}
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
       
    }

	

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	
	

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	   
	    	SectionFragment frag= new SectionFragment();
	   Bundle 	sectionDetails= new Bundle();
	   sectionDetails.putString("SecTitle", getResources().getStringArray(R.array.section_names)[position]);
	   sectionDetails.putString("SecID",Integer.toString( getResources().getIntArray(R.array.sectionIDs)[position+1])); 	
	  frag.setArguments(sectionDetails);
	   trans=MainfragmentManager.beginTransaction();
	   trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	       trans.replace(R.id.FragHolder, frag).addToBackStack(null);
	       trans.commit();
	       mDrawerLayout.closeDrawer(mRightMenu);
	    
	    }
	}

	/** Swaps fragments in the main content view */
	
	@Override
	public void setTitle(CharSequence title) {
	    
	  
	}
	

	@Override
	public void OnArticleSelected(Fragment frag, Fragment LeavingFrsg,
			boolean AddToBS) {
		// TODO Auto-generated method stub
		
		getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		getSupportFragmentManager().beginTransaction().replace(R.id.FragHolder, frag).addToBackStack(null).commit();
		
	}}
