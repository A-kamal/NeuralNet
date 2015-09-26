import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
public class GraphingLib extends JFrame {
	
	public GraphingLib (String title, String chartTitle, double[] error){
		super(title);
		JFreeChart line = ChartFactory.createLineChart(
		         chartTitle,
		         "Iterations","Error",
		         createDataset(error),
		         PlotOrientation.VERTICAL,
		         true,true,false);
		ChartPanel chartPanel = new ChartPanel( line );
	    chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	    setContentPane( chartPanel );
	}
	public DefaultCategoryDataset createDataset(double[] error){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		
			for (int i=0; i<error.length; i++){
				dataset.addValue(error[i],"Error",""+i);
				
			}
			return dataset;
	}
}
