package com.youm7.news;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.youm7.news.NewsLoader.TaskCompletedListener;

public class ArticleFragment extends Fragment implements TaskCompletedListener  {

	// comments variables
	ExpandableListView commLV;
	commAdapter commadapter;
	ArrayList<Comment> comments;
	LayoutInflater commInflater;
	CommLoader commLoader;

	NewsItem Article;

	ImageLoader mloadImage;
	DisplayImageOptions dispoptions;
	String articleapi;
	ImageLoaderConfiguration mLoadconfig;
	NewsLoader loadArticleBody;
	LinearLayout imgHoldeLinearLayout;
	LayoutInflater singArticleInflater;
	TextView newstitle;
	TextView newsdate;
	TextView newswriter;
	TextView newscontent;
	ImageView headimg;
	LinearLayout postcomment;
	
	ProgressBar articlePB;
	View temp;
	EditText name;
	EditText email;
	EditText comTitle;
	EditText comBody;
     Button btnAddComment;
	// TextView newstitle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		((ActionBarActivity) (getActivity())).getSupportActionBar()
				.getCustomView().findViewById(R.id.share_icon)
				.setVisibility(View.VISIBLE);
		 temp = inflater.inflate(R.layout.article_layout, container, false);
		 AdView adView = (AdView)temp.findViewById(R.id.adView);
		 adView.loadAd(new AdRequest());
		dispoptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loader_top).cacheInMemory(true)
				.cacheOnDisc(true).build();
		articlePB = (ProgressBar) temp.findViewById(R.id.articlepb);
		newswriter = (TextView) temp.findViewById(R.id.newswriter);
		newsdate = ((TextView) temp.findViewById(R.id.youm7TextView3));
		newstitle = ((TextView) temp.findViewById(R.id.youmTextView1));
		newscontent = ((TextView) temp.findViewById(R.id.youmTextView2));
		commLV = ((ExpandableListView) temp.findViewById(R.id.commLV));
		headimg = (ImageView) temp.findViewById(R.id.logoimg);
		postcomment= (LinearLayout) temp.findViewById(R.id.addcomment);
		
		btnAddComment=(Button) temp.findViewById(R.id.addcommentbutton);
		newstitle.setText(Article.NewsTitle);
        
		if (commadapter == null)
			commadapter = new commAdapter();

		commLV.setAdapter(commadapter);
		// newswriter.setText(Article.newsWriter);
		commLV.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				// TODO Auto-generated method stub
				commLV.getLayoutParams().height = commLV.getLayoutParams().WRAP_CONTENT;
			}
		});
		commLV.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				setListViewHeightBasedOnChildren(commLV, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40,
								getResources().getDisplayMetrics()));
			}
		});

		imgHoldeLinearLayout = ((LinearLayout) temp
				.findViewById(R.id.imgholder));
		try {
			mloadImage.displayImage(Article.NewsImgLink, headimg);
			newswriter.setText(Article.newsWriter);
			newsdate.setText(Article.NewsDateString);
			newscontent.setText((Article.NewsContent).trim());

			if (Article.imgLinks.size() != 0) {
				ImageView tempholder;
				singArticleInflater = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				for (int i = 0; i < Article.imgLinks.size(); i++) {

					tempholder = (ImageView) singArticleInflater.inflate(
							R.layout.newsimgstub, imgHoldeLinearLayout, false);
					mloadImage
							.displayImage(Article.imgLinks.get(i), tempholder);
					imgHoldeLinearLayout.addView(tempholder);
					// (((RelativeLayout)
					// (getView().findViewById(R.id.Article_holder))).addView(img,
					// LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((ActionBarActivity) (getActivity())).getSupportActionBar()
				.getCustomView().findViewById(R.id.share_icon)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent i = new Intent(
								android.content.Intent.ACTION_SEND);
						i.setType("text/plain");
						i.putExtra(android.content.Intent.EXTRA_TEXT,
								Article.NewsLink);
						startActivity(i);
					}
				});
		((ActionBarActivity) (getActivity())).getSupportActionBar()
				.getCustomView().findViewById(R.id.fullsite)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(Intent.ACTION_VIEW, Uri
								.parse(Article.NewsLink));
						startActivity(i);

					}
				});
		btnAddComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 name= (EditText) temp.findViewById(R.id.namefield);
			     email= (EditText) temp.findViewById(R.id.emailfield);
				comTitle= (EditText) temp.findViewById(R.id.commtitlefield);
				comBody= (EditText) temp.findViewById(R.id.commbodyfield);
				if(name.getText().toString().isEmpty()||email.getText().toString().isEmpty()||comTitle.getText().toString().isEmpty()||comBody.getText().toString().isEmpty())
				{
					Toast.makeText(getActivity().getApplicationContext(), "برجاء اكمال ادخال البيانات", Toast.LENGTH_LONG).show();
					
				}
				else{
					Toast.makeText(getActivity().getApplicationContext(), "جارى ارسال تعليقك", Toast.LENGTH_LONG).show();
					
					postComment CommentPoster = new postComment();

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)

						CommentPoster.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					else
						CommentPoster.execute();
					
				}
			}
		});
		newstitle.requestFocus();
		return temp;
	}

	public ArticleFragment() {

	}
@Override
public void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	temp.requestFocus();
}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (!newscontent.getText().toString().isEmpty())
			articlePB.setVisibility(View.GONE);
		else
			articlePB.setVisibility(View.VISIBLE);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Article = new NewsItem();
		articleapi = getResources().getString(R.string.ArticleAPI);
		Article.NewsTitle = getArguments().getString("newstitle");
		Article.NewsImgLink = getArguments().getString("newsimagelink");
		Article.NewsId = getArguments().getString("newsid");
		Article.NewsDateString = getArguments().getString("newsdate");

		mLoadconfig = new ImageLoaderConfiguration.Builder(getActivity()
				.getApplicationContext()).build();
		mloadImage = ImageLoader.getInstance();
		mloadImage.init(mLoadconfig);
		loadArticleBody = new NewsLoader();
		loadArticleBody.loadSection(articleapi + Article.NewsId, this, 1);
		commadapter = new commAdapter();
		comments = new ArrayList<Comment>();
		commInflater = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		commLoader = new CommLoader();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)

			commLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			commLoader.execute();
		setRetainInstance(true);

	}

	@Override
	public void OnTaskCompleted(ArrayList<NewsItem> result, int taskID) {
		// TODO Auto-generated method stub
        
		if (result == null || result.size() == 0)
			loadArticleBody.loadSection(articleapi + Article.NewsId, this, 1);
		else {
			Article = result.get(0);

			newscontent.setText((Article.NewsContent).trim());
			newsdate.setText(Article.NewsDateString);
			newswriter.setText(Article.newsWriter);
			
			try {
				mloadImage.displayImage(Article.NewsImgLink, (ImageView) getView()
						.findViewById(R.id.logoimg), dispoptions);
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (Article.imgLinks.size() != 0) {
				ImageView tempholder;
				singArticleInflater = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				for (int i = 0; i < Article.imgLinks.size(); i++) {

					tempholder = (ImageView) singArticleInflater.inflate(
							R.layout.newsimgstub, imgHoldeLinearLayout, false);
					mloadImage.displayImage(Article.imgLinks.get(i),
							tempholder, dispoptions);
					imgHoldeLinearLayout.addView(tempholder);
					// (((RelativeLayout)
					// (getView().findViewById(R.id.Article_holder))).addView(img,
					// LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				}
			}

			articlePB.setVisibility(View.GONE);

		}
	}

	private class CommLoader extends AsyncTask<Void, Void, Void> {

		XmlPullParserFactory mParserGenerator;
		XmlPullParser newsparser;
		URL SecApiLink;
		int eventtype;

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			initialize_parser();
			try {
				parse();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		private void initialize_parser() {
			try {
				SecApiLink = new URL(getActivity().getResources()
						.getString(R.string.getcomments)
						.replace("newsidd", Article.NewsId));
				mParserGenerator = XmlPullParserFactory.newInstance();
				newsparser = mParserGenerator.newPullParser();
				newsparser
						.setFeature(
								org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES,
								false);
				newsparser.setInput(SecApiLink.openStream(), "utf-8");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.cancel(true);
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// this.cancel(true);
			}
		}

		private void parse() throws XmlPullParserException, IOException {
			Comment temp = new Comment();
			eventtype = newsparser.getEventType();
			while (eventtype != XmlPullParser.END_DOCUMENT) {

				switch (eventtype) {

				case XmlPullParser.START_TAG:
					String tagName = newsparser.getName();
					if (tagName.equalsIgnoreCase("comment")) {
						temp = new Comment();
					} else if (tagName.equalsIgnoreCase("title")) {
						temp.comTitle = newsparser.nextText();
					} else if (tagName.equalsIgnoreCase("poster")) {
						temp.comwriter = newsparser.nextText();
					} else if (tagName.equalsIgnoreCase("date")) {
						temp.comDate = newsparser.nextText();
					} else if (tagName.equalsIgnoreCase("body")) {
						temp.comContent = newsparser.nextText();
						comments.add(temp);
					}
				}
				eventtype = newsparser.next();
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			commadapter.notifyDataSetChanged();
			if (commLV.isGroupExpanded(1))
				setListViewHeightBasedOnChildren(commLV,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_SP, 20, getResources()
										.getDisplayMetrics()));

		}

	}

	private class commAdapter extends BaseExpandableListAdapter {

		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return arg1;
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return arg1;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				RelativeLayout commBlock = (RelativeLayout) commInflater
						.inflate(R.layout.comments_readblock, parent, false);
				((TextView) commBlock.findViewById(R.id.com_title))
						.setText(comments.get(childPosition).comTitle);
				((TextView) commBlock.findViewById(R.id.com_date))
						.setText(comments.get(childPosition).comDate);
				((TextView) commBlock.findViewById(R.id.com_writer))
						.setText(comments.get(childPosition).comwriter);
				((TextView) commBlock.findViewById(R.id.com_content))
						.setText(comments.get(childPosition).comContent);
				 ((TextView)commBlock.findViewById(R.id.com_num)).setText(Integer.toString(comments.size()-childPosition));
				return commBlock;
			} else {
				RelativeLayout commBlock = (RelativeLayout) convertView;
				((TextView) commBlock.findViewById(R.id.com_title))
						.setText(comments.get(childPosition).comTitle);
				((TextView) commBlock.findViewById(R.id.com_date))
						.setText(comments.get(childPosition).comDate);
				((TextView) commBlock.findViewById(R.id.com_writer))
						.setText(comments.get(childPosition).comwriter);
				((TextView) commBlock.findViewById(R.id.com_content))
						.setText(comments.get(childPosition).comContent);
				 ((TextView)commBlock.findViewById(R.id.com_num)).setText(Integer.toString(comments.size()-childPosition));
				return commBlock;

			}
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			if (comments != null)
				return comments.size();
			return 0;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = (Youm7TextView) commInflater.inflate(
						R.layout.comments_header, parent, false);
				return convertView;
			} else
				return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	public static void setListViewHeightBasedOnChildren(
			ExpandableListView listView, int extraheight) {
		ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.AT_MOST);
		for (int i = 0; i < listAdapter.getChildrenCount(1); i++) {
			View listItem = listAdapter.getChildView(1, i, false, null,
					listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}
		((View) (listAdapter.getGroupView(1, false, null, listView))).measure(
				desiredWidth, MeasureSpec.UNSPECIFIED);
		totalHeight += ((View) (listAdapter.getGroupView(1, false, null,
				listView))).getHeight();
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter
						.getChildrenCount(1) - 1)) + extraheight;

		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	private class postComment extends AsyncTask<Void, Void, Void>
	{
     boolean success;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			postData() ;
			return null;
		}
		public void postData() {
		    // Create a new HttpClient and Post Header
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(getResources().getString(R.string.postcomment));

		    try {
		        // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("NewsID", Article.NewsId));
		        nameValuePairs.add(new BasicNameValuePair("UserName",name.getText().toString() ));
		        nameValuePairs.add(new BasicNameValuePair("Email", email.getText().toString()));
		        nameValuePairs.add(new BasicNameValuePair("Title", comTitle.getText().toString()));
		        nameValuePairs.add(new BasicNameValuePair("Message", comBody.getText().toString()));
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        success=true;
		     
		        
		    } catch (ClientProtocolException e) {
		    	success=false;
		    	Toast.makeText(getActivity().getApplicationContext(), "لم نتمكن من ارسال تعليقك,برجاء المحاولة مرة اخرى",Toast.LENGTH_LONG).show();
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		    	success=false;
		    	Toast.makeText(getActivity().getApplicationContext(), "لم نتمكن من ارسال تعليقك,برجاء المحاولة مرة اخرى", Toast.LENGTH_LONG).show();
		        // TODO Auto-generated catch block
		    }
		} 
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(success)
			{
				 Toast.makeText(getActivity().getApplicationContext(), "لقد تم إرسال رسالتك بنجاح وسيتم مراجعتها من قبل الموقع", Toast.LENGTH_LONG).show();
				 postcomment.setVisibility(View.GONE);
				 
			}
		}
	}
}
