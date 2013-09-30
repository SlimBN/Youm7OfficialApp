package com.youm7.newsapp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.R.layout;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Comments extends DialogFragment {
	LinearLayout commholder;
	ExpandableListView commLV;
	commAdapter commadapter;
	ArrayList<Comment> comments;
	LayoutInflater commInflater;
	CommLoader commLoader;
	String URL;
@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
	commadapter=new commAdapter();
	comments=new ArrayList<Comment>();
	commInflater= (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	commLoader =new CommLoader();
	URL=getArguments().getString("url");
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		
		commLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
	    commLoader.execute();
	setRetainInstance(true);
}@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	LinearLayout commholder=(LinearLayout) inflater.inflate(R.layout.comments_layout,container,false);
    ((TextView)commholder.findViewById(R.id.commentstitle)).setText(this.getArguments().getString("newstitle"));
		commLV= (ExpandableListView) commholder.findViewById(R.id.commLV);
		if(commadapter==null)
			commadapter=new commAdapter();
		
		commLV.setAdapter(commadapter);
		
		return commholder;
	}
private class CommLoader extends AsyncTask<Void, Void, Void>
{
	 
	XmlPullParserFactory mParserGenerator;
     XmlPullParser newsparser;
     URL SecApiLink;
     int eventtype;
    
	@Override
	protected Void doInBackground(Void... params)  {
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
	
	private void initialize_parser()
	{
		try {
			SecApiLink = new URL(URL);
			 mParserGenerator = XmlPullParserFactory.newInstance();
			 newsparser = mParserGenerator.newPullParser();
			newsparser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			newsparser.setInput(SecApiLink.openStream(),"utf-8");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//this.cancel(true);
		}
	}
	private void parse() throws XmlPullParserException, IOException 
	{
		Comment temp=new Comment();
		eventtype=newsparser.getEventType();
		while (eventtype != XmlPullParser.END_DOCUMENT){
			
			
			switch(eventtype){
			
			case XmlPullParser.START_TAG:
			String tagName=newsparser.getName();
			if(tagName.equalsIgnoreCase("comment"))
			{
				temp=new Comment();
			}
			else if(tagName.equalsIgnoreCase("title"))
			{
				temp.comTitle=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("poster"))
			{
				temp.comwriter=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("date"))
			{
				temp.comDate=newsparser.nextText();
			}
			else if(tagName.equalsIgnoreCase("body"))
			{
				temp.comContent=newsparser.nextText();
				comments.add(temp);
			}
			}
			eventtype=newsparser.next();
		}
	}

@Override
protected void onPostExecute(Void result) {
	// TODO Auto-generated method stub
	super.onPostExecute(result);
	doneloading();
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
		if(convertView==null)
		{
			RelativeLayout commBlock=(RelativeLayout) commInflater.inflate(R.layout.comments_readblock, parent,false);
			((TextView)commBlock.findViewById(R.id.com_title)).setText(comments.get(childPosition).comTitle);
			((TextView)commBlock.findViewById(R.id.com_date)).setText(comments.get(childPosition).comDate);
			((TextView)commBlock.findViewById(R.id.com_writer)).setText(comments.get(childPosition).comwriter);
			((TextView)commBlock.findViewById(R.id.com_content)).setText(comments.get(childPosition).comContent);
			//((TextView)commBlock.findViewById(R.id.com_num)).setText(comments.size()-childPosition);
			return commBlock;
		}
		else
		{
			RelativeLayout commBlock=(RelativeLayout) convertView;
			((TextView)commBlock.findViewById(R.id.com_title)).setText(comments.get(childPosition).comTitle);
			((TextView)commBlock.findViewById(R.id.com_date)).setText(comments.get(childPosition).comDate);
			((TextView)commBlock.findViewById(R.id.com_writer)).setText(comments.get(childPosition).comwriter);
			((TextView)commBlock.findViewById(R.id.com_content)).setText(comments.get(childPosition).comContent);
			//((TextView)commBlock.findViewById(R.id.com_num)).setText(comments.size()-childPosition);
			return commBlock;
			
		}
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (comments!=null)
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
		if(convertView==null)
		{
			convertView=(Youm7TextView) commInflater.inflate(R.layout.drawer_header, parent,false);
			return convertView;
		}
		else
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
private void doneloading()
{
	commadapter.notifyDataSetChanged();
}
}
