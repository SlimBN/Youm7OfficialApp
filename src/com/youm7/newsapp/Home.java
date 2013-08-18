package com.youm7.newsapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class Home extends ActionBarActivity implements OnArticleSelectedListener, OnClickListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	public static boolean Frag_Paused;
    public static boolean Pending_Refresh;
	 DrawerLayout mDrawerLayout;
    FragmentManager Fm;
    ListView mRightMenu;
    String[] mSectionNames;
    public  ImageLoaderConfiguration mLoadconfig;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	FragmentManager MainfragmentManager;
	FragmentTransaction trans;
	HomeFragment Startingfrag;
	ImageView logoImg;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	
    ActionBarDrawerToggle mDrawerToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//supportRequestWindowFeature(Window.);
		//supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
		// getSupportActionBar().hide();
		setContentView(R.layout.activity_home);
		getPreferences(MODE_PRIVATE).edit().putBoolean("isloading", true);
      
	
	    
		mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setScrimColor(R.color.Youm7Red);
		mRightMenu= (ListView) findViewById(R.id.drawer_listview);
	    mLoadconfig= new ImageLoaderConfiguration.Builder(this).build();
	    mloadImage=ImageLoader.getInstance();
	    mloadImage.init(mLoadconfig);
	    LayoutInflater  headerinflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    mRightMenu.addHeaderView(headerinflater.inflate(R.layout.drawer_header, null),null,false);
	    mRightMenu.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_text_view,getResources().getStringArray(R.array.section_names)));
	
	    MainfragmentManager= getSupportFragmentManager();
	    mRightMenu.setOnItemClickListener(new DrawerItemClickListener());       
	   
	       
	}
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (isOnline())
       
        Startingfrag=new HomeFragment(this.getApplicationContext(),this);
        else 
        {
        	Toast.makeText(this, "تأكد من الاتصال بالانترنت",Toast.LENGTH_LONG);
        	this.finish();
        }
       
        
    }
@Override
protected void onSaveInstanceState(Bundle outState) {
	// TODO Auto-generated method stub
	super.onSaveInstanceState(outState);
	Log.e("mainactivity", "onsaveinsstancestate");
}
@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	Log.e("mainactivity", "stopped");
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	Log.e("mainactivity", "paused");
	Frag_Paused=true;
}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		
		super.onStart();
		
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Pending_Refresh)
		{
			
			Frag_Paused=false;
			OnHomeUpdateFinished();
			
		}
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
	   sectionDetails.putString("SecTitle", getResources().getStringArray(R.array.section_names)[position-1]);
	   sectionDetails.putString("SecID",Integer.toString( getResources().getIntArray(R.array.sectionIDs)[position-1])); 	
	   frag.setArguments(sectionDetails);
	   trans=MainfragmentManager.beginTransaction();
	   trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	       trans.replace(R.id.FragHolder, frag);
	       if(getSupportFragmentManager().findFragmentByTag("Homefrag")!=null)
	    	   trans.addToBackStack("Homefrag");
	       else 
	    	   trans.addToBackStack(null);
	       trans.commit();
	       mDrawerLayout.closeDrawer(mRightMenu);
	    
	    }
	}

	/** Swaps fragments in the main content view */
	
	

	@Override
	public void OnHomeArticleSelected(Fragment frag, Fragment LeavingFrsg,
			boolean AddToBS) {
		// TODO Auto-generated method stub
		
		FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.FragHolder, frag).addToBackStack("Homefrag");
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		trans.commit();
	}
	@Override
	public void OnHomeUpdateFinished() {
		// TODO Auto-generated method stu
	if(!Frag_Paused){
		//supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
		//getSupportActionBar().show();
		
		getSupportFragmentManager().beginTransaction().replace(R.id.FragHolder, Startingfrag,"Homefrag").setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
		
		 logoImg=(ImageView) getSupportActionBar().getCustomView().findViewById(R.id.logoimg);
		 logoImg.setOnClickListener(this);
		 getSupportActionBar().getCustomView().findViewById(R.id.ImageView2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mDrawerLayout.isDrawerOpen(mRightMenu))
					mDrawerLayout.closeDrawer(mRightMenu);
				else 
					mDrawerLayout.openDrawer(mRightMenu);
			}
		});
		getSupportActionBar().getCustomView().findViewById(R.id.fullsite).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW, 
					       Uri.parse("http://www.youm7.com/"));
					startActivity(i);
				
			}
		});
		Pending_Refresh=false;
	}
	else{
		Pending_Refresh=true;
	}
	}
	public boolean isOnline()
	{
		ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isConnected();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MainfragmentManager.popBackStack("Homefrag",FragmentManager.POP_BACK_STACK_INCLUSIVE);
		
	}
	@Override
	public void onSecArticleSelected(Fragment frag, Fragment LeavingFrsg, boolean AddToBS) {
		// TODO Auto-generated method stub
		FragmentTransaction trans=getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.FragHolder, frag).addToBackStack("secfrag");
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		trans.commit();
	}
	}
