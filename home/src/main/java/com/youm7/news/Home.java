package com.youm7.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class Home extends ActionBarActivity implements
		OnArticleSelectedListener, OnClickListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	public boolean Frag_Paused;
	public boolean Pending_Refresh;
	DrawerLayout mDrawerLayout;
	FragmentManager Fm;
	ListView mRightMenu;
	String[] mSectionNames;
	public ImageLoaderConfiguration mLoadconfig;
	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	FragmentManager MainfragmentManager;
	FragmentTransaction trans;
	HomeFragment Startingfrag;
	ImageView logoImg;
    Comments smsDialog;
    ImageView smsIcon;
	public String fullsitelink = "http://www.youm7.com/";
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */

	ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
		if (this.getSupportFragmentManager().getFragments() == null)
			getSupportActionBar().hide();

		setContentView(R.layout.activity_home);
		// getPreferences(MODE_PRIVATE).edit().putBoolean("isloading", true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setScrimColor(R.color.Youm7Red);
		mRightMenu = (ListView) findViewById(R.id.drawer_listview);
		mLoadconfig = new ImageLoaderConfiguration.Builder(this).build();
		mloadImage = ImageLoader.getInstance();
		mloadImage.init(mLoadconfig);
		smsIcon= (ImageView) findViewById(R.id.sms_ico);
		LayoutInflater headerinflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRightMenu.addHeaderView(
				headerinflater.inflate(R.layout.drawer_header, null), null,
				true);
		mRightMenu.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_text_view, getResources().getStringArray(
						R.array.section_names)));

		mRightMenu.setOnItemClickListener(new DrawerItemClickListener());
		Startingfrag = (HomeFragment) getSupportFragmentManager()
				.findFragmentByTag("Homefrag");

		if (Startingfrag == null
				&& getSupportFragmentManager().getFragments() == null) {
			if (isOnline()) {
				Startingfrag = new HomeFragment();
				getSupportFragmentManager()
						.beginTransaction()
						.add(R.id.FragHolder, Startingfrag, "Homefrag")
						.setTransitionStyle(
								FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.commit();

			} else {
				Toast.makeText(this,
						"برجاء التأكد من الاتصال بالنترنت",
						Toast.LENGTH_LONG).show();
				this.finish();
			}

		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.

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
		Frag_Paused = true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getSupportActionBar().getCustomView().findViewById(R.id.logoimg)
		.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mDrawerLayout.isDrawerOpen(mRightMenu)) {
					mDrawerLayout.closeDrawer(mRightMenu);

				} else {
					mDrawerLayout.openDrawer(mRightMenu);

				}
			}
		});
		smsIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				smsDialog= new Comments();
				smsDialog.show(getSupportFragmentManager(), "sms");
			}
		});
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Log.e("mainactivity", "onrestore");
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Pending_Refresh) {

			Frag_Paused = false;
			OnHomeUpdateFinished();

		}
		Log.e("mainactivity", "resumed");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("mainactivity", "destroyed");
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position,
				long id) {
			trans = getSupportFragmentManager().beginTransaction();
			if (position == 0) {
				if (getSupportFragmentManager().findFragmentById(
						R.id.FragHolder).getTag() == "Homefrag") {
					mDrawerLayout.closeDrawer(mRightMenu);
				} else if (getSupportFragmentManager().popBackStackImmediate(
						"Homefrag", FragmentManager.POP_BACK_STACK_INCLUSIVE) == true) {
					
					mDrawerLayout.closeDrawer(mRightMenu);
					Log.e("frag nums", Integer
							.toString(getSupportFragmentManager()
									.getFragments().size()));
					
				} else {
					Startingfrag = new HomeFragment();
					getSupportFragmentManager()
							.beginTransaction()
							.add(R.id.FragHolder, Startingfrag, "Homefrag")
							.setTransitionStyle(
									FragmentTransaction.TRANSIT_FRAGMENT_FADE)
							.commit();
                        
				}

			} else {
				SectionFragment frag = new SectionFragment();
				fullsitelink = "http://www.youm7.con/NewsSection.asp?SecID="
						+ Integer.toString(getResources().getIntArray(
								R.array.sectionIDs)[position - 1]);
				Bundle sectionDetails = new Bundle();
				sectionDetails.putString("SecTitle", getResources()
						.getStringArray(R.array.section_names)[position - 1]);
				sectionDetails.putString(
						"SecID",
						Integer.toString(getResources().getIntArray(
								R.array.sectionIDs)[position - 1]));
				frag.setArguments(sectionDetails);

				trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				trans.replace(
						R.id.FragHolder,
						frag,
						Integer.toString(getResources().getIntArray(
								R.array.sectionIDs)[position - 1]));
				if (getSupportFragmentManager().findFragmentByTag("Homefrag") != null)
					trans.addToBackStack("Homefrag");
				else
					trans.addToBackStack(null);
				mDrawerLayout.closeDrawer(mRightMenu);
				getSupportActionBar().getCustomView()
						.findViewById(R.id.fullsite)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent i = new Intent(Intent.ACTION_VIEW, Uri
										.parse(fullsitelink));
								startActivity(i);

							}
						});
				trans.commit();
			}
		}
	}

	/** Swaps fragments in the main content view */

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.e("mainactivity", "restarted");
	}

	@Override
	public void OnHomeArticleSelected(Fragment frag, Fragment LeavingFrsg,
			boolean AddToBS) {
		// TODO Auto-generated method stub

		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.replace(R.id.FragHolder, frag).addToBackStack("Homefrag");
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		trans.commit();
	}

	@Override
	public void OnHomeUpdateFinished() {
		// TODO Auto-generated method stu
		if (!Frag_Paused) {
			// supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
			getSupportActionBar().show();

			

			

			
			Pending_Refresh = false;
		} else {
			Pending_Refresh = true;
		}
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnected();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		getSupportFragmentManager().popBackStack("Homefrag",
				FragmentManager.POP_BACK_STACK_INCLUSIVE);

	}

	@Override
	public void onSecArticleSelected(Fragment frag, String fragid,
			boolean AddToBS) {
		// TODO Auto-generated method stub
		FragmentTransaction trans = getSupportFragmentManager()
				.beginTransaction();
		trans.replace(R.id.FragHolder, frag).addToBackStack(fragid);
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		trans.commit();
	}

}