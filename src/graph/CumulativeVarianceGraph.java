package graph;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demonstration application showing a time series chart where you can dynamically add
 * (random) data by clicking on a button.
 *
 */
public class CumulativeVarianceGraph extends ApplicationFrame implements ActionListener {

    /** The time series data. */
    private TimeSeries seriesTank0;
    private TimeSeries seriesTank1;
    private TimeSeries seriesTank2;

    /** The most recent value added. */
    private double lastValueTank0 = 0.0;
    private double lastValueTank1 = 0.0;
    private double lastValueTank2= 0.0;

    /**
     * Constructs a new demonstration application.
     *
     * @param title  the frame title.
     */
    public CumulativeVarianceGraph(final String title) {

        super(title);
        this.seriesTank0 = new TimeSeries("Tank 0 - Data", Millisecond.class);
        this.seriesTank1 = new TimeSeries("Tank 1 - Data", Millisecond.class);
        this.seriesTank2 = new TimeSeries("Tank 2 - Data", Millisecond.class);
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(this.seriesTank0);
        dataset.addSeries(this.seriesTank1);
        dataset.addSeries(this.seriesTank2);
        final JFreeChart chart = createChart(dataset);
        final Millisecond now = new Millisecond();
        
        
        final ChartPanel chartPanel = new ChartPanel(chart);
/*        final JButton button = new JButton("Add New Data Item");
        button.setActionCommand("ADD_DATA");
        button.addActionListener(this);*/

        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
        //content.add(button, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 540));
        setContentPane(content);
        
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);

    }

    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return A sample chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "Cumulative Variance Graph", 
            "Time", 
            "Cumulative Variance",
            dataset, 
            true, 
            true, 
            false
        );
        final XYPlot plot = result.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);        
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(-0.01, 0.5); 
        return result;
    }
    
    public void addVariance(double variance, int tankId, Millisecond now) {
    	if (tankId == 0){
    		this.lastValueTank0 += variance;
         //   final Millisecond now = new Millisecond();
            //System.out.println("Now = " + now.toString());
            this.seriesTank0.add(now, this.lastValueTank0);    		
    	}
    	if (tankId == 1){
    		this.lastValueTank1 += variance;
          //  final Millisecond now = new Millisecond();
            //System.out.println("Now = " + now.toString());
            this.seriesTank1.add(now, this.lastValueTank1);    		
    	}
    	if (tankId == 2){
    		this.lastValueTank2 += variance;
            //final Millisecond now = new Millisecond();
            //System.out.println("Now = " + now.toString());
            this.seriesTank2.add(now, this.lastValueTank2);    		
    	}
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
