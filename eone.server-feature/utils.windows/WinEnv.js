

// Get Objects
var Shell = new ActiveXObject("WScript.Shell");
var DesktopPath = Shell.SpecialFolders("Desktop");
var Args = WScript.Arguments;
var SysEnv = Shell.Environment("SYSTEM");

if (Args.length != 2)
{
  WScript.Echo("Usage: cscript WinEnv.js EONE_HOME JAVA_HOME"
	+ "\nExample:\ncscript WinEnv.js C:\\EONE \"C:\\Program Files\\Java\\jdk1.5.0_05\"");
  WScript.Quit (1);
}


// Set Environment Variables
var home = Args(0).replace("\"","");
SysEnv("IDEMPIERE_HOME") = home;
WScript.Echo ("SET IDEMPIERE_HOME="+ home);

home = Args(1).replace("\"","");
SysEnv("JAVA_HOME") = home;
WScript.Echo ("SET JAVA_HOME="+ home);


// Check that JAVA_HOME is in PATH
var pathString = SysEnv("PATH"); // Shell.ExpandEnvironmentStrings("%PATH%");
var index = pathString.indexOf(home);
if (index == -1)
{
  SysEnv("PATH") = home + "\\bin;" + pathString;
  var index_2 = SysEnv("PATH").indexOf(home);
  if (index_2 == -1)
    WScript.Echo ("Path NOT changed - run program as Administrator!");
  else
    WScript.Echo ("Path Changed = " + SysEnv("PATH"));
}
else
  WScript.Echo ("Path is OK = " + SysEnv("PATH"));


var link = Shell.CreateShortcut(DesktopPath + "\\eone.lnk");
link.TargetPath = Args(0) + "\\lib\\eone.exe";
link.Arguments = "-debug";
link.Description = "EONE Client";
link.IconLocation = Args(0) + "\\lib\\eone.exe,0";
link.WorkingDirectory = Args(0);
link.WindowStyle = 3;
link.HotKey = "CTRL+ALT+SHIFT+C";
link.Save();
WScript.Echo ("Created Shortcut eone.lnk");

// Create Web Site Shortcut
var urlLink = Shell.CreateShortcut(DesktopPath + "\\EONE Web Site.url");
urlLink.TargetPath = "";
urlLink.Save();
WScript.Echo ("Created Shortcut EONE Web Site.url");

WScript.Echo ("Done");
