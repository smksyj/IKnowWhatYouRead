package org.cauoop.IKWYR;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

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
						.addComponent(lblUrl)
						.addComponent(urlInputField, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblUrl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(urlInputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.X_AXIS));
		setLayout(groupLayout);

	}
	
	public String GetURL(){
		return this.urlInputField.getText();
	}
	public void SetResult(String result){
		this.textPane.textArea.setText(result);
		this.textPane.textArea.setCaretPosition(0);
	}

	public String GetCategory() {
		return this.textPane.textArea.getText();
	}
}
