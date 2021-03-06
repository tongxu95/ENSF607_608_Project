package DBController;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

public class CustomerDBController extends DBController {

	/**
	 * Add client encoded as JSONObject to the database.
	 * 
	 * @param newCustomer new client info
	 */
	public void addCustomer(JSONObject newCustomer) throws SQLException {
		int id = generateNewID();
		String sql = "INSERT INTO CLIENT VALUES(?,?,?,?,?,?,?);";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setString(2, newCustomer.getString("LName"));
			stmt.setString(3, newCustomer.getString("FName"));
			stmt.setString(4, newCustomer.getString("Type"));
			stmt.setString(5, newCustomer.getString("Phone"));
			stmt.setString(6, newCustomer.getString("Address"));
			stmt.setString(7, newCustomer.getString("PostalCode"));
			stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return id for a new client.
	 * 
	 * @return new tool id
	 */
	public Integer generateNewID() {
		// generate unique tool id for new tool
		String queryMaxID = "SELECT MAX(ClientID) FROM CLIENT;";
		try {
			stmt = conn.prepareStatement(queryMaxID);
			rs = stmt.executeQuery();

			if (rs.next())
				return rs.getInt("MAX(ClientID)") + 1;
			else
				return 2000;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Update client info for given client.
	 * 
	 * @param client JSONObject with client info
	 */
	public void modifyInfo(JSONObject client) {
		try {
			String sql = "UPDATE CLIENT SET LName=?, FName=?, Type=?, Phone=?, Address=?, PostalCode=? WHERE ClientID=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, client.getString("LName"));
			stmt.setString(2, client.getString("FName"));
			stmt.setString(3, client.getString("Type"));
			stmt.setString(4, client.getString("Phone"));
			stmt.setString(5, client.getString("Address"));
			stmt.setString(6, client.getString("PostalCode"));
			stmt.setInt(7, client.getInt("ClientID"));
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete client with given id from the database.
	 * 
	 * @param id client id
	 */
	public void removeCustomer(int ID) {
		String sql = "DELETE FROM CLIENT WHERE ClientID=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Search the database for client with given id.
	 * 
	 * @param id client id
	 * @return ResultSet with data of the client of the given tool type or null if
	 *         not found.
	 */
	public ResultSet searchByID(int ID) {
		String sql = "SELECT * FROM CLIENT WHERE ClientID=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ID);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Search the database for client with given first and last name.
	 * 
	 * @param LName last name
	 * @param FName first name
	 * @return ResultSet with the client with the given name or null if not found.
	 */
	public ResultSet searchByName(String LName) {
		String sql = "SELECT * FROM CLIENT WHERE LOWER(LName)=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, LName);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Search the database for client of the given type.
	 * 
	 * @param type client type (residential or commercial)
	 * @return ResultSet with data of clients of a given type.
	 */
	public ResultSet searchByType(String type) {
		String sql = "SELECT * FROM CLIENT WHERE LOWER(Type)=?;";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, type);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getAllCustomers() {
		String sql = "SELECT * FROM CLIENT;";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			System.out.print(rs.toString());

			if (rs.next())
				return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// public static void main(String[] args) {
	// try {
	// CustomerDBController c = new CustomerDBController();
	//
	// JSONObject obj = new JSONObject();
	// obj.put("ClientID", 2005);
	// obj.put("LName", "Cartwright");
	// obj.put("FName", "Terry");
	// obj.put("Type", "Residential");
	// obj.put("Phone", "250-565-3807");
	// obj.put("Address", "610 Carlson Road, Prince George, BC");
	// obj.put("PostalCode", "V2L 5E5");
	// c.addCustomer(obj);
	//
	// c.modifyInfo(2000, "FName", "Bob");
	//
	// c.removeCustomer(2000);
	//
	// ResultSet rs = c.searchByID(2000);
	// System.out.println(rs.getString("ClientID"));
	// System.out.println(rs.getString("LName"));
	// System.out.println(rs.getString("FName"));
	// System.out.println(rs.getString("Type"));
	// System.out.println(rs.getString("Phone"));
	// System.out.println(rs.getString("Address"));
	// System.out.println(rs.getString("PostalCode"));
	//
	// ResultSet rs = c.searchByName("ken", "graves");
	// System.out.println(rs.getString("ClientID"));
	// System.out.println(rs.getString("LName"));
	// System.out.println(rs.getString("FName"));
	// System.out.println(rs.getString("Type"));
	// System.out.println(rs.getString("Phone"));
	// System.out.println(rs.getString("Address"));
	// System.out.println(rs.getString("PostalCode"));
	//
	// ResultSet rs = c.searchByType("residential");
	// do {
	// System.out.println(rs.getString("ClientID"));
	// System.out.println(rs.getString("LName"));
	// System.out.println(rs.getString("FName"));
	// System.out.println(rs.getString("Type"));
	// System.out.println(rs.getString("Phone"));
	// System.out.println(rs.getString("Address"));
	// System.out.println(rs.getString("PostalCode"));
	// } while(rs.next());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

}
