package com;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Item {
	
	public Connection connect(){ 
		
		 Connection con = null;
		
		 try{
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tests","root","mysql123");
			 //For testing
			 System.out.println("Successfully Connected.");
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		
		 return con;
		 
	}
	
	public String insertItem(String code, String name, String price, String desc){
			
			String output = "";
			
			try{
				 Connection con = connect();
				 
				 if (con == null){
					 return "Error while connecting to the database";
				 }
				 
				 // create a prepared statement
				 String query = "insert into items(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)" 
						 		+ " values (?, ?, ?, ?, ?)";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
				 // binding values
				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(2, code);
				 preparedStmt.setString(3, name);
				 preparedStmt.setDouble(4, Double.parseDouble(price));
				 preparedStmt.setString(5, desc); 
				 
				//execute the statement
				 preparedStmt.execute();
				 con.close();
				 output = "Inserted successfully";
			 }
			catch (Exception e){
				 output = "Error while inserting";
				 System.err.println(e.getMessage());
			 }
			return output;
		}
	
	public String readItems(){
		
	 	String output = "";
	 
		 try{
			 
			 Connection con = connect();
			 
			 if (con == null){
				 return "Error while connecting to the database for reading.";
			  }
			 
			// Prepare the html table to be displayed
			 output = "<table border='1'>"
					 + "<tr>"
					 + "<th>Item Code</th>"
					 + "<th>Item Name</th>"
					 + "<th>Item Price</th>"
					 + "<th>Item Description</th>"
					 + "<th>Update</th>"
					 + "<th>Remove</th>"
					 + "</tr>";
			 
			 String query = "select * from items";
			 
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query); 
			 
			// iterate through the rows in the result set
			 while (rs.next()){
				  String itemID = Integer.toString(rs.getInt("itemID"));
				  String itemCode = rs.getString("itemCode");
				  String itemName = rs.getString("itemName");
				  String itemPrice = Double.toString(rs.getDouble("itemPrice"));			 
				  String itemDesc = rs.getString("itemDesc");
			 
			 // Add a row into the html table
			 output += "<tr><td>" + itemCode + "</td>";
			 output += "<td>" + itemName + "</td>";
			 output += "<td>" + itemPrice + "</td>";
			 output += "<td>" + itemDesc + "</td>";
			 
			 // buttons
			 output += "<td><form method='post' action='itemUpdate.jsp'>"
						+ "<input name='btnUpdate' type='submit' value='Update' class='btn btn-success'>"
						+ "<input name='itemID' type='hidden' value='" + itemID + "'>" 
						+ "<input name='itemCode' type='hidden' value='" + itemCode + "'>" 
						+ "<input name='itemName' type='hidden' value='" + itemName + "'>" 
						+ "<input name='itemPrice' type='hidden' value='" + itemPrice + "'>" 
						+ "<input name='itemDesc' type='hidden' value='" + itemDesc + "'>" +"</form></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + itemID + "'>" + "</form></td></tr>";
			 }
			 
			 con.close();
			 
			 // Complete the html table
			 output += "</table>";
		 }
	     catch (Exception e){
	    	 
			 output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			 
		 }
		 return output;
	}
	
	public String deleteItem(String itemID){	
    	
    	String output = "";
    	
    	try{
			 Connection con = connect();	
			 
			 String query =" delete from items where ItemID='"+ itemID +"' ";				
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 preparedStmt.execute();
			 con.close();
			 output = "Deleted Successfully";
			
		
		}
    	catch(Exception e){
			output = "Error while deleting the items.";
			System.err.println(e.getMessage());
		}
    	
    	return output;    	
	}
		
	
	public String updateItem(String ID, String code, String name, String price, String desc){
		
		System.out.println(ID);
		
		String output = "";
		
		try{
			
			Connection con = connect();
			
			if (con == null){
				return "Error while connecting to the database";
			}
			
			// create a prepared statement      
			String query = "update items set itemCode=?,itemName =?,itemPrice=?,itemDesc=? where itemID = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3,Double.parseDouble(price));
			preparedStmt.setString(4, desc); 
			preparedStmt.setString(5, ID);
			
			//execute the statement
			preparedStmt.executeUpdate();
			con.close();
			output = "Updated Successfully";
		}
		catch (Exception e){
			output = "Error while updating a one item";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	//Read order by particular itemID
	public String readOneItem(String ID){
	
		String output = "";
		
		try{
			
			Connection con = connect();
			
			if (con == null){
				return "Error while connecting to the database for selected item reading.";
			}
			// Prepare the html table to be displayed
			 output = "<table border='1'>"
			 			+ "<tr>"
			 			+ "<th>itemID</th>"
			 			+ "<th>itemCode</th>"
			 			+ "<th>itemName</th>"
			 			+ "<th>itemPrice</th>" 
			 			+ "<th>itemDesc</th>"
			 			+ "<th>Update</th>"
						+ "<th>Remove</th>" 
			 			+ "</tr>"; 
	 
			String query = "select * from items where itemID = ? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, ID);
			ResultSet rs = preparedStmt.executeQuery();
			
			// iterate through the rows in the result set
			while (rs.next()){
				
				String itemID = rs.getString("itemID");
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");
				
				 //  Add a row into the html table
				 output += "<td>" + itemID + "</td>";
				 output += "<td>" + itemCode + "</td>";
				 output += "<td>" + itemName + "</td>";
				 output += "<td>" + itemPrice + "</td>";
				 output += "<td>" + itemDesc + "</td>";
				 
				 output += "<td><form method='post' action='itemUpdate.jsp'>"
				 		 + "<input name='btnUpdate' type='submit' value='Update' class='btn btn-secondary'></td>"
				 		 + "<input name='itemID' type='hidden' value='" + itemID + "'>"
				 		 + "<input name='itemCode' type='hidden' value='" + itemCode + "'>"
				 		 + "<input name='itemName' type='hidden' value='" + itemName + "'>"
				 		 + "<input name='itemPrice' type='hidden' value='" + itemPrice + "'>"
				 		 + "<input name='itemDesc' type='hidden' value='" + itemDesc + "'>"
				 		 + "</form></td>"
						 + "<td><form method='post' action='items.jsp'>"
						 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						 + "<input name='itemID' type='hidden' value='" + itemID +"'>" 
						 + "</form></td></tr>"; 
			}
			con.close();
			// Complete the html table
			output += "</table>";
	 
	}
	catch (Exception e)
	{
		output = "Error while reading the one item.";
		System.err.println(e.getMessage());
	}
	return output;
	}
}
