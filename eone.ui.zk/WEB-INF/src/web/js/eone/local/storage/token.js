window.eone = {};
var eone = window.eone;
eone.isSupportSavePass=typeof(Storage) !== "undefined";

eone.saveUserToken = function (key, hash, sessionId)
{
	if (!eone.isSupportSavePass)
		return;
	localStorage[key+".sid"] = sessionId;
	localStorage[key+".hash"] = hash;
};

eone.findUserToken = function (cmpid, key)
{
	if (!eone.isSupportSavePass)
		return;
	
	var sid = localStorage[key+".sid"];
	var hash = localStorage[key+".hash"];

	if (sid == null || sid == "" || hash == null || hash == ""){
		return
	}
	
	var widget = zk.Widget.$(cmpid);
	var event = new zk.Event(widget, 'onUserToken', {sid: sid, hash: hash}, {toServer: true});
	zAu.send(event);
};

eone.removeUserToken = function (key)
{
	localStorage[key+".sid"] = "";
	localStorage[key+".hash"] = "";	
};

eone.set = function (key, value){
	localStorage[key] = value;
}

eone.get = function (key, fn, scope){
	value = localStorage[key];

    if (fn)
      fn.call(scope || this, true, value);
}
