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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

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

		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.argb(0, 50, 50, 50));
		mRenderer.setAxisTitleTextSize(16);
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(false);
		mRenderer.setPointSize(5);
		XYSeries series = new XYSeries("历史得分");
		mDataset.addSeries(series);
		mCurrentSeries = series;

		ContentValues cv = DataManager.getInstance().getStatOfToday();
		mRenderer.setXLabels(0);
		long max = 0;
		for (int i = 0; i < 24; i += 2) {
			Long j = new Long(0L);
			try {
				j = cv.getAsLong("z" + i + "_" + (i + 2));
			} catch (Exception e) {

			}

			if (j > max)
				max = j;
			mCurrentSeries.add(i, j);
			mRenderer.addXTextLabel(i, i + "-" + (i + 2));
		}

		mRenderer.setXLabelsColor(0xFF8eb94b);
		mRenderer.setYLabelsColor(0, 0xFF8eb94b);
		mRenderer.setYAxisMax(100);

		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(0xFF1c2575);
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setMarginsColor(0x00FFFFFF);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		mCurrentRenderer = renderer;

		LinearLayout layout1 = (LinearLayout) findViewById(R.id.chart);
		mChartView = ChartFactory.getLineChartView(
				this.getApplicationContext(), mDataset, mRenderer);
		mRenderer.setShowGrid(true);
		mRenderer.setGridColor(0xFF949d87);
		mRenderer.setClickEnabled(true);
		mRenderer.setSelectableBuffer(100);
		layout1.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		mChartView.repaint();

		mChartView.setOnClickListener(new ChartViewListener());
		mChartView.setOnTouchListener(new ChartViewListener());

		dialog = new StatisticsDialog(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_statistics, menu);
		return true;
	}

	private class ChartViewListener implements OnClickListener, OnTouchListener {
		boolean moved = false;
		float x;
		float y;

		@Override
		public void onClick(View v) {
			SeriesSelection seriesSelection = mChartView
					.getCurrentSeriesAndPoint();
			if (seriesSelection != null) {
				final int[] location = new int[2];
				mChartView.getLocationOnScreen(location);

				dialog.show();

				Window dialogWindow = dialog.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
				lp.x = location[0]; // 新位置X坐标
				lp.y = location[1] - 30; // 新位置Y坐标
				lp.width = mChartView.getWidth(); // 宽度
				lp.height = mChartView.getHeight() - 70; // 高度
				lp.alpha = 0.7f; // 透明度

				// 当Window的Attributes改变时系统会调用此函数,可以直接调用以应用上面对窗口参数的更改,也可以用setAttributes
				// dialog.onWindowAttributesChanged(lp);
				dialogWindow.setAttributes(lp);
			}
		}

		@Override
		public boolean onTouch(View arg0, MotionEvent event) {
			// TODO Auto-generated method stub
			int eventaction = event.getAction();
			switch (eventaction) {
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				y = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float nx = event.getX();
				if (nx - x > 20 || nx - x < -20 || moved == true) {
					moved = true;
					event.setLocation(nx, y);
					break;
				}
				return true;
			case MotionEvent.ACTION_UP:
				if (moved == true) {
					moved = false;
					return true;
				}
				break;
			}
			return false;
		}
	}
}
