package org.footoo.cjflsq.neck.welcomepage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ImageView;
import android.widget.PopupWindow;
import org.footoo.cjflsq.neck.R;
import org.footoo.cjflsq.neck.gallery.GalleryFlow;
import org.footoo.cjflsq.neck.gallery.ImageAdapter;

public class WelcomeFragment1 extends Fragment {
    PopupWindow mPopupWindow = null;
    ImageView mImageView = null;
    View[] welcome_view = new View[5];

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);    //To change body of overridden methods use File | Settings | File Templates.

    }

    @Override
    public void onResume() {
        super.onResume();
        final int[] location = new int[2];
        mImageView.getLocationOnScreen(location);
        //mPopupWindow = new PopupWindow(welcome_view[0], mImageView.getWidth() * 2, mImageView.getHeight() + 20);
        mPopupWindow = new PopupWindow(welcome_view[0], WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAtLocation(mImageView, Gravity.LEFT | Gravity.TOP, location[0], location[1]);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        mImageView = (ImageView) v.findViewById(R.id.score_image_1);
        setImageView(mImageView, 9);
        mImageView = (ImageView) v.findViewById(R.id.score_image_0);
        setImageView(mImageView, 9);

        Integer[] images = {R.drawable.guide1, R.drawable.guide1,
                R.drawable.guide1, R.drawable.guide1, R.drawable.guide1};

        ImageAdapter adapter = new ImageAdapter(getActivity(), images);
        adapter.createReflectedImages();//创建倒影效果
        GalleryFlow galleryFlow = (GalleryFlow) v.findViewById(R.id.gallery);
        galleryFlow.setFadingEdgeLength(0);
        galleryFlow.setSpacing(-50); //图片之间的间距
        galleryFlow.setAdapter(adapter);

        galleryFlow.setSelection(2);

        welcome_view[0] = inflater.inflate(R.layout.welcome_item1, container, false);
        welcome_view[1] = inflater.inflate(R.layout.welcome_item2, container, false);
        welcome_view[2] = inflater.inflate(R.layout.welcome_item3, container, false);
        welcome_view[3] = inflater.inflate(R.layout.welcome_item4, container, false);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().setAttributes(lp);
    }



    private void setImageView(ImageView imv, int num) {
        switch (num) {
            case 0:
                imv.setImageResource(R.drawable.green_0);
                break;
            case 1:
                imv.setImageResource(R.drawable.green_1);
                break;
            case 2:
                imv.setImageResource(R.drawable.green_2);
                break;
            case 3:
                imv.setImageResource(R.drawable.green_3);
                break;
            case 4:
                imv.setImageResource(R.drawable.green_4);
                break;
            case 5:
                imv.setImageResource(R.drawable.green_5);
                break;
            case 6:
                imv.setImageResource(R.drawable.green_6);
                break;
            case 7:
                imv.setImageResource(R.drawable.green_7);
                break;
            case 8:
                imv.setImageResource(R.drawable.green_8);
                break;
            case 9:
                imv.setImageResource(R.drawable.green_9);
                break;
        }
    }

}
