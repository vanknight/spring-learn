package com.learn.mq.client.config.covert;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

import java.io.UnsupportedEncodingException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClientMessageConverter extends AbstractMessageConverter {

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
        try {
            json = new String(body, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(body);
        }
        return json;
    }
}
