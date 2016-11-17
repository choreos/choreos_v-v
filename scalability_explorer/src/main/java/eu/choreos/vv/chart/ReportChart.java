package eu.choreos.vv.chart;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

import eu.choreos.vv.aggregations.AggregationFunction;
import eu.choreos.vv.data.ExperimentReport;
import eu.choreos.vv.data.PlotData;

/**
 * Creates charts to present the results obtained by batteries of scalability tests.
 * @author paulo
 *
 */
public class ReportChart extends JFrame {
	
	final static private String APPLICATION_TITLE  = "Scalability explorer";

    public void createChart(final ChartPanel panel) {
    	EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			setContentPane(panel);
    			pack();
    			setLocationRelativeTo(null);
    			setVisible(true);
    		}
    	});
    }
}
