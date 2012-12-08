package org.cauoop.IKWYR;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class URLPanel extends JPanel {
	private JTextField urlInputField;
	private JTextField resultField;

	/**
	 * Create the panel.
	 */
	public URLPanel() {
		
		urlInputField = new JTextField();
		urlInputField.setColumns(10);
		
		resultField = new JTextField();
		resultField.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL \uC785\uB825");
		
		JLabel lblNewLabel = new JLabel("카테고리");
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
						.addComponent(resultField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblUrl))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(urlInputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(resultField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	public String GetURL(){
		return this.urlInputField.getText();
	}
	public void SetResult(String result){
		this.resultField.setText(result);
	}

	public String GetCategory() {
		return this.resultField.getText();
	}
}
