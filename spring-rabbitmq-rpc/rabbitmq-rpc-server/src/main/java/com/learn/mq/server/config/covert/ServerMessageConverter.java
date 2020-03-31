package com.learn.mq.server.config.covert;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ServerMessageConverter extends AbstractMessageConverter {

    /**
     * 创建Message，对象->String(json)->byte[]->Message
     *
     * @param o
     * @param messageProperties
     * @return
     */
    @Override
    protected Message createMessage(Object o, MessageProperties messageProperties) {
        byte[] bytes;
        if (o instanceof String) {
            bytes = ((String) o).getBytes(UTF_8);
        } else {
            String json = JSONObject.toJSONString(o);
            bytes = json.getBytes(UTF_8);
        }
        messageProperties.setContentType("application/json");
        messageProperties.setContentEncoding("UTF-8");
        messageProperties.setContentLength(bytes.length);
        return new Message(bytes, messageProperties);
    }

    /**
     * Message->byte[]->String(json)->对象
     *
     * @param message
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        byte[] body = message.getBody();
        MessageProperties messageProperties = message.getMessageProperties();
        String encoding = messageProperties.getContentEncoding();
        String json = null;
        JSONObject object = new JSONObject();
        try {
            json = new String(body, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (json != null && !json.isEmpty()) {
            try {
                object = JSONObject.parseObject(json);
            } catch (Exception e) {
                System.out.println("转换Message消息错误");
            }
        }
        return object;
    }
}
