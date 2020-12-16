#!/bin/bash

#get pwd dir
BASEPATH=$(dirname $(cd `dirname $0`;pwd))

#echo $(pwd)
source ${BASEPATH}/bin/setenv.sh

source ${BASEPATH}/bin/setclasspath.sh
#start java process
nohup java -Xmx1g  -XX:-UseSplitVerifier -Djava.log.path=${BASEPATH}/logs  wg_media_data.es2ka.launch.ProductScrollGetData es2mysql >/dev/null 2>&1 & 
#nohup java -Xmx1g  -XX:-UseSplitVerifier  wg_media_data.es2ka.launch.ProductScrollGetData es2mysql >out.txt & 
