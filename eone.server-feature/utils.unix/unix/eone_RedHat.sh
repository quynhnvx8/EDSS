#!/bin/bash

# initialization
# adjust these variables to your environment
EONE_HOME=/u01/xerp
ENVFILE=$EONE_HOME/utils/myEnvironment.sh
EONEEUSER=eone
export TELNET_PORT=12612

. /etc/rc.d/init.d/functions
 
RETVAL=0
EONESTATUS=
MAXITERATIONS=60 # 2 seconds every iteration, max wait 2 minutes

getEOnestatus() {
    EONESTATUSSTRING=$(ps ax | grep java | grep ${EONE_HOME} | grep -v grep)
    echo $EONESTATUSSTRING | grep -q ${EONE_HOME}
    IDEMPIERESTATUS=$?
}

start () {
    getEOnestatus
    if [ $EONESTATUS -eq 0 ] ; then
	  echo "EOne is already running"
	  return 1
    fi
    echo -n "Starting EOne: "
    cd $EONE_HOME/utils
    source $ENVFILE 
    export LOGFILE=$EONE_HOME/log/eone_`date +%Y%m%d%H%M%S`.log
    su $EONEEUSER -c "mkdir -p $EONE_HOME/log"
    su $EONEEUSER -c "export TELNET_PORT=$TELNET_PORT;cd $EONE_HOME;$EONE_HOME/eone-server.sh &> $LOGFILE &"
    RETVAL=$?
    if [ $RETVAL -eq 0 ] ; then
	# wait for server to be confirmed as started in logfile
	STATUSTEST=0
	ITERATIONS=0
	while [ $STATUSTEST -eq 0 ] ; do
	    sleep 2
	    cat $LOGFILE | grep -q '.*LoggedSessionListener.contextInitialized: context initialized.*' && STATUSTEST=1
	    echo -n "."
	    ITERATIONS=`expr $ITERATIONS + 1`
	    if [ $ITERATIONS -gt $MAXITERATIONS ]
		then
		   break
	    fi
	done
	if [ $STATUSTEST -eq 0 ]
	then
	    echo "Service hasn't started within the timeout allowed, please review file $LOGFILE to see the status of the service"
	    echo_warning
	else
	    echo_success
	fi
	echo
    else
	echo_failure
	echo
    fi
    return $RETVAL
}

stop () {
    getEOnestatus
    if [ $EONESTATUS -ne 0 ] ; then
	  echo "EOne is already stopped"
	  return 1
    fi
    echo -n "Stopping EOne ERP: "
    cd $EONE_HOME/utils
    source $ENVFILE
    # try shutdown from OSGi console, then direct kill with signal 15, then signal 9
    echo "Trying shutdown from OSGi console"
    ( echo exit; echo y; sleep 5 ) | telnet localhost ${TELNET_PORT} > /dev/null 2>&1
    getEOnestatus
    if [ $EONESTATUS -ne 0 ] ; then
        echo_success
    else
        echo "Trying direct kill with signal -15"
        kill -15 -`ps ax o pgid,command | grep ${EONE_HOME} | grep -v grep | sed -e 's/^ *//g' | cut -f 1 -d " " | sort -u`
        sleep 5
        getEOnestatus
        if [ $EONESTATUS -ne 0 ] ; then
            echo_success
        else
            echo "Trying direct kill with signal -9"
            kill -9 -`ps ax o pgid,command | grep ${EONE_HOME} | grep -v grep | sed -e 's/^ *//g' | cut -f 1 -d " " | sort -u`
            sleep 5
            getEOnestatus
            if [ $EONESTATUS -ne 0 ] ; then
                echo_success
            else
                echo_warning
            fi
        fi
    fi
	RETVAL=$?
    return $RETVAL
}

restart () {
    stop
    sleep 2
    start
}

condrestart () {
    getEOnestatus
    if [ $EONESTATUS -eq 0 ] ; then
	restart
    fi
}

status () {
    getEOnestatus
    if [ $EONESTATUS -eq 0 ] ; then
	echo
	echo "EOne is running:"
	ps ax | grep ${EONE_HOME} | grep -v grep | sed 's/^[[:space:]]*\([[:digit:]]*\).*:[[:digit:]][[:digit:]][[:space:]]\(.*\)/\1 \2/'
	echo
    else
	echo "EOne is stopped"
    fi
}

case "$1" in
    start)
	start
	;;
    stop)
	stop
	;;
    restart)
	restart
	;;
    condrestart)
	condrestart
	;;
    status)
	status
	;;
    *)
	echo $"Usage: $0 {start|stop|restart|condrestart|status}"
	exit 1
esac

exit 0
