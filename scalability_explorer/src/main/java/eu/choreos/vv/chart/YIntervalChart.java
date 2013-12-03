package eu.choreos.vv.chart;

/**
 * JFreeChartDemo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * More than 150 demo applications are included with the JFreeChart
 * Developer Guide. For more information, see:
 * 
 * JFreeChart Developer's Guide
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;

import eu.choreos.vv.data.PlotData;
import eu.choreos.vv.data.StatisticalData;


public class YIntervalChart extends JFrame {
	private static final long serialVersionUID = 1L;

	public YIntervalChart(String applicationTitle, String chartTitle, List<PlotData> reports, String xLabel, String yLabel) {
        super(applicationTitle);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();

        for (PlotData report : reports) {
            createDataset(dataset, (StatisticalData)report);
		}
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle, xLabel, yLabel);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        setContentPane(chartPanel);

    }
	
	public static ChartPanel createChart(String chartTitle, List<PlotData> reports, String xLabel, String yLabel) {
		YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();

        for (PlotData report : reports) {
            createDataset(dataset, (StatisticalData)report);
		}
		JFreeChart chart = createChart(dataset, chartTitle, xLabel, yLabel);
		ChartPanel panel = new ChartPanel(chart);
		return panel;
	}
		
    private static void createDataset(YIntervalSeriesCollection dataset, StatisticalData report) {
    	YIntervalSeries series = new YIntervalSeries(report.getName());
//    	for (int i = 0; i < report.size(); i++) {
    	for (Double x: report.keySet()) {
    		Double y = report.get(x).getMean();
    		Double sd = report.get(x).getStandardDeviation();
			series.add(x, y, y-sd, y+sd);
		}
    	dataset.addSeries(series);
    }
	
    private static JFreeChart createChart(XYDataset dataset, String chartTitle, String xLabel, String yLabel) {

        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
            chartTitle, // chart title
            xLabel, // domain axis label
            yLabel, // range axis label
            dataset,  // initial series
            PlotOrientation.VERTICAL, // orientation
            true, // include legend
            true, // tooltips?
            false // URLs?
            );

        // set chart background
        chart.setBackgroundPaint(Color.white);

        // set a few custom plot features
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);

        // set the plot's axes to display integers
        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setStandardTickUnits(ticks);
        domain.resizeRange(1.1);
        domain.setLowerBound(0.5);

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setStandardTickUnits(ticks);
        range.setUpperBound(range.getUpperBound()*1.1);
        

        // render shapes and lines
        DeviationRenderer renderer =
            new DeviationRenderer(true, true);
        plot.setRenderer(renderer);
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);

        // set the renderer's stroke
        Stroke stroke = new BasicStroke(
            3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        renderer.setBaseOutlineStroke(stroke);
        
        //TODO: render the deviation with the same color as the line
//        renderer.setBaseFillPaint(new Color(255, 200, 200));
//        renderer.setUseOutlinePaint(flag);
//        renderer.setUseFillPaint(flag);
        

        // label the points
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        XYItemLabelGenerator generator =
            new StandardXYItemLabelGenerator(
                StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT,
                format, format);
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        
        //TODO: fix the visible area to show the deviation

        return chart;
    }

}
