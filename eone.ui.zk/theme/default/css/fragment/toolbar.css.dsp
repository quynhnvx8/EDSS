
.toolbar {
	padding: 0px;
}

.z-toolbarbutton-content {
	padding: 0px 3px;
}

.z-toolbarbutton {
    display: inline-block;
    vertical-align: middle;
    position: relative;
    cursor: pointer;
    border-radius: 3px;
    overflow: hidden;
    border: 1px solid transparent;
    background-color: transparent;
    padding: 1px 2px;
}

.z-toolbarbutton[disabled] {
    color: rgba(0,0,0,0.34) !important;
    border-color: transparent;
    background-color: #A8A8A8;
    cursor: default !important;
    width: 27px;
    height: 26px;
}

.toolbar-button {
	background-color: transparent; 	
	margin-left: 1px; 
	margin-right: 1px; 
	width: 28px; 
	height: 28px;
	padding: 1px;
}

.toolbar-button .z-toolbarbutton-content {
	width: 24px;
	height: 24px;
	padding: 1px;
	border: none;	
}

.depressed img {
	border-width: 1px;
	border-color: #9CBDFF;
	background-color: #C4DCFB;
	padding: 0px 1px 0px 1px;
}

.disableFilter img {
	opacity: 0.2;
	filter: progid : DXImageTransform . Microsoft . Alpha(opacity = 20);
	-moz-opacity: 0.2;
}

.z-toolbar-start{
	width:100%;
}

.z-toolbarbutton [class^="z-icon-"] {
	color: inherit;
}
.z-toolbarbutton.toolbarbutton-with-text [class^="z-icon-"] {
	padding-right: 4px;
}
.z-toolbarbutton.xlarge-toolbarbutton [class^="z-icon-"] {
	font-size: 24px;
}
.z-toolbarbutton.large-toolbarbutton [class^="z-icon-"] {
	font-size: 20px;
}
.z-toolbarbutton.medium-toolbarbutton [class^="z-icon-"] {
	font-size: 16px;
}
.z-toolbarbutton.small-toolbarbutton [class^="z-icon-"] {
	font-size: 12px;
}
.z-toolbarbutton, .z-toolbarbutton .z-toolbarbutton-content {
	display:inline-flex;
	align-items: center;
	padding: 0px;
	margin: 1px;
}
.z-toolbar-content {
	display:flex;
	align-items: center;
	background: #F5F5F5;
}
.z-toolbar.space-between-content .z-toolbar-content {
	justify-content: space-between;
}

.z-toolbar {
    display: block;
    border-color: #CFCFCF;
    border-width: 0 0 1px;
    padding: 1px 4px 3px;
    background: #F5F5F5;
    position: relative;
}

.font-icon-toolbar-button.toolbar-button [class^="z-icon-"] {
	font-size: 18px;
	color: inherit;
}
.font-icon-toolbar-button.toolbar-button {
	color :#333;
}
.font-icon-toolbar-button.toolbar-button, .font-icon-toolbar-button.toolbar-button .z-toolbarbutton-content {
	display:inline-flex;
	align-items: center;
	justify-content: center; 
}
.font-icon-toolbar-button:active, .font-icon-toolbar-button:hover {
	color: #3949AB;
}
.font-icon-toolbar-button .z-toolbarbutton-content {
	color: inherit;
}
.font-icon-menuitem i {
	vertical-align: middle;
}
.toolbar-searchbox {
    margin-right: 10px;
    margin-left: 10px;
    border: 1px;
}
@media screen and (max-width: 768px) {
  .toolbar-searchbox {
    display: none;
    width: 0px;
  }
}