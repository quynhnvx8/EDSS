.z-button {
  display: inline-block;
  margin: 0px;
  padding: 0px 10px;
  font-size: 12px;
  color: #FFFFFF;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  border: 1px solid #cccccc;
  margin: 0px !important;
  background: #0093F9;
}

.z-button:hover {
    border-color: #8FB9D0;
    background: #7AC8FF;
}

.z-button[disabled] {
    border: 1px solid #A8A8A8;
    background-color: #D9D9D9;
    cursor: default;
}

.z-button[disabled]:hover {
    border: 1px solid #A8A8A8;
    background-color: #D9D9D9;
    cursor: default;
}

.z-paging-button {
    font-family: Arial,Sans-serif;
    font-size: 12px;
    font-weight: normal;
    font-style: normal;
    color: #2184BA;
    display: inline-block;
    min-width: 24px;
    height: 24px;
    border: 1px solid #CFCFCF;
    margin-right: 6px;
    padding: 4px 0px;
    line-height: 14px;
    background: #F9F9F9;
    text-align: center;
    vertical-align: top;
    cursor: pointer;
    text-decoration: none;
    white-space: nowrap;
}


.z-button-focus,
.z-button-click,
.z-button-disabled {
  background: #7AC8FF;
}

.z-button-click {
  background-color: #cccccc;
}

.z-button-focus {
  color: #F0F0F0;
  text-decoration: none;
  
  
}



.z-button-click {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
     -moz-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
          box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
}

.z-button.btn-small {
	padding: 1px 5px;
}
.z-button.btn-medium {
	padding: 2px 10px;
}

.z-button-disabled {
	color: black; cursor: default; opacity: .6; -moz-opacity: .6; -khtml-opacity: .6; filter: alpha(opacity=60);
}

.img-btn{
	text-align: center;
	cursor: pointer; 
	/*width:24px;*/ 
	height:24px;
}

.img-btn img {
	height: 22px;
	width: 22px;
	background-color: transparent;
}

.txt-btn img, .small-img-btn img {
	height: 16px;
	width: 16px;
	background-color: transparent;
	vertical-align: middle;
	display: inline-block;
}

.btn-sorttab {
	box-shadow: 0px 0px 4px #bbb;
	color: #555;
	border: solid 1px #bbb;
	text-shadow: 0px 1px 2px #888;
}

.z-button [class^="z-icon-"],
.z-button-os [class^="z-icon-"]{
	font-size: larger;
	color: #333;	
	padding-left: 2px;
	padding-right: 2px;
}
.z-button.xlarge-toolbarbutton [class^="z-icon-"] {
	font-size: 24px;
}
.z-button.large-toolbarbutton [class^="z-icon-"] {
	font-size: 20px;
}
.z-button.medium-toolbarbutton [class^="z-icon-"] {
	font-size: 16px;
}
.z-button.small-toolbarbutton [class^="z-icon-"] {
	font-size: 12px;
}
.z-button {
	vertical-align: middle;
	text-align: center;
}
.btn-ok.z-button [class^="z-icon-"]:before {
	color: green;	
}
.btn-cancel.z-button [class^="z-icon-"]:before {
	color: red;	
}