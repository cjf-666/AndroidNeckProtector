package org.footoo.cjflsq.neck;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.footoo.cjflsq.neck.database.DataManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class StatisticsDialog extends Dialog {

	private Activity activity;
	public StatisticsDialog(Context context) {
		super(context);
		activity = (Activity)context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.setContentView(R.layout.popup);
		
		 LayoutInflater inflater = LayoutInflater.from(activity);
         View layout = inflater.inflate(R.layout.popup, null);
         XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

     	XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

     	XYSeries mCurrentSeries;

     	GraphicalView mChartView;
 		mRenderer.setApplyBackgroundColor(true);
 		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
 		mRenderer.setAxisTitleTextSize(16);
 		mRenderer.setChartTitleTextSize(20);
 		mRenderer.setLabelsTextSize(15);
 		mRenderer.setLegendTextSize(15);
 		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
 		mRenderer.setZoomButtonsVisible(true);
 		mRenderer.setPointSize(10);
 		XYSeries series = new XYSeries("今日各时段使用手机时间");
 		mDataset.addSeries(series);
 		mCurrentSeries = series;

 		
 		ContentValues cv = DataManager.getInstance().getStatOfToday();
 		mRenderer.setXLabels(0);
 		long max = 0;
 		for (int i = 0; i < 24; i+=2){
 			Long j = new Long(0L);
 			try{
 				j = cv.getAsLong("z" + i + "_" + (i+2));
 			}catch(Exception e){
 				
 			}
 			if (j > max)
 				max = j;
 			mCurrentSeries.add(i, j);
 			mRenderer.addXTextLabel(i, i + "-" + (i+2));
 		}
 		
 		mRenderer.setXLabelsColor(0xFF000000);
 		mRenderer.setYLabelsColor(0, 0xFF000000);
 		mRenderer.setYAxisMax(max + 5);
 		
 		XYSeriesRenderer renderer = new XYSeriesRenderer();
 		renderer.setColor(0xFF019cfe);
 		mRenderer.addSeriesRenderer(renderer);
 		mRenderer.setMarginsColor(0xFFFFFFFF);
 		renderer.setPointStyle(PointStyle.DIAMOND);
 		renderer.setFillPoints(true);
 		
 		
 		LinearLayout layout1 = (LinearLayout) layout.findViewById(R.id.chart);
 		mChartView = ChartFactory.getLineChartView(activity.getApplicationContext(), mDataset, mRenderer);
 		mRenderer.setClickEnabled(true);
 		mRenderer.setSelectableBuffer(100);
 		layout1.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
 		          LayoutParams.WRAP_CONTENT));
 		
 		 Window dialogWindow = getWindow();
         WindowManager.LayoutParams lp = dialogWindow.getAttributes();
         dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
         
         final int[] location = new int[2]; 
         mChartView.getLocationOnScreen(location);

         lp.x = location[0]; // 新位置X坐标
         lp.y = location[1]; // 新位置Y坐标
         lp.width = mChartView.getWidth(); // 宽度
         lp.height = mChartView.getHeight(); // 高度
         lp.alpha = 0.7f; // 透明度

         // 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
         // dialog.onWindowAttributesChanged(lp);
         dialogWindow.setAttributes(lp);
	}


}
