package com.youm7.newsapp;

import android.support.v4.app.Fragment;



public interface OnArticleSelectedListener{
	public void OnHomeArticleSelected(Fragment frag, Fragment LeavingFrsg, boolean AddToBS);
	public void OnHomeUpdateFinished();
	public void onSecArticleSelected(Fragment frag, String fragid, boolean AddToBS);
}