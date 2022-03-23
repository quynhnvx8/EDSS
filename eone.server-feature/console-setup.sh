#!/bin/sh
#
echo Setup EOne Server

# Setup eone.properties and eoneEnv.properties
./eone --launcher.ini setup.ini -application eone.install.console-application

# Setup Jetty
./eone --launcher.ini setup.ini -application org.eclipse.ant.core.antRunner -buildfile build.xml

echo .
echo For problems, check log file in base directory
