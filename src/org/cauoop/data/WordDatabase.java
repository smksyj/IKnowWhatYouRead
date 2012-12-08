package org.cauoop.data;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordDatabase {
	public static void main(String[] args) {
		WordDatabase learn = new WordDatabase();
		List<LinkedList<String>> word = new ArrayList<LinkedList<String>>();
		List<String> test = new LinkedList<String>();

		test.add("han");
		test.add("hyun");
		test.add("abcd");
		learn.learningInsert(test, "english");

		List<String> wordSet = new LinkedList<String>();
		wordSet.add("han");
		wordSet.add("hyun");
		wordSet.add("noex");
		word = learn.wordStatistic(wordSet);

		for(int i = 0; i<word.size(); i++){
			for(int j = 0; j<word.get(i).size(); j++){
				System.out.print(word.get(i).get(j)+" ");
			}
			System.out.println();
		}
	}

	//Change parameter later List<String>, String cat -> List<Data>      Data is UDT made by jae chul
	public void learningInsert(List<String> word, String cat){
		Connection conn;
		Statement stmt;
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
			System.out.println("connetion success");
			stmt = conn.createStatement();

			/*updateStmt = conn.prepareStatement(sql1);
			createStmt = conn.prepareStatement(sql2);
			insertStmt = conn.prepareStatement(sql3);*/

			//word table
			//+------------+
			//|category|num|
			//|------------|
			int j = word.size();
			for(int i = 0; i<j; i++){
				String sql = "UPDATE " + word.get(0) + " SET num=num+1 WHERE category='" + cat + "'";
				try{
					//updateStmt.setString(1, word.get(0));
					//updateStmt.setString(2, cat);
					if(stmt.executeUpdate(sql)==0){	//Table is exist but have no row category=word.get(0)
						sql = "INSERT INTO " + word.get(0) + " VALUES('"+cat+"', 1)";
						stmt.executeUpdate(sql);
						/*insertStmt.setString(1, word.get(0));
						insertStmt.setString(2, cat);
						insertStmt.executeUpdate();*/
					}
				}catch(SQLException e){	//If word table wasn't exist. Create table and insert values
					sql = "CREATE TABLE IF NOT EXISTS " + word.get(0) + "(category CHAR(10) NOT NULL, num INT, PRIMARY KEY(category))";
					stmt.executeUpdate(sql);
					sql = "INSERT INTO " + word.get(0) + " VALUES('"+cat+"', 1)";
					stmt.executeUpdate(sql);
					//createStmt.setString(1, word.get(0));
					//createStmt.execute();
					//insertStmt.setString(1, word.get(0));
					//insertStmt.setString(2, cat);
					//insertStmt.executeUpdate();
				}
				word.remove(0);
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

		String [] cat = {"정치","경제","사회","문화","IT","한글이다","english"};

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
			for(int i = 0; i<cat.length; i++){
				stat.add(new LinkedList<String>());
				stat.get(i).add(cat[i]);	//First element of each list is category name
				for(int j = 0; j<words.size(); j++){
					sql = "SELECT num FROM " + words.get(j) + " WHERE category='" + cat[i] + "'";
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
}