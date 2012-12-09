package org.cauoop.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.cauoop.filter.ArticleFilter;

public class WordDatabase {
	public List<String> categoryList;
	
	public WordDatabase() {
		categoryList = new LinkedList<String>();
		categoryList.add("정치");
		categoryList.add("경제");
		categoryList.add("사회");
		categoryList.add("문화");
		categoryList.add("IT");
		categoryList.add("한글이다");
		categoryList.add("english");
		categoryList.add("포탈");
		
		Collections.sort(categoryList, new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				return arg0.compareToIgnoreCase(arg1);	
			}			
		});
	}
	
	public static void main(String[] args) {
		WordDatabase learn = new WordDatabase();
		//List<LinkedList<String>> word = new ArrayList<LinkedList<String>>();
		String[] test = new String[3];

		test[0] = "han";
		test[1] = "hyun";
		test[2] = "abcd";
		
		ArticleFilter insertWord = new ArticleFilter("english", "��ǻ�Ͱ� ����Ʈ���� ���ȴ�");
		learn.learningInsert(insertWord);
		//learn.learningInsert(test, "�ѱ��̴�");
		
		List<String> catCount = learn.categoryCount();
		
//		for(int i = 0; i<catCount.size(); i++){
//			System.out.println(catCount.get(i));
//		}	
		
		/*List<String> wordSet = new LinkedList<String>();
		wordSet.add("han");
		wordSet.add("hyun");
		wordSet.add("noex");
		word = learn.wordStatistic(wordSet);

		for(int i = 0; i<word.size(); i++){
			for(int j = 0; j<word.get(i).size(); j++){
				System.out.print(word.get(i).get(j)+" ");
			}
			System.out.println();
		}*/
	}

	//Change parameter later List<String>, String cat -> List<Data>      Data is UDT made by gea chul
	public void learningInsert(ArticleFilter word){
		Connection conn;
		Statement stmt, catCount;
		/*PreparedStatement updateStmt;
		String sql1 = "UPDATE ? SET num=num+1 WHERE category=?";
		PreparedStatement createStmt;
		String sql2 = "CREATE TABLE IF NOT EXISTS ?(category CHAR(10) NOT NULL, num INT, PRIMARY KEY(category))";
		PreparedStatement insertStmt;
		String sql3 = "INSERT INTO ? VALUES(?, 1)";*/

		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			System.err.print("ClassNOtFoundException: ");
		}

		try{
			String jdbcUrl = "jdbc:mysql://intra.zeropage.org:3306/OOPProj";
			String userid = "root";
			String userPass = "";
			conn = DriverManager.getConnection(jdbcUrl,userid,userPass);
			stmt = conn.createStatement();

			/*updateStmt = conn.prepareStatement(sql1);
			createStmt = conn.prepareStatement(sql2);
			insertStmt = conn.prepareStatement(sql3);*/
			
			catCount = conn.createStatement();

			String catInsert = "CREATE TABLE IF NOT EXISTS catCount(cat CHAR(10) NOT NULL, num INT, PRIMARY KEY(cat))";
			catCount.executeUpdate(catInsert);
			
			String catUpdate = "UPDATE catCount SET num=num+1 WHERE cat='" + word.getCategory() + "'";
			if(catCount.executeUpdate(catUpdate)==0){	//Table is exist but have no row category=word.get(0)
				catUpdate = "INSERT INTO catCount VALUES('"+word.getCategory()+"', 1)";
				stmt.executeUpdate(catUpdate);
			}
			catCount.close();

			//word table
			//+------------+
			//|category|num|
			//|------------|
			String [] words = word.getSplit();
			int j = words.length;
			String cat = word.getCategory();
			
			for(int i = 0; i<j; i++){
				String sql = "UPDATE " + words[i] + " SET num=num+1 WHERE category='" + cat + "'";
				try{
					//updateStmt.setString(1, word.get(0));
					//updateStmt.setString(2, cat);
					if(stmt.executeUpdate(sql)==0){	//Table is exist but have no row category=word.get(0)
						sql = "INSERT INTO " + words[i] + " VALUES('"+cat+"', 1)";
						stmt.executeUpdate(sql);
						/*insertStmt.setString(1, word.get(0));
						insertStmt.setString(2, cat);
						insertStmt.executeUpdate();*/
					}
				}catch(SQLException e){	//If word table wasn't exist. Create table and insert values
					sql = "CREATE TABLE IF NOT EXISTS " + words[i] + " (category CHAR(10) NOT NULL, num INT, PRIMARY KEY(category))";
					stmt.executeUpdate(sql);
					sql = "INSERT INTO " + words[i] + " VALUES('"+cat+"', 1)";
					stmt.executeUpdate(sql);
					//createStmt.setString(1, word.get(0));
					//createStmt.execute();
					//insertStmt.setString(1, word.get(0));
					//insertStmt.setString(2, cat);
					//insertStmt.executeUpdate();
				}
			}
			conn.close();
			stmt.close();
		}catch(SQLException e){
			System.out.println("fail... "+e.getMessage());
		}
	}
	
	public void learningInsert(String[] words, String cat){
		Connection conn;
		Statement stmt, catCount;

		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			System.err.print("ClassNOtFoundException: ");
		}

		try{
			String jdbcUrl = "jdbc:mysql://intra.zeropage.org:3306/OOPProj";
			String userid = "root";
			String userPass = "";
			conn = DriverManager.getConnection(jdbcUrl,userid,userPass);
			stmt = conn.createStatement();
			
			catCount = conn.createStatement();

			String catInsert = "CREATE TABLE IF NOT EXISTS catCount(cat CHAR(10) NOT NULL, num INT, PRIMARY KEY(cat))";
			catCount.executeUpdate(catInsert);

			String catUpdate = "UPDATE catCount SET num=num+1 WHERE cat='" + cat + "'";
			if(catCount.executeUpdate(catUpdate)==0){	//Table is exist but have no row category=word.get(0)
				catUpdate = "INSERT INTO catCount VALUES('"+cat+"', 1)";
				stmt.executeUpdate(catUpdate);
			}
			catCount.close();
			
			//word table
			//+------------+
			//|category|num|
			//|------------|
			int j = words.length;		
			for(int i = 0; i<j; i++){
				if ( words[i].equals("") || words[i].length() > 30 || words[i].matches("[0-9]*") ) {
					continue;
				}
				String sql = "UPDATE " + words[i] + " SET num=num+1 WHERE category='" + cat + "'";
//				System.out.println(words[i]);
				try{
					if(stmt.executeUpdate(sql)==0){	//Table is exist but have no row category=word.get(0)
						sql = "INSERT INTO " + words[i] + " VALUES('"+cat+"', 1)";
						stmt.executeUpdate(sql);
					}
				}catch(SQLException e){	//If word table wasn't exist. Create table and insert values
					sql = "CREATE TABLE IF NOT EXISTS " + words[i] + " (category CHAR(10) NOT NULL, num INT, PRIMARY KEY(category))";
					stmt.executeUpdate(sql);
					sql = "INSERT INTO " + words[i] + " VALUES('"+cat+"', 1)";
					stmt.executeUpdate(sql);
				}
			}
			conn.close();
			stmt.close();
		}catch(SQLException e){
			System.out.println("fail... "+e.getMessage());			
		}
	}

	public List<LinkedList<String>> wordStatistic(List<String> words){
		Connection conn;
		Statement stmt, inLoop;		
		
		Collections.sort(categoryList);

//		String [] cat = {"정치","경제","사회","문화","IT","한글이다","english"};

		List<LinkedList<String>> stat = new ArrayList<LinkedList<String>>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			System.err.print("ClassNOtFoundException: ");
		}

		try{
			String jdbcUrl = "jdbc:mysql://intra.zeropage.org:3306/OOPProj";
			String userid = "root";
			String userPass = "";

			conn = DriverManager.getConnection(jdbcUrl,userid,userPass);
			stmt = conn.createStatement();
			inLoop = conn.createStatement();

			//		->stat -- arraylist
			//		 cat1  cat2  cat3 ...	↓
			//word1   3      0	   2		stat.get(i).get(j)
			//word2   2     10     7
			//word3   1      4     8
			//  .
			//  .
			String sql;
			for(int i = 0; i<categoryList.size(); i++){
				stat.add(new LinkedList<String>());
				stat.get(i).add(categoryList.get(i));	//First element of each list is category name
				for(int j = 0; j<words.size(); j++){
					sql = "SELECT num FROM " + words.get(j) + " WHERE category='" + categoryList.get(i) + "'";
					ResultSet temp;
					try{
						temp = inLoop.executeQuery(sql);
						temp.next();
						stat.get(i).add(Integer.toString(temp.getInt(1)));
					}catch(SQLException e){
						stat.get(i).add("0");
					}
					//temp.close();
				}
			}
			conn.close();
			stmt.close();
		}catch(SQLException e){
			System.out.println("fail... "+e.getMessage());
		}
		return stat;
	}
	
	public List<String> categoryCount(){
		List<String> category = new LinkedList<String>();
		Connection conn;
		Statement stmt;
		ResultSet rs;

		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			System.err.print("ClassNOtFoundException: ");
		}

		try{
			String jdbcUrl = "jdbc:mysql://intra.zeropage.org:3306/OOPProj";
			String userid = "root";
			String userPass = "";
			conn = DriverManager.getConnection(jdbcUrl,userid,userPass);
			stmt = conn.createStatement();
			
			String sql = "SELECT * FROM catCount ORDER BY cat ASC";
			rs = stmt.executeQuery(sql);
			rs.next();
			
			while(!rs.isAfterLast()){
				category.add(rs.getString(1));
				category.add(Integer.toString(rs.getInt(2)));
				rs.next();
			}			
		}catch(SQLException e){
			System.out.println("fail... "+e.getMessage());
		}
		
		return category;
	}
	
	public void addCategory(String cat){
		categoryList.add(cat);
		Collections.sort(categoryList, new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				return arg0.compareToIgnoreCase(arg1);	
			}			
		});
	}
}