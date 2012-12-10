package org.cauoop.IKWYR;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.cauoop.alg.Classifier;
import org.cauoop.crawler.ArticleCrawler;
import org.cauoop.data.WordDatabase;
import org.cauoop.filter.ArticleFilter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {	
	private static final int CATEGORY_RANK = 3;
	private GridBagLayout gridBagLayout;
	private JPanel buttonPanel;
	private JPanel inputPanel;
	private Vector<URLPanel> urlpanels;
	// added
	private WordDatabase database = new WordDatabase();
	private ArticleCrawler crawler = new ArticleCrawler();

	private JButton crawlingButton;
	private JButton analysisButton;
	private JComboBox<String> categoryComboBox;
	private JButton portalCrawling;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		urlpanels= new Vector<URLPanel>();

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setTitle("IKWYR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 539);
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		buttonPanel = new JPanel();

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 3;
		gbc_panel.weighty = 1.0;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(buttonPanel, gbc_panel);

		JButton btnNewButton = new JButton("ADD URL");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getActionCommand().equals("ADD URL")){
					urlpanels.addElement(new URLPanel());
					urlpanels.get(urlpanels.size()-1).setPreferredSize(new Dimension(485,104));
					MainFrame.this.inputPanel.add(urlpanels.get(urlpanels.size()-1));
					crawlingButton.setEnabled(true);
					analysisButton.setEnabled(true);

					MainFrame.this.validate();
				}
			}
		});

		crawlingButton = new JButton("Crawling");
		crawlingButton.setEnabled(false);
		crawlingButton.addActionListener(new CrawlingListener());

		analysisButton = new JButton("Analysis");
		analysisButton.setEnabled(false);
		analysisButton.addActionListener(new AnalysisListener());
		
		categoryComboBox = new JComboBox<String>();
		categoryComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"None", "\uC815\uCE58", "\uACBD\uC81C", "IT", "\uC0AC\uD68C", "\uBB38\uD654", "\uD3EC\uD0C8", "\uD55C\uAE00\uC774\uB2E4", "english"}));
		categoryComboBox.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if ( categoryComboBox.getSelectedItem().toString().equals("None") ) {
					portalCrawling.setEnabled(false);
				} else {
					portalCrawling.setEnabled(true);
				}				
			}
		});

		portalCrawling = new JButton("Portal");
		portalCrawling.setEnabled(false);
		portalCrawling.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				database.learningInsert(crawler.autoGet((String) categoryComboBox.getSelectedItem()).split(" "), (String) categoryComboBox.getSelectedItem());
			}
		});
		
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel.setHorizontalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addComponent(portalCrawling, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(categoryComboBox, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addComponent(crawlingButton, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(analysisButton, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_buttonPanel.setVerticalGroup(
			gl_buttonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_buttonPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
						.addGroup(gl_buttonPanel.createSequentialGroup()
							.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(categoryComboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(portalCrawling))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crawlingButton, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
						.addComponent(analysisButton, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
					.addContainerGap())
		);
		buttonPanel.setLayout(gl_buttonPanel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 30.0;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);

		inputPanel = new JPanel();
		scrollPane.setViewportView(inputPanel);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
	}

	private class CrawlingListener implements ActionListener {
//		ArticleCrawler crawler = new ArticleCrawler();

		@Override
		public void actionPerformed(ActionEvent e) {
			for ( URLPanel panel : MainFrame.this.urlpanels ) {
//				System.out.println(crawler.getHtml(panel.GetURL()));

				ArticleFilter filter = new ArticleFilter(panel.GetCategory(), crawler.getHtml(panel.GetURL()));

				database.learningInsert(filter.getSplit(), panel.GetCategory());
			}			
		}		
	}

	private class AnalysisListener implements ActionListener {
		private static final int NUMBER_FOR_DECIMAL_CALCULATION = 10000;
		List<String> words = null;
		Classifier classifier = new Classifier();

		@Override
		public void actionPerformed(ActionEvent e) {
//			System.out.println("------------------- Analysis Start -----------------------");

			for ( URLPanel panel : MainFrame.this.urlpanels ) {
				ArticleFilter filter = new ArticleFilter(null, crawler.getHtml(panel.GetURL()));
				words = new LinkedList<String>(Arrays.asList(filter.getSplit()));

				List<LinkedList<String>> wordStatistic = database.wordStatistic(words);
				List<String> results = classifier.classification(wordStatistic, database.categoryCount());
				Collections.sort(results, new Comparator<String>() {
					@Override
					public int compare(String category1, String category2) {
						String percent1 = category1.substring(category1.indexOf(":") + 2, category1.indexOf("%")-1);
						String percent2 = category2.substring(category2.indexOf(":") + 2, category2.indexOf("%")-1);
						return (int) (Double.parseDouble(percent2) * NUMBER_FOR_DECIMAL_CALCULATION - Double.parseDouble(percent1) * NUMBER_FOR_DECIMAL_CALCULATION);
					}
				});

				StringBuilder panelValue = new StringBuilder();
				for (int i = 0; i < CATEGORY_RANK; i++ ) {
					if ( i == CATEGORY_RANK - 1 ) {
						panelValue.append(results.get(i));						
					} else {
						panelValue.append(results.get(i)).append("\r\n");
					}
				}

				panel.SetResult(panelValue.toString());
			}
		}
	}
}