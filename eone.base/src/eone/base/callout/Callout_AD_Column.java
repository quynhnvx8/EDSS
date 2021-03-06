
package eone.base.callout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.GridTabWrapper;
import eone.base.model.I_AD_Column;
import eone.base.model.MColumn;
import eone.base.model.M_Element;
import eone.base.model.PO;
import eone.exceptions.DBException;
import eone.util.DB;
import eone.util.DisplayType;


public class Callout_AD_Column extends CalloutEngine
{
	public String columnName (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{ 
		I_AD_Column column = GridTabWrapper.create(mTab, I_AD_Column.class);
		if (MColumn.isSuggestSelectionColumn(column.getColumnName(), true))
			column.setIsSelectionColumn(true);

		if (PO.getUUIDColumnName(column.getAD_Table().getTableName()).equals(column.getColumnName())) {
			// UUID column
			column.setAD_Reference_ID(DisplayType.String);
			column.setAD_Val_Rule_ID(0);
			column.setAD_Reference_Value_ID(0);
			column.setFieldLength(36);
			column.setDefaultValue(null);
			column.setMandatoryLogic(null);
			column.setReadOnlyLogic(null);
			column.setIsIdentifier(false);
			column.setIsUpdateable(false);
			column.setIsAlwaysUpdateable(false);
			column.setIsKey(false);
		} else if (column.getAD_Table().getTableName().concat("_ID").equals(column.getColumnName())) {
			// ID key column
			column.setAD_Reference_ID(DisplayType.ID);
			column.setAD_Val_Rule_ID(0);
			column.setAD_Reference_Value_ID(0);
			column.setFieldLength(22);
			column.setDefaultValue(null);
			column.setMandatoryLogic(null);
			column.setReadOnlyLogic(null);
			column.setIsIdentifier(false);
			column.setIsUpdateable(false);
			column.setIsAlwaysUpdateable(false);
			column.setIsKey(true);
		} else {
			// get defaults from most used case
			String sql = ""
					+ "SELECT AD_Reference_ID, "
					+ "       AD_Val_Rule_ID, "
					+ "       AD_Reference_Value_ID, "
					+ "       FieldLength, "
					+ "       DefaultValue, "
					+ "       MandatoryLogic, "
					+ "       ReadOnlyLogic, "
					+ "       IsIdentifier, "
					+ "       IsUpdateable, "
					+ "       IsAlwaysUpdateable, "
					+ "       FKConstraintType,"	
					+ "       COUNT(*) "
					+ "FROM   AD_Column "
					+ "WHERE  ColumnName = ? "
					+ "GROUP  BY AD_Reference_ID, "
					+ "          AD_Val_Rule_ID, "
					+ "          AD_Reference_Value_ID, "
					+ "          FieldLength, "
					+ "          DefaultValue, "
					+ "          MandatoryLogic, "
					+ "          ReadOnlyLogic, "
					+ "          IsIdentifier, "
					+ "          IsUpdateable, "
					+ "          IsAlwaysUpdateable, "
					+ "          FKConstraintType "
					+ "ORDER  BY COUNT(*) DESC ";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setString(1, column.getColumnName());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					int ad_reference_id = rs.getInt(1);
					if (ad_reference_id == DisplayType.ID)
						ad_reference_id = DisplayType.TableDir;
					column.setAD_Reference_ID(ad_reference_id);
					column.setAD_Val_Rule_ID(rs.getInt(2));
					column.setAD_Reference_Value_ID(rs.getInt(3));
					column.setFieldLength(rs.getInt(4));
					column.setDefaultValue(rs.getString(5));
					column.setMandatoryLogic(rs.getString(6));
					column.setReadOnlyLogic(rs.getString(7));
					column.setIsIdentifier("Y".equals(rs.getString(8)));
					column.setIsUpdateable("Y".equals(rs.getString(9)));
					column.setIsAlwaysUpdateable("Y".equals(rs.getString(10)));
					column.setFKConstraintType(rs.getString(11));
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}

		return "";
	}

	public String element (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		I_AD_Column column = GridTabWrapper.create(mTab, I_AD_Column.class);
		if (column.getAD_Element_ID() > 0) {
			M_Element element = new M_Element(ctx, column.getAD_Element_ID(), null);
			column.setColumnName(element.getColumnName());
			column.setName(element.getName());
			column.setDescription(element.getDescription());
			column.setPlaceholder(element.getPlaceholder());
		}

		return "";
	}

}
