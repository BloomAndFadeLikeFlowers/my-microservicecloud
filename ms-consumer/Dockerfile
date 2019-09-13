FROM java:8
ADD ./target/*.jar /

ARG PINPOINT_VERSION=${PINPOINT_VERSION:-1.8.4}

ADD ./pinpoint/pinpoint-agent-${PINPOINT_VERSION}.tar.gz /

COPY ./pinpoint/configure-agent.sh /usr/local/bin/
RUN touch ./*.jar \
    && chmod a+x /usr/local/bin/configure-agent.sh \
    && mv  pinpoint-agent-${PINPOINT_VERSION} /pinpoint-agent \
    && chmod -R o+x /pinpoint-agent

VOLUME ["/pinpoint-agent"]

ENTRYPOINT ["/usr/local/bin/configure-agent.sh"]