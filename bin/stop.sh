#!bin/bash

STOPPID=$(jps -lvm |grep es2mysql|awk '{print $1}')
if [ -n "${STOPPID}" ]; then
	echo stoppid is ${STOPPID}
	kill -9 ${STOPPID}
else
	echo es2mysql is not running
fi
