package com.example.nayon.nanjicamp.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.nayon.nanjicamp.MainActivity;
import com.example.nayon.nanjicamp.R;
import com.example.nayon.nanjicamp.adapter.CustomPagerAdapterPrepare;
import com.example.nayon.nanjicamp.adapter.CustomPagerAdapterReserve3;
import com.example.nayon.nanjicamp.custom_fragment.CustomFragmentPrepare;
import com.example.nayon.nanjicamp.data.Manager;

/**
 * Created by nayon on 2017-05-02.
 */
public class FragmentPrepare2 extends Fragment {

    public CustomPagerAdapterPrepare mAdapter;
    public ViewPager mViewPager;
    public LinearLayout indicator;
    private int dotCount;
    private ImageView[] dots;
    private int mCurrentPosition;
    CustomFragmentPrepare fragment;
    CustomFragmentPrepare fragmentBefore;
    CustomFragmentPrepare fragmentAfter;

    public final static int PAGES = 3;
    public final static int FIRST_PAGE = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    public View onCreateView(final LayoutInflater inflater, final ViewGroup parentViewGroup, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_prepare_2, parentViewGroup, false);

        LinearLayout btn1 = (LinearLayout)rootView.findViewById(R.id.btn_prepare_1);
        LinearLayout btn2 = (LinearLayout)rootView.findViewById(R.id.btn_prepare_2);
        LinearLayout btn3 = (LinearLayout)rootView.findViewById(R.id.btn_prepare_3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).transFragment(Manager.FRAGMENT_PREPARE_1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).transFragment(Manager.FRAGMENT_PREPARE_2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).transFragment(Manager.FRAGMENT_PREPARE_3);
            }
        });


       /*ViewPager*/
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_prepare_2);
        indicator = (LinearLayout) rootView.findViewById(R.id.indicator_prepare_2);

        //getSupportFragmentManager -> getChildFragmentManager
        mAdapter = new CustomPagerAdapterPrepare(this.getActivity(), this.getChildFragmentManager(), Manager.FRAGMENT_PREPARE_2);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(false, mAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        mViewPager.setCurrentItem(FIRST_PAGE);

        // Necessary or the mViewPager will only have one extra page to show
        // make this at least however many pages you can see
        mViewPager.setOffscreenPageLimit(3);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        mViewPager.setPageMargin(-500);

        dotCount = mAdapter.getCount();
        dots = new ImageView[dotCount];

        for (int i = 0; i < dotCount; i++) {
            dots[i] = new ImageView(this.getActivity());
            dots[i].setImageResource(R.drawable.icon_indicator);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(38, 38);
            params.setMargins(14, 0, 14, 0);
            indicator.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.icon_indicator_selected);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotCount; i++) {
                    dots[i].setImageResource(R.drawable.icon_indicator);
                }
                dots[position].setImageResource(R.drawable.icon_indicator_selected);

                /*get seleceted fragment*/
                mCurrentPosition = position;
                fragment = mAdapter.getRegisteredFragment(mCurrentPosition);
                fragmentAfter = mAdapter.getRegisteredFragment(mCurrentPosition-1);
                if(fragmentAfter!=null){
                    fragmentAfter.videoView.setSeekTo(true);
                }
                fragmentBefore = mAdapter.getRegisteredFragment(mCurrentPosition+1);
                if(fragmentBefore!=null){
                    fragmentBefore.videoView.setSeekTo(true);
                }
                if (fragment == null) {
                    return;
                }
                fragment.videoView.setSeekTo(false);


//                for (int i = 0; i < dotCount; i++) {
//                    dots[i].setImageResource(R.drawable.icon_indicator);
//                }
//                dots[position].setImageResource(R.drawable.icon_indicator_selected);
//
//                /*get seleceted fragment*/
//                mCurrentPosition = position;
//                fragment = mAdapter.getRegisteredFragment(mCurrentPosition);
//                fragmentAfter = mAdapter.getRegisteredFragment(mCurrentPosition-1);
//                if(fragmentAfter!=null){
//                    fragmentAfter.videoView.pause();
//                }
//                fragmentBefore = mAdapter.getRegisteredFragment(mCurrentPosition+1);
//                if(fragmentBefore!=null){
//                    fragmentBefore.videoView.pause();
//                }
//
//                if (fragment == null) {
//                    return;
//                }
//                fragment.videoView.start();
//                //proba
//                fragment.videoView.setOnCompletionListener(((new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        fragment.videoView.start();
//                        mp.setLooping(true);
//                    }
//                })));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return rootView;
    }
}