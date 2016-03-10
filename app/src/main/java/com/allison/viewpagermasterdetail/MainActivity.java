package com.allison.viewpagermasterdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by CJH on 1/29/2016.
 */
public class MainActivity extends AppCompatActivity implements Bridge{

    private ViewPager viewPager = null;
    private MyAdapter mAdapter;
    PageChangeListener mListener = new PageChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOnPageChangeListener(mListener);       // Page Change Listener

        mAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (mListener.getCurrentPage()==2 && mAdapter.mFragment instanceof ItemOneDetailFragment) {     // current page is Tab-3, current fragment is detail fragment
            mAdapter.mListener.onSwitchToNextFragment("0");     // show List fragment
            return;     // prevent to finish app.
        }

        super.onBackPressed();
    }

    // Do the same thing as the back button - go back to ItemListFragment
    // Only when in ItemOneDetailFragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (mListener.getCurrentPage() == 2 && mAdapter.mFragment instanceof ItemOneDetailFragment) {     // current page is Tab-3, current fragment is detail fragment
                mAdapter.mListener.onSwitchToNextFragment("0");     // show List fragment
//                return;     // prevent to finish app.
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBack() {
        onBackPressed();
    }
}

/**
 * Fragment Page Change Listener
 */
class PageChangeListener extends ViewPager.SimpleOnPageChangeListener {
    private int currentPage;

    @Override
    public void onPageSelected(int position) {
        currentPage = position;     // current selected page
    }

    /**
     * Get current selected page.
     * @return page index
     */
    public final int getCurrentPage() {
        return currentPage;
    }
}

/**
 * Fragment Page Adapter
 */
class MyAdapter extends FragmentPagerAdapter{

    private final FragmentManager mFragmentManager;
    public BaseFragment mFragment;

    /**
     *  PageFragmentListener for switching fragment.
     */
    public PageFragmentListener mListener = new PageFragmentListener() {
        @Override
        public void onSwitchToNextFragment(final String id) {
            mFragmentManager.beginTransaction().remove(mFragment).commit();
            if (mFragment instanceof ItemListFragment){     // current fragment is List Fragment
                Bundle arguments = new Bundle();
                arguments.putString(Constants.ARG_ITEM_ID, id);     // selected item id
                mFragment = ItemOneDetailFragment.newInstance(mListener);       // switch detail fragment
                mFragment.setArguments(arguments);
            }else{      // DetailFragment
                mFragment = ItemListFragment.newInstance(mListener);    // => switch list fragment
            }

            notifyDataSetChanged();     // notify changes
        }
    };

    public MyAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)      // Tab-1
            return FragmentA.newInstance();

        if (position == 1)      // Tab-2
            return FragmentB.newInstance();

        if (position == 2) {    // Tab-3
            if (mFragment==null)    // first time => create list fragment
                mFragment = ItemListFragment.newInstance(mListener);

            return mFragment;
        }

        return null;
    }

    /**
     * To set tab title.
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {    // Tab-1
            return "Tab 1";
        }
        if (position == 1) {    // Tab-2
            return "Tab 2";
        }
    if (position == 2) {        //Tab-3
            return "Tab 3";
        }
        return null;
    }

    @Override
    public int getCount() {     // Count of Tabs
        return 3;
    }

    @Override
    public int getItemPosition(Object object) {
        Log.i("Adapter", "ItemPosition>>>" + object.toString());
        if (object instanceof ItemListFragment && mFragment instanceof ItemOneDetailFragment) {     //  fragment changed
            return POSITION_NONE;
        }

        if (object instanceof ItemOneDetailFragment && mFragment instanceof ItemListFragment) {     // fragment changed
            return POSITION_NONE;
        }

        return POSITION_UNCHANGED;
    }
}
