package InventoryModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represent a tool item and keeps track of its id, name, quantity in stock,
 * price, and supplier id.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since Nov 26, 2020
 */
public class Tool extends JSONObject {

	/**
	 * Construct a Tool object with the specified information
	 * 
	 * @param id       tool id
	 * @param name     description or name of tool
	 * @param tool     type (electrical or non-electrical)
	 * @param qty      quantity in stock
	 * @param price    unit price
	 * @param supplier Supplier object
	 */
	public Tool(int id, String name, String type, int qty, double price, int supplier) {
		try {
			put("ToolID", id);
			put("Name", name);
			put("Type", type);
			put("Quantity", qty);
			put("Price", price);
			put("SupplierID", supplier);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

    // TODO: Move to tool
    public String getToolString(JSONObject jsonTool) throws JSONException {
        int toolId = jsonTool.getInt("ToolID");
        String name = jsonTool.getString("Name");
        int stock = jsonTool.getInt("Quantity");
        Double price = jsonTool.getDouble("Price");
        int supplierID = jsonTool.getInt("SupplierID");
        String toolType = jsonTool.getString("Type");
        return String.format("ToolID: %d, Name: %14s, Stock: %4d, Price: %6.2f, SupplierID: %5d, Tool Type: %10s\n",
                toolId, name, stock, price, supplierID, toolType);

    }
	
	public JSONObject encode() {
		return this;
	}

	// // TODO: Move to tool
	// public String getToolString(JSONObject jsonTool) throws JSONException {
	// int toolId = jsonTool.getInt("ToolID");
	// String name = jsonTool.getString("Name");
	// int stock = jsonTool.getInt("Quantity");
	// Double price = jsonTool.getDouble("Price");
	// int supplierID = jsonTool.getInt("SupplierID");
	// String toolType = jsonTool.getString("Type");
	// return String.format("ToolID: %d, Name: %14s, Stock: %4d, Price: %6.2f,
	// SupplierID: %5d, Tool Type: %10s\n",
	// toolId, name, stock, price, supplierID, toolType);

	// }
}
