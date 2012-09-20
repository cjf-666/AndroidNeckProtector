package org.footoo.cjflsq.neck;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.*;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.footoo.cjflsq.neck.database.DataManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

	Button rtBtn = (Button) findViewById(R.id.ct_return_button);
	rtBtn.setOnClickListener(new RTOnClickListener());
        LayoutInflater inflater = LayoutInflater.from(StatisticsActivity.this);
        View layout = inflater.inflate(R.layout.popup, null);
        popupWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(0, 50, 50, 50));
        mRenderer.setAxisTitleTextSize(16);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[]{20, 30, 15, 0});
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setPointSize(5);
        XYSeries series = new XYSeries("历史得分");
        mDataset.addSeries(series);
        mCurrentSeries = series;

        mRenderer.setXLabels(0);
        Calendar c = new GregorianCalendar();
        Time time = new Time();
        for (int i = 20; i > 0; i--) {
            time.year = c.get(GregorianCalendar.YEAR);
            time.month = c.get(GregorianCalendar.MONTH);
            time.monthDay = c.get(GregorianCalendar.DAY_OF_MONTH);
            mCurrentSeries.add(i, DataManager.getInstance().getScore(time));
            mRenderer.addXTextLabel(i, String.valueOf(time.monthDay));
            c.add(GregorianCalendar.DAY_OF_MONTH, -1);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_statistics, menu);
        return true;
    }

    private class RTOnClickListener implements OnClickListener {
	@Override
	    public void onClick(View v) {
	    StatisticsActivity.this.finish();
	}
    }

    private class ChartViewListener implements OnClickListener, OnTouchListener {
        boolean moved = false;
        float x;
        float y;

        @Override
        public void onClick(View v) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                return;
            }
            SeriesSelection seriesSelection = mChartView
                    .getCurrentSeriesAndPoint();
            if (seriesSelection != null) {
                final int[] location = new int[2];
                mChartView.getLocationOnScreen(location);

                Calendar c = new GregorianCalendar();
                LayoutInflater inflater = LayoutInflater.from(StatisticsActivity.this);
                View layout = inflater.inflate(R.layout.popup, null);
                TextView tvScore = (TextView) layout.findViewById(R.id.score);
                TextView tvTime = (TextView) layout.findViewById(R.id.time);

                c.add(GregorianCalendar.DAY_OF_MONTH, seriesSelection.getPointIndex() - 20);
                Time time = new Time();
                time.year = c.get(GregorianCalendar.YEAR);
                time.month = c.get(GregorianCalendar.MONTH);
                time.monthDay = c.get(GregorianCalendar.DAY_OF_MONTH);

                tvScore.setText("当日得分：" + DataManager.getInstance().getScore(time));
                tvTime.setText("当日使用手机时长：" + DataManager.getInstance().getTime(time));
                popupWindow = new PopupWindow(layout, LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
                popupWindow.showAtLocation(mChartView, Gravity.LEFT | Gravity.TOP, location[0] + (mChartView.getWidth() - 272) / 2, location[1] - 50);
                popupWindow.update(272, 109);
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
