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
		setBounds(100, 100, 473, 539);
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		buttonPanel = new JPanel();

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 0;
		gbc_panel.weighty = 1.0;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(buttonPanel, gbc_panel);
		buttonPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnNewButton = new JButton("ADD URL");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getActionCommand().equals("ADD URL")){
					urlpanels.addElement(new URLPanel());
					urlpanels.get(urlpanels.size()-1).setPreferredSize(new Dimension(450,104));
					MainFrame.this.inputPanel.add(urlpanels.get(urlpanels.size()-1));
					crawlingButton.setEnabled(true);
					analysisButton.setEnabled(true);

					MainFrame.this.validate();
				}
			}
		});
		buttonPanel.add(btnNewButton);

		crawlingButton = new JButton("Crawling");
		crawlingButton.setEnabled(false);
		crawlingButton.addActionListener(new CrawlingListener());
		buttonPanel.add(crawlingButton);

		analysisButton = new JButton("Analysis");
		analysisButton.setEnabled(false);
		analysisButton.addActionListener(new AnalysisListener());
		buttonPanel.add(analysisButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 8.0;
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
				System.out.println(crawler.getHtml(panel.GetURL()));

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