package com.cx.logclient.config;

import com.cx.logclient.appender.KafkaLogAppender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Iterator;

/**
 * @author: cx
 * @Description:
 * @Date: Created in 11:56 2018/10/26
 */
@Configuration
public class KafkaConfig {
    @Value("${com.cx.appId}")
    private String appId;

    @PostConstruct
    public void  init(){
        if(StringUtils.isEmpty(this.appId)) {
            throw new RuntimeException("appId is empty");
        } else {
            Iterator it = KafkaLogAppender.getKafkaAppenders().iterator();
            while(it.hasNext()) {
                KafkaLogAppender appender = (KafkaLogAppender)it.next();
                appender.setAppId(appId);
                if(!appender.isStarted()) {
                    appender.doStart();
                }
            }

        }
    }


}
