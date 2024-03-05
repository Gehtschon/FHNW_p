package realtime_view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import frameGenerator.FrameGenerator;

public class Menu implements I_Menu {
	JMenuBar menuBar = new JMenuBar();
	FrameGenerator frameGenerator = new FrameGenerator();

	public Menu() {
		JMenu menuPath = new JMenu("Path");
		menuBar.add(menuPath);

		JMenu menuBand = new JMenu("Band");
		menuBar.add(menuBand);

		JMenu menuFft = new JMenu("FFT");
		menuBar.add(menuFft);

		menuPath.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Menu Button Path pressed");
				frameGenerator.buildPathSettingsFrame();
			}
		});
		menuBand.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Menu Button Band pressed");
				frameGenerator.buildBandSettingsFrame();
			}
		});
		menuFft.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Menu Button FFT pressed");
				frameGenerator.buildFftSettingsFrame();
			}
		});

	}

	@Override
	public JMenuBar getMenuBar() {
		return menuBar;
	}

}
