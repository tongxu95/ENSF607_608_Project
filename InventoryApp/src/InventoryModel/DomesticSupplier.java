package InventoryModel;

import org.json.JSONException;
import org.json.JSONObject;

public class DomesticSupplier extends Supplier {
    private String type;

    public DomesticSupplier(int supplierID, String name, String address, String cName, String phone, String type) {
        super(supplierID, name, address, cName, phone);
        setType(type);
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

    public String toDescriptionString() {
        // TODO: Add type info?
        return super.toDescriptionString();
    }
}