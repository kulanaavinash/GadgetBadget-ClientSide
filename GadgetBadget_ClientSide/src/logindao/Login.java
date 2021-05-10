package logindao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
	final String GET_ONE = "SELECT * FROM logins WHERE log_uname=? AND log_password=?";

	private DBConnection connection = DBConnection.getInstance();

	public boolean authentication(String userName, String password) {
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		try {
			preparedStatement = connection.getConnection().prepareStatement(GET_ONE);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			connection.closeConnection();
		}

		return false;
	}

}
