div.wc-modal, div.wc-modal-none, div.wc-highlighted, div.wc-highlighted-none {
	background-color: white;
}

.z-window-embedded .z-window-content {
	border: none;
}

.z-window-embedded .z-window-header, 
.z-window-embedded .z-window-content {
	background-image: none;
}

.z-modal-mask {
	z-index: 1800 !important;
}
.z-window {
	padding: 0px;
}

.z-window-embedded .z-window-content {
	border: none;
}

.z-window-overlapped .z-window-content,  .z-window-modal .z-window-content, 
.z-window-highlighted .z-window-content, .z-window-embedded .z-window-content {
	border: none;
}

.z-window-header {
	padding: 3px;
	border-top-left-radius: 3px;
	border-top-right-radius: 3px;
	border-color: #0093F9;
	background: #0093F9;;
	font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 12px;
    font-weight: bold;
    font-style: normal;
    color: #363636;
}

.z-messagebox-window {
    padding: 3px;
}

.z-window-content {
	background-image: none !important;
	background: #F9F9F9;	
}

.z-window-embedded > .z-window-header {
	border-radius: 3px;
	background-color: transparent;
}

.z-window-overlapped, .z-window-popup, .z-window-modal, .z-window-highlighted, 
.embedded-dialog .z-window-embedded
{
	background-color: #fff;
	margin: 0px;
}

.z-window-overlapped .z-window-header,
.z-window-popup .z-window-header,
.z-window-modal .z-window-header,
.z-window-highlighted .z-window-header
{
	color: #fff;
	font-weight: bold;
}


.z-window-modal-shadow, .z-window-overlapped-shadow, .z-window-popup-shadow, .z-window-embedded-shadow, .z-window-highlighted-shadow
{
	border-radius: 0px !important;
}

<%-- dialog --%>
.embedded-dialog {
	position: absolute;
}

.embedded-dialog .z-window-embedded-header {
	color: #fff;
	font-weight: bold;
}

.popup-dialog .z-window-overlapped .z-window-content, .popup-dialog .z-window-highlighted .z-window-content {
	padding: 0px;
	background-color: #f5f5f5;
}
.popup-dialog > .z-window-content, .info-panel > .z-window-content {
	padding: 0px;
	border-bottom-left-radius: 4px;
	border-bottom-right-radius: 4px;
}
.popup-dialog .dialog-content {
	padding: 8px !important;
	--margin-bottom: 20px !important;
}

.popup-dialog.z-window-overlapped .dialog-footer {
	padding: 12px 15px 8px 15px !important;
}

.popup-dialog.z-window-highlighted .dialog-footer {
	padding: 12px 15px 8px 15px !important;
}

.dialog-footer {
	margin-bottom: 0;
	background-color: #f5f5f5;
	border-top: 1px solid #ddd;
	-webkit-box-shadow: inset 0 1px 0 #ffffff;
	-moz-box-shadow: inset 0 1px 0 #ffffff;
	box-shadow: inset 0 1px 0 #ffffff;
}

.btn-ok {
	font-weight: nomal;
	color: #FFFFFF;
}

<%-- notification message --%>
.z-notification .z-notification-content {
    width: 400px;
}

.z-notification {
	padding: 3px !important;
}

input[type="checkbox"]:focus
{
 	 outline: #0000ff auto 1px;
	-moz-outline-radius: 3px;
}

<%-- Quick Form Read-only Component --%>
.quickform-readonly .z-textbox-readonly, .quickform-readonly .z-intbox-readonly, .quickform-readonly .z-longbox-readonly, .quickform-readonly .z-doublebox-readonly,
.quickform-readonly .z-decimalbox-readonly, .quickform-readonly .z-datebox-readonly, .quickform-readonly .z-timebox-readonly, .quickform-readonly .editor-input-disd,
.quickform-readonly .z-textbox[readonly], .quickform-readonly .z-intbox[readonly], .quickform-readonly .z-longbox[readonly], .quickform-readonly .z-doublebox[readonly],
.quickform-readonly .z-decimalbox[readonly], .quickform-readonly .z-datebox[readonly], .quickform-readonly .z-timebox[readonly]
{
    color: #252525 !important;
    opacity: .8;
}

.z-panel-icon {
	background: transparent;
    color: rgba(0,0,0,0.57);
    margin: 2px 5px;
    padding: 4px;
    text-align: center;
    overflow: hidden;
    cursor: pointer;
    float: right;
    font-size: 12px;
    display: block;
    width: 24px;
    height: 24px;
    line-height: 20px;
    border: 0px;
    
}

.z-window-icon {
    font-size: 14px;
    color: #636363;
    display: block;
    width: 28px;
    height: 24px;
    border-radius: 3px;
    margin: 2px 5px;
    line-height: 24px;
    background: transparent;
    text-align: center;
    overflow: hidden;
    cursor: pointer;
    float: right;
    border: 0px;
}

.z-window-close {
    font-size: 14px;
    line-height: 23px;
}

.z-paging-input {
    width: 50px;
    height: 25px;
    border: 1px solid #D9D9D9;
    border-radius: 4px;
    background: #FFFFFF;
    margin-left: 6px;
    padding: 0 8px;
    line-height: 16px;
    vertical-align: baseline;
    margin-top: 6px;
}