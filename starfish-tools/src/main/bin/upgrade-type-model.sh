#!/bin/bash
#
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

BIN_DIR=$(dirname $0)
STARFISH_HOME=${STARFISH_HOME:-$(cd $BIN_DIR/..; pwd)}

HOSTNAME=`hostname`

log=$STARFISH_HOME/logs/nohup-InitTypeModel-$HOSTNAME.out

JAVA_OPTS=${JAVA_OPTS:-"-server -Xms1g -Xmx1g -Xmn512m -XX:+PrintGCDetails -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=dump.hprof"}

PROJECT_HOME=$STARFISH_HOME/..

LIBS="$STARFISH_HOME/libs/*":"$PROJECT_HOME/api-server/libs/*"
java $JAVA_OPTS -cp "$STARFISH_HOME/config":$LIBS org.metahut.starfish.InitTypeModel
