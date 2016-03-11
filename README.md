# ViewPagerMasterDetail
A variant of the Master/Detail Flow template found in Android Studio. It contains only a single Activity and uses a ViewPager to manage Fragments.

Issues:

1. When fragment_item_list is first inflated the App Bar pushes the last item of the list below the bounds of the screen. This issue is no longer present after rotating the device. Simply adding padding to the bottom of the screen will not fix the issue as it will leave an unwanted space at the bottom of the screen after rotating. - Fixed

2. Requires android:configChanges="orientation|keyboardHidden|screenSize" added to the manifest in order for the Child (detail) fragment to inflate the correct layout when rotated. I'd like to not have to enforce this. PLEASE help fix this bug.

3. Uses deprecated setOnPageChangeListener and onAttach(Activity)

4. Upgrading the dependencies to 23.2.0 results in the ItemListFragment failing to inflate the correct Fragment when rotated so I can't update the libraries in the application

5. The code is probably more cumbersome than necessary
