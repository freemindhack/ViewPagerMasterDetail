package com.allison.viewpagermasterdetail;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allison.viewpagermasterdetail.dummy.DummyContent;

import java.util.List;

/**
 * Created by CJH on 1/29/2016.
 */
public class ItemListFragment extends BaseFragment{

    static final String TAG = "ItemListFragment";
    private boolean mTwoPane = false;
    private PageFragmentListener mListener;

    /**
     *   Create a fragment.
     * @return ItemListFragment
     */
    public static ItemListFragment newInstance(PageFragmentListener listener) {
        Log.d(TAG, "newInstance()");
        ItemListFragment fragment = new ItemListFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    /**
     * Create Layout
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View root = inflater.inflate(R.layout.fragment_item_list, container, false);
        initLayout(root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated()");
        Activity activity = getActivity();
        if (activity instanceof MainActivity){
            mListener = ((MainActivity)activity).mPageFragmentListener;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged()");
    }

    /**
     * Initialize Components.
     *
     * @param root
     */
    public void initLayout(View root) {
        Log.d(TAG, "initLayout()");
        View recyclerView = root.findViewById(R.id.item_list);

        mTwoPane = false;
        if (root.findViewById(R.id.item_detail_container) != null) {        // R.layout.list_item is located "layout", "layout-land"..
            mTwoPane = true;            // currently loaded "layout-land/list_item".  landscape mode
        }

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar2);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("List");
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    /**
     * Setup RecyclerView
     *
     * @param recyclerView
     */
    private void setupRecyclerView(RecyclerView recyclerView) {
        Log.d(TAG, "setupRecyclerView");
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    /**
     * ListAdapter for RecyclerView
     */
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        /**
         * Create List Row Layout
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            // One row of List.  define click event
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {     // landscape mode
                        Bundle arguments = new Bundle();
                        arguments.putString(Constants.ARG_ITEM_ID, holder.mItem.id);
                        ItemTwoDetailFragment fragment = ItemTwoDetailFragment.newInstance();
                        fragment.setArguments(arguments);

                        // show detail fragment to right side of screen
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();

                    } else {    // portrait mode
                        if (mListener!=null)
                            mListener.onSwitchToNextFragment(holder.mItem.id);      // switch detail fragment
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

}
