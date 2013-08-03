package com.youm7.newsapp;

import android.support.v4.app.Fragment;



public interface OnArticleSelectedListener{
	public void OnArticleSelected(Fragment frag, Fragment LeavingFrsg, boolean AddToBS);
	public void OnHomeUpdateFinished();
}