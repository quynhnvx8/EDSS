
package net.sf.compilo.report;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 	@author 	Peter Shen
 * 	@version 	$Id: FileFilter.java,v 1.2 2005/07/16 02:39:17 pshen Exp $
 **/
public class FileFilter implements FilenameFilter
    {
	    private String reportStart;
        private File directory;
        private String extension[];

        public FileFilter(String reportStart, File directory, String[] extension)
        {
        	this.reportStart = reportStart;
            this.directory = directory;
            this.extension = extension;
        }

        public boolean accept(File file, String name)
        {    	
            if (file.equals( directory))
            {
               	if (name.startsWith( reportStart))
                {
               	    for(int i=0; i<extension.length; i++)
               	    {
               	        int pos = name.lastIndexOf(extension[i]);
               	        if ( (pos!=-1) && (pos==(name.length() - extension[i].length()))) 
               	            return true;
               	    }
                }   
            }
            return false;
        }
    }
