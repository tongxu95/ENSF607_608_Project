package Client.CommonModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

public class CommercialCustomer extends Customer {
    String type;

    public CommercialCustomer(int clientId, String fName, String lName, String address, String postal, String phone) {
        super(clientId, fName, lName, address, postal, phone);
        setType("Commercial");
    }

    public CommercialCustomer(ResultSet rs) {
        super();
        try {
            clientId = rs.getInt("ClientID");
            fName = rs.getString("FName");
            lName = rs.getString("LName");
            address = rs.getString("Address");
            phone = rs.getString("Phone");
            postalCode = rs.getString("PostalCode");
            type = "Commercial";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CommercialCustomer(JSONObject jsonObject) {
        super();
        try {
            clientId = jsonObject.getInt("ClientID");
            fName = jsonObject.getString("FName");
            lName = jsonObject.getString("LName");
            address = jsonObject.getString("Address");
            phone = jsonObject.getString("Phone");
            postalCode = jsonObject.getString("PostalCode");
            type = "Commercial";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void putFields() {
        try {
            super.putFields();
            put("Type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject encode() {
        super.putFields();
        putFields();
        return this;
    }

    @Override
    public String toDescriptionString() {
        String superString = super.toDescriptionString();
        return superString + ", Type: " + String.format("%-15s", type);
    }

}
