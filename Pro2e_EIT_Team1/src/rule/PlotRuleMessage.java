package rule;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PlotRuleMessage extends JPanel implements I_Plot_rule_message {

	// TODO falsche Eingaben überprüfen Tel Nr. nur Zahlen

	private PlotRuleController plotRuleController;

	// Gui Elements
	private JCheckBox cbTel = new JCheckBox();
	private JCheckBox cbSMS = new JCheckBox();
	private JCheckBox cbMail = new JCheckBox();

	public JTextField txtTelNumber = new JTextField();
	public JTextField txtSMSNumber = new JTextField();
	public JTextField txtMailAddress = new JTextField();
	public JTextArea txtAlertMessage = new JTextArea();
	private JScrollPane scrollPAlertMessage = new JScrollPane(txtAlertMessage);

	private final Insets insets = new Insets(5, 5, 5, 5);

	public PlotRuleMessage(PlotRuleController plotRuleMessageController) {
		this.plotRuleController = plotRuleMessageController;
		setPreferredSize(new Dimension(300, 300));
		setDoubleBuffered(true);
		setFocusable(true);
		requestFocus();

		initPlotRuleMessage();
	}

	private void initPlotRuleMessage() {
		setLayout(new GridBagLayout());

		// add extra space between Labels
		add(cbTel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets,
				0, 0));
		add(cbSMS, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets,
				0, 0));
		add(cbMail, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets,
				0, 0));
		add(new JLabel("Tel."), new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, insets, 0, 0));
		add(new JLabel("SMS"), new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, insets, 0, 0));
		add(new JLabel("Mail"), new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, insets, 0, 0));
		add(new JLabel("       "), new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, insets, 0, 0));
		add(new JLabel("       "), new GridBagConstraints(2, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, insets, 0, 0));
		add(new JLabel("       "), new GridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, insets, 0, 0));
		add(new JLabel("Telephone Numbers"), new GridBagConstraints(3, 0, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, insets, 0, 0));
		add(new JLabel("Telephone Numbers"), new GridBagConstraints(3, 2, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, insets, 0, 0));
		add(new JLabel("E-Mail Adresses"), new GridBagConstraints(3, 4, 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, insets, 0, 0));
		add(txtTelNumber, new GridBagConstraints(3, 1, 1, 1, 1.0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(txtSMSNumber, new GridBagConstraints(3, 3, 1, 1, 1.0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(txtMailAddress, new GridBagConstraints(3, 5, 1, 1, 1.0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));
		add(new JLabel("Alertmessage:"), new GridBagConstraints(0, 6, 2, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, insets, 0, 0));
		add(scrollPAlertMessage, new GridBagConstraints(0, 7, 4, 2, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, insets, 0, 0));

		txtAlertMessage.setFont(txtMailAddress.getFont());
		// txtAlertMessage.setBorder(BorderFactory.createEtchedBorder());
		txtAlertMessage.setWrapStyleWord(true);
		txtAlertMessage.setLineWrap(true);

		txtTelNumber.setEditable(false);
		txtSMSNumber.setEditable(false);
		txtMailAddress.setEditable(false);

		cbTel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbTel.isSelected()) {
					plotRuleController.setRulePhoneTelState(true);
					txtTelNumber.setEditable(true);
				} else {
					plotRuleController.setRulePhoneTelState(false);
					txtTelNumber.setEditable(false);
				}
			}
		});
		cbSMS.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				if (cbSMS.isSelected()) {
					plotRuleController.setRulePhoneSMSState(true);
					txtSMSNumber.setEditable(true);
				} else {
					plotRuleController.setRulePhoneSMSState(false);
					txtSMSNumber.setEditable(false);
				}
			}
		});
		cbMail.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (cbMail.isSelected()) {
					plotRuleController.setRuleMailState(true);
					txtMailAddress.setEditable(true);
				} else {
					plotRuleController.setRuleMailState(false);
					txtMailAddress.setEditable(false);
				}
			}
		});
		txtSMSNumber.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				readValueSMSNumber();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				readValueSMSNumber();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				readValueSMSNumber();
			}

			void readValueSMSNumber() {
				if (!txtSMSNumber.getText().equals("")) {
					try {
						Long.parseLong(txtSMSNumber.getText());

					} catch (Exception e) {
						WarningMessageBox("Failure", "Only Numbers allowed");
					}
					if (txtSMSNumber.getText().length()>10) {
						WarningMessageBox("Failure", "Enter viable Number (e.g. 0771234567");
					}
				}
			}
		});
		txtTelNumber.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				readValueTelNumber();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				readValueTelNumber();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				readValueTelNumber();
			}

			void readValueTelNumber() {
				if (!txtTelNumber.getText().equals("")) {
					try {
						Long.parseLong(txtTelNumber.getText());

					} catch (Exception e) {
						WarningMessageBox("Failure", "Only Numbers allowed");
					}
					if (txtTelNumber.getText().length()>10) {
						WarningMessageBox("Failure", "Enter viable Number (e.g. 0771234567");
					}

				}
			}
		});
	}

	@Override
	public void update(I_Rule rule) {
		cbTel.setSelected(rule.getRulePhoneTelState());
		cbSMS.setSelected(rule.getRulePhoneSMSState());
		cbMail.setSelected(rule.getRuleMailState());

		txtTelNumber.setText(rule.getRulePhoneTel());
		txtSMSNumber.setText(rule.getRulePhoneSMS());
		txtMailAddress.setText(rule.getRuleMail());

		txtAlertMessage.setText(rule.getRuleMessage());

	}

	/**
	 * Opens a WarningMessageBox for the User
	 * 
	 * @param boxtitle string with the title of the box
	 * @param message  message that should written in the box
	 */
	void WarningMessageBox(String boxtitle, String message) {
		JOptionPane.showMessageDialog(this, message, boxtitle, JOptionPane.WARNING_MESSAGE);
	}

}
