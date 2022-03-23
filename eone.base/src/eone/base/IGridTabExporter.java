
package eone.base;

import java.io.File;
import java.util.List;

import eone.base.model.GridTab;

/**
 *
 * @author Quynhnv.x8 mode 10/03/2022
 *
 */
public interface IGridTabExporter {

	public void export(GridTab gridTab, List<GridTab> childs, boolean isCurrentRowOnly, File file, int indxDetailSelected);

	public String getFileExtension();

	public String getFileExtensionLabel();

	public String getContentType();

	public String getSuggestedFileName(GridTab gridTab);
	
	public boolean isExportableTab (GridTab gridTab);
}
