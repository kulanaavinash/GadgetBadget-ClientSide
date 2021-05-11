package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gadgetbadget_clientside", "root", "");

			// For testing
			System.out.print("Successfully connected");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String readCustomer() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Customer Name</th>" + "<th>Customer Email</th><th>Customer Type</th>"
					+ "<th>Customer Contact</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from customer";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {

				String customerID = Integer.toString(rs.getInt("customerID"));
				String customerName = rs.getString("customerName");
				String customerEmail = rs.getString("customerEmail");
				String customerType = rs.getString("customerType");
				String customerContact = Integer.toString(rs.getInt("customerContact"));

				// Add into the html table

				output += "<tr><td><input id='hidcustomerIDUpdate' name='hidcustomerIDUpdate' type='hidden' value='"
						+ customerID + "'>" + customerName + "</td>";

				output += "<td>" + customerEmail + "</td>";
				output += "<td>" + customerType + "</td>";
				output += "<td>" + customerContact + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-customerID='"
						+ customerID + "'>" + "</td></tr>";

			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Customer Details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Insert Customer
	public String insertCustomer(String customerName, String customerEmail, String customerType,
			String customerContact) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into customer (`customerID`,`customerName`,`customerEmail`,`customerType`,`customerContact`)"
					+ " values (?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, customerName);
			preparedStmt.setString(3, customerEmail);
			preparedStmt.setString(4, customerType);
			preparedStmt.setString(5, customerContact);

			// execute the statement
			preparedStmt.execute();
			con.close();

			// Create JSON Object to show successful msg.
			String newCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";
		} catch (Exception e) {
			// Create JSON Object to show Error msg.
			output = "{\"status\":\"error\", \"data\": \"Error while Inserting Customer.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// Update Customer
	public String updateCustomer(String customerID, String customerName, String customerEmail, String customerType,
			String customerContact) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE customer SET customerName=?,customerEmail=?,customerType=?,customerContact=? WHERE customerID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, customerName);
			preparedStmt.setString(2, customerEmail);
			preparedStmt.setString(3, customerType);
			preparedStmt.setInt(4, Integer.parseInt(customerContact));
			preparedStmt.setInt(5, Integer.parseInt(customerID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			// create JSON object to show successful msg
			String newCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while Updating Customer Details.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteCustomer(String customerID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "DELETE FROM customer WHERE customerID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(customerID));
			// execute the statement
			preparedStmt.execute();
			con.close();

			// create JSON Object
			String newCustomer = readCustomer();
			output = "{\"status\":\"success\", \"data\": \"" + newCustomer + "\"}";
		} catch (Exception e) {
			// Create JSON object
			output = "{\"status\":\"error\", \"data\": \"Error while Deleting Customer.\"}";
			System.err.println(e.getMessage());

		}

		return output;
	}

}
