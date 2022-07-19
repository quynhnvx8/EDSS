
package eone.rest.api.v1.resource.impl;

import java.io.File;
import java.util.logging.Level;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eone.distributed.IClusterMember;
import eone.distributed.IClusterService;
import eone.rest.api.util.ClusterUtil;
import eone.rest.api.util.ErrorBuilder;
import eone.rest.api.v1.resource.FileResource;
import eone.rest.api.v1.resource.file.FileAccess;
import eone.rest.api.v1.resource.file.FileInfo;
import eone.rest.api.v1.resource.file.FileStreamingOutput;
import eone.rest.api.v1.resource.file.GetFileInfoCallable;
import eone.rest.api.v1.resource.file.RemoteFileStreamingOutput;
import eone.util.CLogger;
import eone.util.Util;

/**
 * 
 * @author hengsin
 *
 */
public class FileResourceImpl implements FileResource {
	
	protected static final int BLOCK_SIZE = 1024 * 1024 * 5;
	private static final CLogger log = CLogger.getCLogger(FileResourceImpl.class);
	
	public FileResourceImpl() {
	}

	@Override
	public Response getFile(String fileName, long length, String nodeId) {
		if (Util.isEmpty(nodeId)) {
			return getLocalFile(fileName, true, length);
		} else {
			IClusterService service = ClusterUtil.getClusterService();
			if (service == null) 
				return getLocalFile(fileName, true, length);
			
			IClusterMember local = service.getLocalMember();
			if (local != null && local.getId().equals(nodeId))
				return getLocalFile(fileName, true, length);
			
			return getRemoteFile(fileName, true, length, nodeId);
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param nodeId
	 * @return response
	 */
	public Response getFile(String fileName, String nodeId) {
		if (Util.isEmpty(nodeId)) {
			return getLocalFile(fileName, false, 0);
		} else {
			IClusterService service = ClusterUtil.getClusterService();
			if (service == null) 
				return getLocalFile(fileName, false, 0);
			
			IClusterMember local = service.getLocalMember();
			if (local != null && local.getId().equals(nodeId))
				return getLocalFile(fileName, false, 0);
			
			return getRemoteFile(fileName, false, 0, nodeId);
		}
	}
	
	private Response getLocalFile(String fileName, boolean verifyLength, long length) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (!file.canRead() || !FileAccess.isAccessible(file)) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("File not readable").append("File not readable: ").append(fileName).build().toString())
						.build();
			} else if (!verifyLength || file.length()==length) {
				String contentType = null;
				String lfn = fileName.toLowerCase();
				if (lfn.endsWith(".html") || lfn.endsWith(".htm")) {
					contentType = MediaType.TEXT_HTML;
				} else if (lfn.endsWith(".csv") || lfn.endsWith(".ssv") || lfn.endsWith(".log")) {
					contentType = MediaType.TEXT_PLAIN;
				} else {
					MimetypesFileTypeMap map = new MimetypesFileTypeMap();
					contentType = map.getContentType(file);
				}
				if (Util.isEmpty(contentType, true))
					contentType = MediaType.APPLICATION_OCTET_STREAM;
				
				FileStreamingOutput fso = new FileStreamingOutput(file);
				return Response.ok(fso, contentType).build();
			} else {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for file: ").append(fileName).build().toString())
						.build();
			}
		} else {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("File not found").append("File not found: ").append(fileName).build().toString())
					.build();
		}
	}

	private Response getRemoteFile(String fileName, boolean verifyLength, long length, String nodeId) {
		IClusterService service = ClusterUtil.getClusterService();
		IClusterMember member = ClusterUtil.getClusterMember(nodeId);
		if (member == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid Node Id").append("No match found for node id: ").append(nodeId).build().toString())
					.build(); 
		}
		
		try {
			GetFileInfoCallable infoCallable = new GetFileInfoCallable(null, fileName, BLOCK_SIZE);
			FileInfo fileInfo = service.execute(infoCallable, member).get();
			if (fileInfo == null) {
				return Response.status(Status.NOT_FOUND)
						.entity(new ErrorBuilder().status(Status.NOT_FOUND).title("Invalid File Name").append("File does not exists or not readable: ").append(fileName).build().toString())
						.build(); 
			}
			if (verifyLength && length != fileInfo.getLength()) {
				return Response.status(Status.FORBIDDEN)
						.entity(new ErrorBuilder().status(Status.FORBIDDEN).title("Access denied").append("Access denied for file: ").append(fileName).build().toString())
						.build();
			}
			
			String contentType = null;
			String lfn = fileName.toLowerCase();
			if (lfn.endsWith(".html") || lfn.endsWith(".htm")) {
				contentType = MediaType.TEXT_HTML;
			} else if (lfn.endsWith(".csv") || lfn.endsWith(".ssv") || lfn.endsWith(".log")) {
				contentType = MediaType.TEXT_PLAIN;
			} else {
				MimetypesFileTypeMap map = new MimetypesFileTypeMap();
				contentType = map.getContentType(fileInfo.getFileName());
			}
			if (Util.isEmpty(contentType, true))
				contentType = MediaType.APPLICATION_OCTET_STREAM;
			
			RemoteFileStreamingOutput rfso = new RemoteFileStreamingOutput(fileInfo, member);
			return Response.ok(rfso, contentType).build();
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new ErrorBuilder().status(Status.INTERNAL_SERVER_ERROR).title("Server error").append("Server error with exception: ").append(ex.getMessage()).build().toString())
					.build();
		}
	}			
}
