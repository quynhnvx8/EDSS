
package eone.rest.api.v1.resource.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import eone.base.model.MColumn;
import eone.base.model.MRefList;
import eone.base.model.MRefTable;
import eone.base.model.MReference;
import eone.base.model.MTable;
import eone.base.model.PO;
import eone.base.model.Query;
import eone.rest.api.json.IPOSerializer;
import eone.rest.api.json.TypeConverterUtils;
import eone.rest.api.util.ErrorBuilder;
import eone.rest.api.v1.resource.ReferenceResource;
import eone.util.Env;
import eone.util.ValueNamePair;

/**
 * 
 * @author druiz
 *
 */
public class ReferenceResourceImpl implements ReferenceResource {

	public ReferenceResourceImpl() {
	}

	@Override
	public Response getList(String refID) {

		MReference ref = (MReference) TypeConverterUtils.getPO(MReference.Table_Name, refID, false, false);

		if (ref == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid reference id").append("No match found for AD_Reference_ID: ").append(refID).build().toString())
					.build();
    	} else if (MReference.VALIDATIONTYPE_ListValidation.equals(ref.getValidationType())) {
        	IPOSerializer serializer = IPOSerializer.getPOSerializer(MReference.Table_Name, MTable.getClass(MReference.Table_Name));
        	JsonObject referenceJsonObject = serializer.toJson(ref, new String[] {"AD_Reference_ID", "AD_Reference_UU", "Name", "Description", "Help", "ValidationType", "VFormat"}, null);
        	JsonArray refListArray = new JsonArray();
        	for(ValueNamePair refList : MRefList.getList(Env.getCtx(), ref.getAD_Reference_ID(), false)) {
        		JsonObject json = new JsonObject();
    			json.addProperty("value", refList.getValue());
    			json.addProperty("name", refList.getName());
    			refListArray.add(json);
        	}
        	referenceJsonObject.add("reflist", refListArray);

        	return Response.ok(referenceJsonObject.toString()).build();
    	} else if (MReference.VALIDATIONTYPE_TableValidation.equals(ref.getValidationType())) {
    		
    		MRefTable refTable = MRefTable.get(Env.getCtx(), ref.getAD_Reference_ID());
    		if (refTable == null || refTable.get_ID() == 0)
    			return Response.status(Status.NOT_FOUND)
    					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid reference table id").append("No ref table match found for AD_Reference_ID: ").append(refID).build().toString())
    					.build();

    		MTable table = new MTable(Env.getCtx(), refTable.getAD_Table_ID(), null);
    		Query query = new Query(Env.getCtx(), table, refTable.getWhereClause(), null);
    		List<PO> list = query
    				.setApplyAccessFilter(true, false)
    				.setOnlyActiveRecords(true)
    				.setOrderBy(refTable.getOrderByClause())
    				.list();
    		
        	IPOSerializer serializer = IPOSerializer.getPOSerializer(MReference.Table_Name, MTable.getClass(table.getTableName()));
        	JsonArray array = new JsonArray();
    		if (list != null && !list.isEmpty()) {
            	ArrayList<String> includes = new ArrayList<String>();
            	
            	includes.add(MColumn.getColumnName(Env.getCtx(), refTable.getAD_Key()));
           		includes.add(MColumn.getColumnName(Env.getCtx(), refTable.getAD_Display()));
            	if (refTable.isValueDisplayed())
            		includes.add("Value");

    			for (PO po : list) {
    				JsonObject json = serializer.toJson(po, includes.toArray(new String[includes.size()]), null);
    				array.add(json);
    			}
    		}

			JsonObject json = new JsonObject();
			json.add("reftable", array);
    		return Response.ok(json.toString()).build();
    	} else {
    		//Reference DataValidation not implemented
    		return Response.status(Status.NOT_IMPLEMENTED)
    				.entity(new ErrorBuilder().status(Status.NOT_IMPLEMENTED).title("References with data validation are not implemented.").append("Not implemented AD_Reference_ID: ").append(refID).build().toString())
    				.build();
    	}
	}

}