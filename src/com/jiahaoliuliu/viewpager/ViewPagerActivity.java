package com.jiahaoliuliu.viewpager;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class ViewPagerActivity extends FragmentActivity {
	
	private static final String LOG_TAG = ViewPagerActivity.class.getSimpleName();
	
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;
    
    private int currentPage;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager_layout);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
              currentPage = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
              // not needed
            }

            @Override
            public void onPageScrollStateChanged(int state) {
              if (state == ViewPager.SCROLL_STATE_IDLE) {
              	Log.v(LOG_TAG, "Page Scroll state started. The actual page number is " + currentPage);

              	// If it is in the first page
                if (currentPage == 0){
                	mPager.setCurrentItem(NUM_PAGES - 2, false);
                } else if (currentPage == NUM_PAGES - 1){
                	mPager.setCurrentItem(1, false);
                }
              }
            }
          });

        //Bind the title indicator to the adapter
        //CirclePageIndicator circlePageIndicator = (CirclePageIndicator)findViewById(R.id.circles);
        //circlePageIndicator.setViewPager(mPager);
        
	}

    /**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
