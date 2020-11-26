package ModelController;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;

import Client.ClientController.Message;
import Client.ClientController.ClientServerConstants;
import DBController.InventoryDBController;
import InventoryModel.Inventory;

public class InventoryController extends ModelController implements ClientServerConstants {

	private InventoryDBController inventoryDB;
	private Inventory model;
	
	public InventoryController(InventoryDBController inventoryDB) {
		this.inventoryDB = inventoryDB;
		model = new Inventory();
	}

	@Override
	public Message notify(Message data) {
		try {
			if (! data.getString(DB).equals(INVENTORY)) return null;
			if (data.getString(MESSAGE_TYPE).equals(REQUEST)) {
				return processMessage(data);
			}
		} catch (JSONException jse) {
			jse.printStackTrace();
		}
		return null;
	}

	public Message processMessage(Message data) {
		try {
			Message response = null;
			switch(data.getString(VERB)) {
				case POST:
					response = addTool(data);
					break;
				case GET:
					response = searchTool(data);
					break;
				case PUT:
					response = makeSale(data);
					break;
				case DELETE:
					response = deleteTool(data);
					break;
			}
			return response;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Message searchTool(Message data) {
		try {
			String query = data.getString(QUERY);
			ResultSet rs = null;
			switch(query) {
				case BY_ID:
					rs = inventoryDB.searchToolbyID(Integer.parseInt(data.getString(DATA)));
					break;
				case BY_NAME:
					rs = inventoryDB.searchToolbyName(data.getString(DATA));
					break;
				case BY_TYPE:
					rs = inventoryDB.searchToolbyType(data.getString(DATA));
					break;
				case ALL:
					rs = inventoryDB.getInventory();
			}
			Message response;
			if (rs == null) {
				String errorMessage = "Tool(s) not found!";
				response = new Message(RESPONSE, ERROR, errorMessage);
			} else {
				response = new Message(RESPONSE, OK, model.encodeSearchQuery(rs));
			}
			return response;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Message makeSale(Message data) {
		// TODO: Generate orderline
		Message response = null;
		try {
			try {
				int id = data.getJSONObject(DATA).getInt("ToolID");
				int qtyInStock = data.getJSONObject(DATA).getInt("ToolID");
				inventoryDB.reduceToolQuantity(id, qtyInStock);
				String successMessage = "Quantity updated successfully.";
				response = new Message(RESPONSE, OK, successMessage);
			} catch (SQLException sqlE) {
				String errorMessage = "Exceeded quantity in stock. Please try with smaller quantity.";
				response = new Message(RESPONSE, ERROR, errorMessage);
			} 
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
	}

	private Message addTool(Message data) {
		Message response = null;
		try {
			try {
				inventoryDB.addTool(data.getJSONObject(DATA));
				String successMessage = "Tool added successfully.";
				response = new Message(RESPONSE, OK, successMessage);
			} catch (SQLException sqlE) {
				String errorMessage = "Invalid ToolID, use " + inventoryDB.generateNewID() + ".";
				response = new Message(RESPONSE, ERROR, errorMessage);
			} 
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
	}


	private Message deleteTool(Message data) {
		Message response = null;
		try {
			inventoryDB.deleteToolbyID(data.getJSONObject(DATA).getInt("ToolId"));
			String successMessage = "Tool deleted successfully.";
			response = new Message(RESPONSE, OK, successMessage);
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
	}
	
}
