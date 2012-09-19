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
