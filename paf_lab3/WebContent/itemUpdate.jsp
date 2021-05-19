<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ page import="com.Item" %>

<%
	//Retrieving the values from items.jsp----------------------------------
	String itemID = request.getParameter("itemID");
	String itemCode = request.getParameter("itemCode");
	String itemName = request.getParameter("itemName");
	String itemPrice = request.getParameter("itemPrice");
	String itemDesc = request.getParameter("itemDesc");	
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Views/bootstrap.min.css">
<title>Update item</title>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col">
			
				<h1>Update item details</h1>
		
				<form method="post" action="itemUpdated.jsp">		
				   <input name="itemID" type="hidden" value="<%=itemID%>"> 
					Item code: <input name="itemCode" type="text" value="<%=itemCode%>"required class="form-control"><br> 
					Item name: <input name="itemName" type="text" value="<%=itemName%>"required class="form-control"><br>
					Item price: <input name="itemPrice" type="text" value="<%=itemPrice%>"required class="form-control"><br> 
					Item description: <input name="itemDesc" type="text" value="<%=itemDesc%>"required class="form-control"><br>
						  <input name="btnSubmit" type="submit" value="Update">
						  <input name='itemID' type='hidden' value="itemID">
						  <input name='itemCode' type='hidden' value="itemCode"> 
						  <input name='itemName' type='hidden' value="itemName"> 
						  <input name='itemPrice' type='hidden' value="itemPrice"> 
						  <input name='itemDesc' type='hidden' value="itemDesc">
				</form>
		
				<br>
				<div class="alert alert-success">
						<% 
							out.print(session.getAttribute("statusMsg"));
						%>
				</div>
				<hr>
				<br>
				
				<%
					Item itemObj = new Item();
					out.print(itemObj.readItems());
				%>				
				
				<hr>
				<br>
			
			</div>
		</div>
	</div>

	
	
</body>
</html>