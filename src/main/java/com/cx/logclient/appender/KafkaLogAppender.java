package com.cx.logclient.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.cx.logclient.kafka.KafkaSender;
import com.cx.logclient.model.LogMessage;
import com.cx.logclient.model.LogMessageBuilder;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;

/**
 * @author: cx
 * @Description:
 * @Date: Created in 11:35 2018/10/26
 */
public class KafkaLogAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private String topic = "application.log";
    private String appId;
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    private static HashSet<KafkaLogAppender> kafkaAppenders = new HashSet();
    public KafkaLogAppender() {
        kafkaAppenders.add(this);
    }

    public static HashSet<KafkaLogAppender> getKafkaAppenders() {
        return kafkaAppenders;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (event != null && event.getMessage() != null && this.isStarted()) {
            LogMessage message = LogMessageBuilder.build(event);
            message.setAppId(appId);
            message.setMessage(event.getMessage());
            KafkaSender.sendMsgToKafka(message, topic);
        }
    }


    @Override
    public void start() {
        if(!this.isStarted()&&appId!=null) {
                this.doStart();
        }
    }

    public void doStart() {

        super.start();
    }
    @Override
    public void stop() {

    }

}