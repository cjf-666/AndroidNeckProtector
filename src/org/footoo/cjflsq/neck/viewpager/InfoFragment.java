package org.footoo.cjflsq.neck.viewpager;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.footoo.cjflsq.neck.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class InfoFragment extends Fragment {
	public static final String TYPE = "type";

	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

	private XYSeries mCurrentSeries;

	private XYSeriesRenderer mCurrentRenderer;

	private String mDateFormat;

	private GraphicalView mChartView;

	private int index = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.info, container, false);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
		mRenderer.setAxisTitleTextSize(16);
		mRenderer.setChartTitleTextSize(20);
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setPointSize(10);
		XYSeries series = new XYSeries("1111test");
		mDataset.addSeries(series);
		mCurrentSeries = series;
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(0xFF019cfe);
		mRenderer.addSeriesRenderer(renderer);
		renderer.setPointStyle(PointStyle.CIRCLE);
		renderer.setFillPoints(true);
		mCurrentRenderer = renderer;
		mCurrentSeries.add(1, 2);
		mCurrentSeries.add(3, 4);
		mCurrentSeries.add(4, 1);
		LinearLayout layout = (LinearLayout)v.findViewById(R.id.chart);
		mChartView = ChartFactory.getLineChartView(container.getContext(), mDataset, mRenderer);
		mRenderer.setClickEnabled(true);
		mRenderer.setSelectableBuffer(100);
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
		          LayoutParams.FILL_PARENT));
		mChartView.repaint();
		return v;
	}
}
