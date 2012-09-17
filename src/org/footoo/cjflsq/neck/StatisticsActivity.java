package org.footoo.cjflsq.neck;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.footoo.cjflsq.neck.database.DataManager;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

public class StatisticsActivity extends Activity {
	public static final String TYPE = "type";

	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

	private XYSeries mCurrentSeries;

	private XYSeriesRenderer mCurrentRenderer;

	private String mDateFormat;

	private GraphicalView mChartView;

	private int index = 0;
	
	private PopupWindow popupWindow;
	
	private StatisticsDialog dialog = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        
        LayoutInflater inflater = LayoutInflater.from(StatisticsActivity.this);
        View layout = inflater.inflate(R.layout.popup, null);
        popupWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        Button retButton = (Button) findViewById(R.id.ret_button);
        retButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}});
        
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
		mCurrentRenderer = renderer;
		
		
		LinearLayout layout1 = (LinearLayout) findViewById(R.id.chart);
		mChartView = ChartFactory.getLineChartView(this.getApplicationContext(), mDataset, mRenderer);
		mRenderer.setClickEnabled(true);
		mRenderer.setSelectableBuffer(100);
		layout1.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
		          LayoutParams.WRAP_CONTENT));
		mChartView.repaint();
        
		mChartView.setOnClickListener(new View.OnClickListener() {
		       @Override
		       public void onClick(View v) {
		         SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
		         double[] xy = mChartView.toRealPoint(0);
		         if (seriesSelection == null) {
		           Toast.makeText(StatisticsActivity.this, "No chart element was clicked", Toast.LENGTH_SHORT)
		               .show();
		         } else {
		        	 final int[] location = new int[2]; 
		             mChartView.getLocationOnScreen(location);

		        	 dialog.show();

		             Window dialogWindow = dialog.getWindow();
		             WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		             dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
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
		     });
		
        dialog= new StatisticsDialog(this);
       
        }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_statistics, menu);
        return true;
    }
    
}
