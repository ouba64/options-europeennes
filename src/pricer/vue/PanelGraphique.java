package pricer.vue;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class PanelGraphique extends JPanel {

	private static final long serialVersionUID = 8767345739580491607L;

	/**
	 * Dimension du panel
	 */
	private static final Dimension PANEL_DIMENSION = new Dimension(800, 400);

	/**
	 * La série des call pour Black-Scholes
	 */
	private XYSeries xysCallBS;
	/**
	 * La série des put pour Black-Scholes
	 */
	private XYSeries xysPutBS;
	/**
	 * La série des call pour Monte-Carlo
	 */
	private XYSeries xysCallMC;
	/**
	 * La série des put pour Monte-Carlo
	 */
	private XYSeries xysPutMC;

	/**
	 * Le graphe
	 */
	private JFreeChart chart;

	/**
	 * Crée un nouveau panel comportant les graphes.
	 */
	public PanelGraphique() {
		super(new BorderLayout());

		ChartPanel chartPanel = buildChart();

		add(chartPanel);
	}

	/**
	 * Construit le panel.
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ChartPanel buildChart() {
		xysCallBS = new XYSeries("Call Black-Scholes");
		xysPutBS = new XYSeries("Put Black-Scholes");
		xysCallMC = new XYSeries("Call Monte-Carlo");
		xysPutMC = new XYSeries("Put Monte-Carlo");

		XYSeriesCollection datasetCall = new XYSeriesCollection();
		datasetCall.addSeries(xysCallBS);
		datasetCall.addSeries(xysCallMC);

		StandardXYItemRenderer rendererCall = new StandardXYItemRenderer(StandardXYItemRenderer.LINES);
		rendererCall.setSeriesStroke(0, new BasicStroke(2));
		rendererCall.setSeriesStroke(1, new BasicStroke(2));
		rendererCall.setSeriesPaint(0, Color.blue.darker().darker());
		rendererCall.setSeriesPaint(1, Color.blue);
		rendererCall.setToolTipGenerator(new StandardXYToolTipGenerator("nSimul : {1}, valeur : {2}",
				new DecimalFormat("0"), new DecimalFormat("0.0000")));

		NumberAxis rangeAxisCall = new NumberAxis("Call");
		rangeAxisCall.setAutoRangeIncludesZero(false);

		XYPlot plotCall = new XYPlot(datasetCall, null, rangeAxisCall, rendererCall);
		plotCall.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		XYSeriesCollection datasetPut = new XYSeriesCollection();
		datasetPut.addSeries(xysPutBS);
		datasetPut.addSeries(xysPutMC);

		StandardXYItemRenderer rendererPut = new StandardXYItemRenderer(StandardXYItemRenderer.LINES);
		rendererPut.setSeriesStroke(0, new BasicStroke(2));
		rendererPut.setSeriesStroke(1, new BasicStroke(2));
		rendererPut.setSeriesPaint(0, Color.red.darker().darker());
		rendererPut.setSeriesPaint(1, Color.red);
		rendererPut.setToolTipGenerator(new StandardXYToolTipGenerator("nSimul : {1}, valeur : {2}",
				new DecimalFormat("0"), new DecimalFormat("0.0000")));

		NumberAxis rangeAxisPut = new NumberAxis("Put");
		rangeAxisPut.setAutoRangeIncludesZero(false);

		XYPlot plotPut = new XYPlot(datasetPut, null, rangeAxisPut, rendererPut);
		plotPut.setRangeAxisLocation(AxisLocation.TOP_OR_LEFT);

		NumberAxis domainAxis = new NumberAxis("Nombre de simulations");
		domainAxis.setAutoRangeIncludesZero(false);
		CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot(domainAxis);
		combinedPlot.setGap(10.0);
		combinedPlot.add(plotCall, 1);
		combinedPlot.add(plotPut, 1);

		combinedPlot.setBackgroundPaint(Color.white);
		combinedPlot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		combinedPlot.setDomainGridlinePaint(Color.white);
		combinedPlot.setRangeGridlinePaint(Color.white);

		chart = new JFreeChart("Résultats", combinedPlot);

		chart.setAntiAlias(true);

		chart.setBackgroundPaint(Color.white);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createLineBorder(Color.black, 2)));
		chartPanel.setBackground(Color.white);
		chartPanel.setPreferredSize(PanelGraphique.PANEL_DIMENSION);

		return chartPanel;
	}

	/**
	 * Ajoute un point aux graphiques
	 * 
	 * @param nSimul le nombre de simulations
	 * @param callBS la valeur du call pour Black-Scholes
	 * @param putBS  la valeur du put pour Black-Scholes
	 * @param callMC la valeur du call pour Monte-Carlo
	 * @param putMC  la valeur du put pour Monte-Carlo
	 */
	public void ajouterPoint(int nSimul, double callBS, double putBS, double callMC, double putMC) {
		xysCallBS.add(nSimul, callBS);
		xysPutBS.add(nSimul, putBS);
		xysCallMC.add(nSimul, callMC);
		xysPutMC.add(nSimul, putMC);
	}

	/**
	 * Efface les graphiques.
	 */
	public void effacer() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				xysCallBS.clear();
				xysPutBS.clear();
				xysCallMC.clear();
				xysPutMC.clear();
			}
		});
	}
}
