if (typeof window.eone === 'undefined')
	window.eone = {};
window.eone.zoom = function (){
	
	cmpid = parent.arrayData[0]; 
	tableLink = parent.arrayData[1];
	zoomLogic = parent.arrayData[2];
	value = parent.arrayData[3];
	zAu.cmd0.showBusy(null);
	var widget = zk.Widget.$(cmpid);
	var event = new zk.Event(widget, 'onZoom', {data: [tableLink, value, zoomLogic]}, {toServer: true});
	zAu.send(event);
}

window.eone.zoom = function (e){
	var cmpid = e.target.getAttribute ("componentId");
	var tableLink = e.target.getAttribute ("tableLink");
	var zoomLogic = e.target.getAttribute ("zoomLogic");
	var value = e.target.getAttribute ("value");
	
	zAu.cmd0.showBusy(null);
	var widget = zk.Widget.$(cmpid);
	var event = new zk.Event(widget, 'onZoom', {data: [tableLink, value, zoomLogic]}, {toServer: true});
	zAu.send(event);
}

window.eone.zoomWindow = function (cmpid, column, value, windowuu){
	zAu.cmd0.showBusy(null);
	var widget = zk.Widget.$(cmpid);
	var event = new zk.Event(widget, 'onZoom', {data: [column, value, 'AD_Window_UU', windowuu]}, {toServer: true});
	zAu.send(event);
}

var arrayData = null;

window.eone.showColumnMenu = function (doc, e) {
	var compid = e.target.getAttribute ("componentId");
	var tableLink = e.target.getAttribute ("tableLink");
	var zoomLogic = e.target.getAttribute ("zoomLogic");
	var value = e.target.getAttribute ("value");
	parent.arrayData = [compid, tableLink, zoomLogic, value];
	var d = window.eone.getMenu (doc, compid, tableLink, zoomLogic, value);
	
	var posx = 0;
	var posy = 0;
	if (!e) var e = window.event;
	if (e.pageX || e.pageY) 	{
		posx = e.pageX;
		posy = e.pageY;
	}
	else if (e.clientX || e.clientY) 	{
		posx = e.clientX + doc.body.scrollLeft
			+ doc.documentElement.scrollLeft;
		posy = e.clientY + doc.body.scrollTop
			+ doc.documentElement.scrollTop;
	}
	
	d.style.top = posy;	
	d.style.left = posx;
	d.style.display = "block";
	
	var f = function() {
		doc.contextMenu.style.display='none'
	};
	setTimeout(f, 3000);
}

var contextMenu;

window.eone.getMenu = function  (doc, componentId, tableLink, zoomLogic, value){
	doc.contextMenu = null;
	if (componentId != null){
	
		//menu div
		var menu = document.createElement("div");
		menu.style.position = "absolute";
		menu.style.display = "none";
		menu.style.top = "0";
		menu.style.left = "0";
		menu.style.border = "1px solid lightgray";
		menu.style.border = "1px solid lightgray";
		menu.style.backgroundColor = "white";
		
		//window menu item
		var windowMenu = document.createElement("div");
		windowMenu.style.padding = "3px";
		windowMenu.style.verticalAlign = "middle";
		windowMenu.setAttribute("onmouseover", "this.style.backgroundColor = 'lightgray'");
		windowMenu.setAttribute("onmouseout", "this.style.backgroundColor = 'white'");									
		
		var href = document.createElement("a");
		href.style.fontSize = "11px";
		href.style.textDecoration = "none";
		href.style.verticalAlign = "middle";
		href.href = "javascript:void(0)";
		href.setAttribute("onclick", "parent.eone.zoom()");
		
		windowMenu.appendChild(href);
		menu.appendChild(windowMenu);				
		
		var windowIco = document.body.getAttribute ("windowIco");
		if (typeof windowIco === 'string' && windowIco.length > 0) {
			var image = document.createElement("img"); 
			image.src = windowIco;
			image.setAttribute("align", "middle");
			href.appendChild(image);
		}
		href.appendChild(doc.createTextNode(window.document.body.getAttribute ("windowLabel")));
		
		doc.contextMenu = menu;
		doc.body.appendChild (doc.contextMenu);
	}
	
	doc.contextMenu.setAttribute ("componentId", componentId);
	doc.contextMenu.setAttribute ("tableLink", tableLink);
	doc.contextMenu.setAttribute ("zoomLogic", zoomLogic);
	doc.contextMenu.setAttribute ("value", value);
	return doc.contextMenu;
}

