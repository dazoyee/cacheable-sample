#!/usr/bin/bash
 
CURRENT_DATETIME=`date +"%Y%m%d%H%M%S"`
APP_NAME=cacheable-sample.jar
 
APP_LOGPATH=/mnt/c/project/sample/cacheable-sample/dev/log
GC_LOG=/mnt/c/project/sample/cacheable-sample/dev/gc_${CURRENT_DATETIME}.log
 
java \
    -Xms128m \
    -Xmx128m \
    -XX:MaxDirectMemorySize=128m \
    -XX:+HeapDumpOnOutOfMemoryError \
    -Xlog:gc*=info:file=${GC_LOG}:time,uptime,level,tags:filecount=10,filesize=10M \
    -jar /mnt/c/project/sample/cacheable-sample/target/${APP_NAME} \
    --logging.file.path=${APP_LOGPATH} 
