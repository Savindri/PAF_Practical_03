<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ page import="com.Item" %>

<%
	//Update item details----------------------------------
	if (request.getParameter("itemID") != null && 
		request.getParameter("itemCode") != null && 
		request.getParameter("itemName") != null &&
		request.getParameter("itemPrice") != null && 
		request.getParameter("itemDesc") != null) {
		
			Item itemObj = new Item();
			
			String stsMsg = itemObj.updateItem(request.getParameter("itemID"), 
												request.getParameter("itemCode"), 
						                        request.getParameter("itemName"),
						                        request.getParameter("itemPrice"), 
						                        request.getParameter("itemDesc") );
			
			session.setAttribute("statusMsg", stsMsg);		
	}
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Views/bootstrap.min.css">
<title>Items Management</title>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col">
			
				<h1>Items Management</h1>
		
				<form method="post" action="items.jsp">		
					Item code: <input name="itemCode" type="text" required class="form-control"><br> 
					Item name: <input name="itemName" type="text" required class="form-control"><br>
					Item price: <input name="itemPrice" type="text" required class="form-control"><br> 
					Item description: <input name="itemDesc" type="text" required class="form-control"><br>
					<input name="btnSubmit" type="submit" value="Save">
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
							
				
			</div>
		</div>
	</div>

	
</body>
</html>