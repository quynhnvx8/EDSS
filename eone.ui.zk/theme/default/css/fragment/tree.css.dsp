<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
.z-treecell-content {
	${fontFamilyC};
	${fontSizeM};
}

.z-treecell-content {
	padding: 2px 1px;
}
.z-treecell:hover {
	backgroud: red !important;
}

.tree-moveitem-btn {
	padding: 2px 4px; 
	border-radius: 3px;
}
.tree-moveitem-btn.pressed {
	box-shadow: inset 0 0 0 1px #efefef,inset 0 3px 15px #9f9f9f;
}

.z-tree-body {
    position: relative;
    overflow: hidden;
    background: #F9F9F9;
}
/*Style màu nền của menu*/
.z-panel-body {
    margin: 0;
    padding: 0;
    backgroud: #F9F9F9;
    color: rgba(0,0,0,0.9);
    overflow: hidden;
    zoom: 1;
}

/*Style boder từng menu và màu nền của menu*/
.z-treerow .z-treecell {
    border-top: 1px solid #7ac8ff;
    overflow: hidden;
    padding: 5px;
    cursor: pointer;
    background: #F9F9F9;
    position: relative;
    z-index: 0;
}

/*Màu chữ menu*/
.z-treecell-text {
    vertical-align: middle;
    margin-left: 8px;
    color: black;
    #font-weight: bold;
}

.z-treerow.z-treerow:hover > .z-treecell {
	background-color: #7AC8FF;
}


