#!/bin/bash
set -e
set -x
PINPOINT_VERSION=${PINPOINT_VERSION:-1.8.4}
COLLECTOR_IP=${COLLECTOR_IP:-127.0.0.1}
COLLECTOR_TCP_PORT=${COLLECTOR_TCP_PORT:-9994}
COLLECTOR_STAT_PORT=${COLLECTOR_STAT_PORT:-9995}
COLLECTOR_SPAN_PORT=${COLLECTOR_SPAN_PORT:-9996}
sed -i "/profiler.collector.ip=/ s/=.*/=${COLLECTOR_IP}/" /pinpoint-agent/pinpoint.config
sed -i "/profiler.collector.tcp.port=/ s/=.*/=${COLLECTOR_TCP_PORT}/" /pinpoint-agent/pinpoint.config
sed -i "/profiler.collector.stat.port=/ s/=.*/=${COLLECTOR_STAT_PORT}/" /pinpoint-agent/pinpoint.config
sed -i "/profiler.collector.span.port=/ s/=.*/=${COLLECTOR_SPAN_PORT}/" /pinpoint-agent/pinpoint.config
#sed -i "/profiler.sampling.rate=/ s/=.*/=${PROFILER_SAMPLING_RATE}/" /pinpoint-agent/pinpoint.config

sed -i "/level value=/ s/=.*/=\"${DEBUG_LEVEL}\"\/>/g" /pinpoint-agent/lib/log4j.xml

#ln -s  /pinpoint-agent /pinpoint-agent

serviceName=$(ls *.jar|sed s/.jar//)
echo "serviceName"  $serviceName

AGENT_ID=$serviceName
APPLICATION_NAME=$serviceName
#Agent端jar的路径，不需要修改
JAVA_OPTS="-javaagent:/pinpoint-agent/pinpoint-bootstrap-${PINPOINT_VERSION}.jar -Dpinpoint.agentId=$AGENT_ID -Dpinpoint.applicationName=$APPLICATION_NAME"

java $JAVA_OPTS -jar $serviceName.jar

exec "$@"
