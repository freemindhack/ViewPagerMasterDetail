package com.allison.viewpagermasterdetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by CJH on 1/29/2016.
 */
public class MainActivity extends AppCompatActivity implements Bridge{
    static final String TAG = "MainActivity";
    private ViewPager viewPager = null;
    private MyAdapter mAdapter;
    private int currentPage;
    //
    private Toolbar toolbar;
    private TabLayout tabLayout;


    ViewPager.OnPageChangeListener mListener = new ViewPager.SimpleOnPageChangeListener(){
        static final String TAG = "PageChangeListener";

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected");
            super.onPageSelected(position);
            currentPage = position;     // current selected page
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    // Keep a reference to listener, need to access this from fragment
    PageFragmentListener mPageFragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.addOnPageChangeListener(mListener);       // Page Change Listener

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mPageFragmentListener = mAdapter.mListener;         // Set the listener
        viewPager.setAdapter(mAdapter);

        //
        toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onBackPressed() {
        if (currentPage==2 && mAdapter.mFragment instanceof ItemOneDetailFragment) {     // current page is Tab-3, current fragment is detail fragment
            Log.d(TAG, "onBackPressed(): currentPage="+currentPage);
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
            if (currentPage == 2 && mAdapter.mFragment instanceof ItemOneDetailFragment) {     // current page is Tab-3, current fragment is detail fragment
                Log.d(TAG, "onOptionsItemsSelected(): currentPage="+currentPage);
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
 * Fragment Page Adapter
 */
class MyAdapter extends FragmentStatePagerAdapter {
    static final String TAG = "MyAdapter";
    private final FragmentManager mFragmentManager;
    public BaseFragment mFragment;

    /**
     *  PageFragmentListener for switching fragment.
     */
    public PageFragmentListener mListener = new PageFragmentListener() {
        @Override
        public void onSwitchToNextFragment(final String id) {
            Log.d(TAG, "remove");
            mFragmentManager.beginTransaction().remove(mFragment).commit();
            Log.d(TAG, "if instance");
            if (mFragment instanceof ItemListFragment){     // current fragment is List Fragment
                Log.d(TAG, "new bundle");
                Bundle arguments = new Bundle();
                Log.d(TAG, "arguments");
                arguments.putString(Constants.ARG_ITEM_ID, id);     // selected item id
                Log.d(TAG, "ItemOneDetailFragment.newInstance");
                mFragment = ItemOneDetailFragment.newInstance(mListener);       // switch detail fragment
                Log.d(TAG, "setARgs");
                mFragment.setArguments(arguments);
            }else{      // DetailFragment
                Log.d(TAG, "ItemListFragment.newInstance");
                mFragment = ItemListFragment.newInstance(mListener);    // => switch list fragment
            }
            Log.d(TAG, "notify");
            notifyDataSetChanged();     // notify changes
        }
    };

    public MyAdapter(FragmentManager fm) {
        super(fm);
        Log.d(TAG, "MyAdapterConstructor");
        mFragmentManager = fm;
        List<Fragment> fragments = fm.getFragments();
        if(fragments != null){
            for(Fragment f : fragments){
                if(f instanceof ItemListFragment || f instanceof ItemOneDetailFragment){
                    mFragment = (BaseFragment) f;
                }
            }
        }
     }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem()");
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
     * @param position the currently visible ViewPager fragment
     * @return the title of the fragment
     */
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle()");
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
