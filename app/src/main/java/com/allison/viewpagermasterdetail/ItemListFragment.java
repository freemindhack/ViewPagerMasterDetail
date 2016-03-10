package com.allison.viewpagermasterdetail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

    private boolean mTwoPane = false;
    private PageFragmentListener mListener;

    /**
     *   Create a fragment.
     * @return ItemListFragment
     */
    public static ItemListFragment newInstance(PageFragmentListener listener) {
        ItemListFragment fragment = new ItemListFragment();
        fragment.mListener = listener;
        return fragment;
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
        View root = inflater.inflate(R.layout.fragment_item_list, container, false);
        initLayout(root);
        return root;
    }

    /**
     * Configuration Changed. (landscape or portrait)
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    /**
     *  Refresh Layout.
     *
     * @param inflater
     * @param viewGroup
     */
    private void populateViewForOrientation(LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View subview = inflater.inflate(R.layout.fragment_item_list, viewGroup);

        initLayout(subview);
    }

    /**
     * Initialize Componenents.
     *
     * @param root
     */
    public void initLayout(View root) {
        View recyclerView = root.findViewById(R.id.item_list);

        mTwoPane = false;
        if (root.findViewById(R.id.item_detail_container) != null) {        // R.layout.list_item is located "layout", "layout-land"..
            mTwoPane = true;            // currently loaded "layout-land/list_item".  landscape mode
        }

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
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
