package com.cx.logclient.config;

import com.cx.logclient.appender.KafkaLogAppender;
import com.cx.logclient.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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
        KafkaLogAppender appender= (KafkaLogAppender) SpringContextUtil.getBean("kafak");
        appender.setAppId(appId);
    }


}
