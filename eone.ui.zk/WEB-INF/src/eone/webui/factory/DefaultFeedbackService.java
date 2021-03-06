/******************************************************************************
 * Copyright (C) 2013 Heng Sin Low                                            *
 * Copyright (C) 2013 Trek Global                 							  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package eone.webui.factory;

import javax.xml.bind.DatatypeConverter;

import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;

import eone.util.ByteArrayDataSource;
import eone.util.Env;
import eone.util.Msg;
import eone.webui.apps.AEnv;
import eone.webui.apps.FeedbackRequestWindow;
import eone.webui.session.SessionManager;

/**
 * @author hengsin
 *
 */
public class DefaultFeedbackService implements IFeedbackService {

	/**
	 * default constructor
	 */
	public DefaultFeedbackService() {
	}

	/* (non-Javadoc)
	 */
	@Override
	public void emailSupport(boolean errorOnly) {
		new EmailSupportAction(errorOnly);
	}

	/* (non-Javadoc)
	 */
	@Override
	public void createNewRequest() {
		new CreateNewRequestAction();
	}

	protected static class EmailSupportAction implements EventListener<Event>{

		
		protected EmailSupportAction(boolean errorOnly) {
			SessionManager.getAppDesktop().getComponent().addEventListener("onEmailSupport", this);
			
			String script = "html2canvas(document.body, { onrendered: function(canvas) " +
					"{ var dataUrl = canvas.toDataURL();" +
					"  var widget = zk.Widget.$('#" + SessionManager.getAppDesktop().getComponent().getUuid()+"');"+
		    		"  var event = new zk.Event(widget, 'onEmailSupport', dataUrl, {toServer: true});" +
		    		"  zAu.send(event); } " +
		    		"});";
			Clients.response(new AuScript(script));
		}
		
		@Override
		public void onEvent(Event event) throws Exception {
			SessionManager.getAppDesktop().getComponent().removeEventListener("onEmailSupport", this);
			String dataUrl = (String) event.getData();
			byte[] imageBytes = null;
			if (dataUrl != null && dataUrl.startsWith("data:image/png;base64,"))
			{
				try {
		            // remove data:image/png;base64, and then take rest sting
		            String img64 = dataUrl.substring("data:image/png;base64,".length()).trim();
			        imageBytes = DatatypeConverter.parseBase64Binary(img64 );			        
			    } catch(Exception e) {  			              
			    }
			}
			showEmailDialog(imageBytes);
		}
		
		protected String getFeedbackSubject() {
			String feedBackHeader = Msg.getMsg(Env.getCtx(), "FeedBackHeader");
			return Env.parseContext(Env.getCtx(), 0, feedBackHeader, false, false);
		}
		
		protected void showEmailDialog(byte[] imageBytes) {
			
		}
	}
	
	protected static class CreateNewRequestAction implements EventListener<Event>{
		protected CreateNewRequestAction() {
			SessionManager.getAppDesktop().getComponent().addEventListener("onCreateFeedbackRequest", this);
			
			String script = "html2canvas(document.body, { onrendered: function(canvas) " +
					"{ var dataUrl = canvas.toDataURL();" +
					"  var widget = zk.Widget.$('#" + SessionManager.getAppDesktop().getComponent().getUuid()+"');"+
		    		"  var event = new zk.Event(widget, 'onCreateFeedbackRequest', dataUrl, {toServer: true});" +
		    		"  zAu.send(event); } " +
		    		"});";
			Clients.response(new AuScript(script));
		}
		
		@Override
		public void onEvent(Event event) throws Exception {
			SessionManager.getAppDesktop().getComponent().removeEventListener("onCreateFeedbackRequest", this);
			String dataUrl = (String) event.getData();
			byte[] imageBytes = null;
			if (dataUrl != null && dataUrl.startsWith("data:image/png;base64,"))
			{
				try {
		            // remove data:image/png;base64, and then take rest sting
		            String img64 = dataUrl.substring("data:image/png;base64,".length()).trim();
			        imageBytes = DatatypeConverter.parseBase64Binary(img64 );			        
			    } catch(Exception e) {  			              
			    }
			}
			showRequestDialog(imageBytes);
		}
		
		protected void showRequestDialog(byte[] imageBytes) {
			FeedbackRequestWindow window = new FeedbackRequestWindow();
			AEnv.showWindow(window);
			
			if (imageBytes != null && imageBytes.length > 0) {
				ByteArrayDataSource screenShot = new ByteArrayDataSource(imageBytes, "image/png");
				screenShot.setName("screenshot.png");
				window.addAttachment(screenShot, true);
			}
			window.focus();
		}
	}
}
