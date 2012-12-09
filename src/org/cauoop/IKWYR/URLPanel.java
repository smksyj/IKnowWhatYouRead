package org.cauoop.IKWYR;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;

public class URLPanel extends JPanel {
	private JTextField urlInputField;
	CategoryPanel textPane;

	/**
	 * Create the panel.
	 */
	public URLPanel() {
		
		urlInputField = new JTextField();
		urlInputField.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL \uC785\uB825");
		
		JLabel lblNewLabel = new JLabel("카테고리");
		
		textPane = new CategoryPanel();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(urlInputField, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUrl))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblUrl))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(urlInputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.X_AXIS));
		setLayout(groupLayout);

	}
	
	public String GetURL(){
		return this.urlInputField.getText();
	}
	public void SetResult(String result){
		this.textPane.textArea.setText(result);
	}

	public String GetCategory() {
		return this.textPane.textArea.getText();
	}
}
