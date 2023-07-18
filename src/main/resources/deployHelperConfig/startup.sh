#!/bin/sh
jarName=agile-1.0.0.jar
JVM_OPTS="-Dname=$jarName  -Duser.timezone=Asia/Shanghai -Xms512M -Xmx1024M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"
nohup java -jar  $JVM_OPTS /home/agile/dev/$jarName > /dev/null 2>&1 &
