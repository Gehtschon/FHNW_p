package frameGenerator;
import javax.swing.JFrame;

public class Frame extends JFrame{

	public Frame(String frameName) {
		super(frameName);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public void configPlotPanelFrame()	{
		this.pack();
		this.setVisible(true);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
	}
	

	public void configFrame()	{	
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
	}
	

}
