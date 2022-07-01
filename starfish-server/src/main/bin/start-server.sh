#!/bin/bash
#
#  Copyright 2010-2012 Apache DolphinScheduler, Inc.
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
PID=`lsof -i :8801 |grep java |awk '{print $2}'`
if [ $PID ]; then
	kill -9 $PID
fi

BIN_DIR=$(dirname $0)
STARFISH_HOME=${STARFISH_HOME:-$(cd $BIN_DIR/..; pwd)}

HOSTNAME=`hostname`

log=$STARFISH_HOME/logs/nohup-$HOSTNAME.out

JAVA_OPTS=${JAVA_OPTS:-"-server -Xms1g -Xmx1g -Xmn512m -XX:+PrintGCDetails -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=dump.hprof"}

nohup java $JAVA_OPTS -cp "$STARFISH_HOME/config":"$STARFISH_HOME/libs/*" org.metahut.starfish.ServerApplication > $log 2>&1 &
