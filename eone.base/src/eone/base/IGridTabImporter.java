
package eone.base;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import eone.base.model.GridTab;
import eone.util.IProcessUI;

/**
 *
 * @author Carlos Ruiz
 *
 */
public interface IGridTabImporter {

	public File fileImport(GridTab gridTab, List<GridTab> childs, InputStream filestream, Charset charset, String importMode);
	
	public File fileImport(GridTab gridTab, List<GridTab> childs, InputStream filestream, Charset charset, String importMode, IProcessUI processUI);

	public String getFileExtension();

	public String getFileExtensionLabel();

	public String getContentType();

	public String getSuggestedFileName(GridTab gridTab);
}
