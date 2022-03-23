#!/bin/sh

# $Id: RUN_Env.sh,v 1.16 2005/01/22 21:59:15 jjanke Exp $
echo idempiere Environment Check

if [ $EONE_HOME ]; then
  cd $EONE_HOME/utils
fi
# Environment is read from the following script myEnvironment.sh
. ./myEnvironment.sh

echo General ...
echo PATH      = $PATH
echo CLASSPTH  = $CLASSPATH

echo .
echo Homes ...
echo EONE_HOME        = $EONE_HOME
echo JAVA_HOME            = $JAVA_HOME
echo EONE_DB_URL      = $EONE_DB_URL

echo .
echo Database ...
echo EONE_DB_USER     = $EONE_DB_USER
echo EONE_DB_PASSWORD = $EONE_DB_PASSWORD
echo EONE_DB_PATH     = $EONE_DB_PATH

echo .. Oracle specifics
echo EONE_DB_NAME      = $EONE_DB_NAME
echo EONE_DB_SYSTEM   = $EONE_DB_SYSTEM

echo .
echo Java Test ... should be 1.6+
$JAVA_HOME/bin/java -version

echo .
echo Database Connection Test \(1\) ... TNS
echo Running tnsping $EONE_DB_NAME
tnsping $EONE_DB_NAME

echo .
echo Database Connection Test \(2\)... System
echo Running sqlplus system/$EONE_DB_SYS@$EONE_DB_NAME @$EONE_DB_PATH/Test.sql
sqlplus system/$EONE_DB_SYSTEM@$EONE_DB_NAME @$EONE_DB_HOME/Test.sql 

echo .
echo Checking Database Size \(3\)
sqlplus system/$EONE_DB_SYSTEM@$EONE_DB_NAME @$EONE_DB_HOME/CheckDB.sql $EONE_DB_USER

echo .
echo == It is ok for the next to fail before the EONE Database Import Step ==
echo "Database Connection Test \(4\) ... EONE \(May not work, if not user not yet imported\)"
sqlplus $EONE_DB_USER/$EONE_DB_PASSWORD@$EONE_DB_NAME @$EONE_DB_HOME/Test.sql

echo .
echo Done

