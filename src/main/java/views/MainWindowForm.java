package views;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import views.listeners.BtsSpinnerListener;
import views.listeners.GenerateDistributionListener;
import views.listeners.MenuOpenListener;
import views.listeners.SubscriberCenterSpinnerListener;
import views.map.MapApplet;
import calculations.Terrain;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

public class MainWindowForm extends JFrame {

	// FIXME Remove these initializers, I put them here so it won't crash, but
	// it's definitely wrong
	private static final long serialVersionUID = -372666681152456536L;
	private final JFileChooser fc = new JFileChooser();
	private JPanel mainPanel = new JPanel();
	private JButton loadFileButton = new JButton();
	private JButton generateDistributionButton = new JButton();
	private final JSpinner btsNumberSpinner = new JSpinner();
	private final JPanel drawingPanel = new DrawingPanel();
	private JScrollPane mainScrollPane = new JScrollPane();
	private JTabbedPane mainTabPanel;
	private JCheckBox showUsersCheckBox;
    private JSpinner numberOfSubscriberCenters = new JSpinner();
    private MapApplet mapApplet;

	public MainWindowForm() {
		$$$setupUI$$$();
		setContentPane(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(ResourceBundle.getBundle("language").getString("Application_title"));
		pack();
		initComponents();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setDrawingPanel(BufferedImage image, Terrain terrain) {
		DrawingPanel cast = (DrawingPanel) drawingPanel;
		cast.setMap(image);
		cast.resetTerrain(terrain);
	}

	private void initComponents() {
		fc.setFileFilter(new FileNameExtensionFilter("JPG File", "jpg"));
		mapApplet = new MapApplet(mainTabPanel.getSize());

		File runPath = new File(""/*
								 * MainWindowForm.class.getProtectionDomain().
								 * getCodeSource() .getLocation().getPath()
								 */);
		fc.setCurrentDirectory(runPath);
		loadFileButton.addActionListener(new MenuOpenListener());
		btsNumberSpinner.addChangeListener(new BtsSpinnerListener(btsNumberSpinner, mapApplet));
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel();
		spinnerModel.setMinimum(0);
		spinnerModel.setValue(30);
		btsNumberSpinner.setModel(spinnerModel);
        SpinnerNumberModel subscriberSpinnerModel = new SpinnerNumberModel();
        subscriberSpinnerModel.setMinimum(0);
        subscriberSpinnerModel.setValue(5);
        numberOfSubscriberCenters.addChangeListener(new SubscriberCenterSpinnerListener(numberOfSubscriberCenters, mapApplet));
        numberOfSubscriberCenters.setModel(subscriberSpinnerModel);
		generateDistributionButton.addActionListener(new GenerateDistributionListener(mapApplet));
		setJMenuBar(createJMenuBar());

		mainTabPanel.addTab("Map", mapApplet);
		showUsersCheckBox.setText(ResourceBundle.getBundle("language").getString("Show_users"));
	}

	private JMenuBar createJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		return menuBar;
	}

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createOpenMenuItem());
		return menu;
	}

	private JMenuItem createOpenMenuItem() {
		JMenuItem openFile = new JMenuItem(ResourceBundle.getBundle("language").getString(
				"MenuBar_file_openFile"));

		openFile.addActionListener(new MenuOpenListener());
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		return openFile;
	}

	@SuppressWarnings("unused")
	private void createUIComponents() {
		// IntelliJ requires this method, do not delete it
	}

	public static void main(String[] args) {
		setLookAndFeel("Nimbus");
		new MainWindowForm();

	}

	private static void setLookAndFeel(String lookAndFeel) {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (lookAndFeel.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT
	 * edit this method OR call it in your code!
	 * 
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		createUIComponents();
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		mainPanel.setMinimumSize(new Dimension(445, 600));
		mainPanel.setOpaque(true);
		mainPanel.setPreferredSize(new Dimension(1000, 800));
		mainPanel.setRequestFocusEnabled(true);
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
		mainPanel.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
				GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED,
				GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				ResourceBundle.getBundle("language").getString("BTS_configuration_panel"),
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		generateDistributionButton = new JButton();
		generateDistributionButton.setActionCommand(ResourceBundle.getBundle("language").getString(
				"Generate_random_BTS_distribution"));
		generateDistributionButton.setLabel(ResourceBundle.getBundle("language").getString(
				"Generate_random_BTS_distribution"));
		generateDistributionButton.setText("Generate random BTS distribution");
		panel1.add(generateDistributionButton, new GridConstraints(3, 1, 1, 1,
				GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		panel1.add(btsNumberSpinner, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
				GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
				GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label1 = new JLabel();
		this.$$$loadLabelText$$$(label1,
				ResourceBundle.getBundle("language").getString("BTS_number"));
		panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST,
				GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED,
				GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		loadFileButton = new JButton();
		loadFileButton.setActionCommand(ResourceBundle.getBundle("language").getString("Load_map"));
		this.$$$loadButtonText$$$(loadFileButton,
				ResourceBundle.getBundle("language").getString("MenuBar_file_openFile"));
		panel1.add(loadFileButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
				GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK
						| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED,
				null, null, null, 0, false));
		showUsersCheckBox = new JCheckBox();
		showUsersCheckBox.setText("Show users");
		panel1.add(showUsersCheckBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST,
				GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK
						| GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED,
				null, null, null, 0, false));
		mainTabPanel = new JTabbedPane();
		mainPanel.add(mainTabPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER,
				GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK
						| GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null,
				new Dimension(200, 200), null, 0, false));
		mainScrollPane = new JScrollPane();
		mainScrollPane.setVerticalScrollBarPolicy(20);
		mainTabPanel.addTab("Untitled", mainScrollPane);
		mainScrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				ResourceBundle.getBundle("language").getString("BTS_drawing_panel"),
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		drawingPanel.setPreferredSize(new Dimension(500, 500));
		mainScrollPane.setViewportView(drawingPanel);
		label1.setLabelFor(btsNumberSpinner);
	}

	/**
	 * @noinspection ALL
	 */
	private void $$$loadLabelText$$$(JLabel component, String text) {
		StringBuffer result = new StringBuffer();
		boolean haveMnemonic = false;
		char mnemonic = '\0';
		int mnemonicIndex = -1;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '&') {
				i++;
				if (i == text.length())
					break;
				if (!haveMnemonic && text.charAt(i) != '&') {
					haveMnemonic = true;
					mnemonic = text.charAt(i);
					mnemonicIndex = result.length();
				}
			}
			result.append(text.charAt(i));
		}
		component.setText(result.toString());
		if (haveMnemonic) {
			component.setDisplayedMnemonic(mnemonic);
			component.setDisplayedMnemonicIndex(mnemonicIndex);
		}
	}

	/**
	 * @noinspection ALL
	 */
	private void $$$loadButtonText$$$(AbstractButton component, String text) {
		StringBuffer result = new StringBuffer();
		boolean haveMnemonic = false;
		char mnemonic = '\0';
		int mnemonicIndex = -1;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '&') {
				i++;
				if (i == text.length())
					break;
				if (!haveMnemonic && text.charAt(i) != '&') {
					haveMnemonic = true;
					mnemonic = text.charAt(i);
					mnemonicIndex = result.length();
				}
			}
			result.append(text.charAt(i));
		}
		component.setText(result.toString());
		if (haveMnemonic) {
			component.setMnemonic(mnemonic);
			component.setDisplayedMnemonicIndex(mnemonicIndex);
		}
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return mainPanel;
	}
}
