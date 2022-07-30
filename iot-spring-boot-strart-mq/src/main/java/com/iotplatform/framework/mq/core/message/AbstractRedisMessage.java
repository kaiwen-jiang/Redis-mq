package com.iotplatform.framework.mq.core.message;

/**
 * Redis 消息抽象基类
 *
 * @author Kevin
 * @date 2022/7/28 15:58
 */

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class AbstractRedisMessage {
    /**
     * 头
     */
    private Map<String, String> headers = new HashMap<>();

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
}
