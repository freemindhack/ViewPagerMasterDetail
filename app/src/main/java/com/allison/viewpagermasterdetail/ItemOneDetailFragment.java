package com.allison.viewpagermasterdetail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allison.viewpagermasterdetail.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * on handsets.
 */
public class ItemOneDetailFragment extends BaseFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /*
    Listener for switch fragment
     */
    private PageFragmentListener mListener;

    /**
     * Create New Fragment.
     *
     * @param listener
     * @return
     */
    public static ItemOneDetailFragment newInstance(PageFragmentListener listener) {
        ItemOneDetailFragment fragment = new ItemOneDetailFragment();
        fragment.mListener = listener;
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemOneDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MainActivity activity = (MainActivity) getActivity();
//        ActionBar actionBar = activity.getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        if (getArguments().containsKey(Constants.ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(Constants.ARG_ITEM_ID)); // Get selected Item ID to show details
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_item_one_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {

//            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                SpannableString title = new SpannableString(" " + mItem.content);
//                title.setSpan(new ImageSpan(getResources().getDrawable(R.drawable.back)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                appBarLayout.setTitle(title);   // set title
//            }

            Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.detail_toolbar);
            toolbar.setTitle(mItem.content);
            toolbar.setNavigationIcon(R.drawable.ic_ab_back_material);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBridget.onBack();
                }
            });

            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);    // show details
        }

        return rootView;
    }

}
