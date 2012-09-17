package org.footoo.cjflsq.neck.settings;

import android.widget.Button;
import android.widget.ImageView;

public class SizeCallBackForSetting implements SizeCallBack {

    private Button scrollBtn;
    private int seekBarWidth;
	
	
    public SizeCallBackForSetting(Button btn){
	super();
	scrollBtn = btn;
    }

    @Override
    public void onGlobalLayout() {
	seekBarWidth = scrollBtn.getMeasuredWidth();
    }

    @Override
    public void getViewSize(int idx, int width, int height, int[] dims) {
	dims[0] = width;
	dims[1] = height;

	/*视图不是中间视图*/
	if(idx != 1) {
	    dims[0] = width - seekBarWidth;
	}
    }

}
