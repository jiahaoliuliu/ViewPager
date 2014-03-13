package com.jiahaoliuliu.viewpager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewPagerActivity extends FragmentActivity {

	private static final String LOG_TAG = ViewPagerActivity.class.getSimpleName();

	/**
	 * The number of pages (wizard steps) to show in this demo.
	 */
	private static final int NUM_PAGES = 3;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally to access previous
	 * and next wizard steps.
	 */
	private ViewPager mPager;

	TimerTask task;

	ImageView indicator;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager_layout);

		indicator = (ImageView) findViewById(R.id.indicator);

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("primera posicion");
		for (int i = 0; i < NUM_PAGES;++i) {
			list.add("Hola, soy la posicion "+i);
		}
		list.add("ultima posicion");
		
		mPagerAdapter = new CircularPagerAdapter(list);
		mPager.setAdapter(mPagerAdapter);
		mPager.setOffscreenPageLimit(3);
		mPager.setCurrentItem(1,false);

		try {
			Field mScroller;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true); 
			FixedSpeedScroller scroller = new FixedSpeedScroller(mPager.getContext());
			scroller.setFixedDuration(1000);
			mScroller.set(mPager, scroller);
		} catch (Exception e) {
		} 


		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				if (position == 0) {
					position = NUM_PAGES;
				} else if (position == NUM_PAGES+1){
					position = 1;
				}
				int indexDrawable = position-1;
				int id = getResources().getIdentifier("paginador0"+indexDrawable, "drawable", getPackageName());
				indicator.setImageDrawable(getResources().getDrawable(id));
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

			@Override
			public void onPageScrollStateChanged(int state) { 

				if (state == ViewPager.SCROLL_STATE_IDLE) {
					int position = mPager.getCurrentItem();
					if (position == 0) {
						position = NUM_PAGES;
						mPager.setCurrentItem(position,false);
					} else if (position == NUM_PAGES+1) {
						position = 1;
						mPager.setCurrentItem(position,false);
					}
				}
			}
		});

		/*
		// Creates the timer to automatically scroll the view pager.
		task = new TimerTask() {
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						boolean animation = true;
						int position = mPager.getCurrentItem()+1;
						mPager.setCurrentItem(position,animation);
					}
				});
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 3000, 3000);

		// Cancel the timer if the user has clicked on the viewPager
		mPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				task.cancel();
				return false;
			}
		});
		*/
	}

	private class CircularPagerAdapter extends PagerAdapter {

		private ArrayList<String> data;
		private int currentPos;

		public CircularPagerAdapter(ArrayList<String> list) {
			super();
			data = list;
			currentPos = -1;
		}

		public int getCount() {
			return data.size();
		}

		public Object instantiateItem(ViewGroup container, int position) {
				LinearLayout root = new LinearLayout(ViewPagerActivity.this);
				TextView text = new TextView(ViewPagerActivity.this);
				text.setGravity(Gravity.CENTER);
				int index = position;
				if (position == 0){
					index = NUM_PAGES;
				} else if (position == NUM_PAGES+1){
					index = 1;
				}
				currentPos = index;
				text.setText(data.get(index));
				text.setTextColor(Color.WHITE);
				root.addView(text);
				((ViewPager) container).addView(root, 0);
				return root; 
		}

		@Override
		public void destroyItem(View container, int position, Object object) { ((ViewPager) container).removeView((View) object); }
		
		@Override
		public void finishUpdate(View container) { }
		@Override
		public boolean isViewFromObject(View view, Object object) { return view == ((View) object); }
		@Override
		public void restoreState(Parcelable state, ClassLoader loader) { }
		@Override
		public Parcelable saveState() {  return null; }
		@Override
		public void startUpdate(View container) { }
	}

}
