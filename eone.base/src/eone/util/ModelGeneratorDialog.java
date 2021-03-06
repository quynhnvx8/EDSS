package eone.util;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import eone.EOne;

/**
 *
 * @author hengsin
 *
 */
public class ModelGeneratorDialog extends JDialog implements ActionListener {

	/**
	 * default generated serial version Id
	 */
	private static final long serialVersionUID = 3546051609729699491L;
	private JButton bGenerate;
	private JButton bCancel;
	private JButton bFolder;
	private JTextField fFolderName;
	private JTextField fPackageName;
	private JTextField fTableName;
	private JCheckBox fGenerateInterface;
	private JCheckBox fGenerateClass;
	
	public ModelGeneratorDialog() {
		super();
		setTitle("Model Class Generator");
		BorderLayout layout = new BorderLayout();
		this.getContentPane().setLayout(layout);
		Panel confirmPanel = new Panel();
		this.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		Panel mainPanel = new Panel();
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new GridLayout(6, 2));

		Panel filePanel = new Panel();
		filePanel.setLayout(new BorderLayout());
		String defaultPath = EOne.getEOneHome() + File.separator + "eone.base" + File.separator + "src";
		fFolderName = new JTextField(defaultPath);
		filePanel.add(fFolderName, BorderLayout.CENTER);
		bFolder = new JButton("...");
		bFolder.setMargin(new Insets(0, 0, 0, 0));
		filePanel.add(bFolder, BorderLayout.EAST);
		mainPanel.add(new JLabel("Source Folder"));
		mainPanel.add(filePanel);
		bFolder.addActionListener(this);

		mainPanel.add(new JLabel("Package Name"));
		fPackageName = new JTextField("eone.base.model");
		mainPanel.add(fPackageName);

		mainPanel.add(new JLabel("Table Name"));
		fTableName = new JTextField("");
		mainPanel.add(fTableName);

		Panel chkPanel = new Panel();
		chkPanel.setLayout(new GridLayout(1, 2));
		mainPanel.add(new JLabel(""));
		mainPanel.add(chkPanel);
		fGenerateInterface = new JCheckBox("Generate Interface");
		fGenerateInterface.setSelected(true);
		chkPanel.add(fGenerateInterface);
		fGenerateClass = new JCheckBox("Generate Class");
		fGenerateClass.setSelected(true);
		chkPanel.add(fGenerateClass);

		bGenerate = new JButton("Generate Source");
		confirmPanel.add(bGenerate);
		bCancel = new JButton("Cancel");
		confirmPanel.add(bCancel);
		bGenerate.addActionListener(this);
		bCancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bGenerate) {
			String folder = fFolderName.getText();
			if (folder == null || folder.trim().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter source folder name", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String packageName = fPackageName.getText();
			if (packageName == null || packageName.trim().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter package name", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tableName = fTableName.getText();
			if (tableName == null || tableName.trim().length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter table name", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!fGenerateClass.isSelected() && !fGenerateInterface.isSelected()) {
				JOptionPane.showMessageDialog(this, "Must select at least one of generate interface or generate class", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			if (fGenerateInterface.isSelected()) {
				ModelInterfaceGenerator.generateSource(folder, packageName, tableName);
			}
			if (fGenerateClass.isSelected()) {
				ModelClassGenerator.generateSource(folder, packageName, tableName);
			}			
			this.dispose();
		} else if (e.getSource() == bCancel) {
			this.dispose();
		} else if (e.getSource() == bFolder) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int state = fileChooser.showOpenDialog(this);
			if (state == JFileChooser.APPROVE_OPTION) {
				fFolderName.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}

}
