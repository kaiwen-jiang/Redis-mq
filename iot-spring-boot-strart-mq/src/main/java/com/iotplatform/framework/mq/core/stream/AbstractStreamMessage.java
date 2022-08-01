package com.iotplatform.framework.mq.core.stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotplatform.framework.mq.core.message.AbstractRedisMessage;

/**
 * Redis Stream Message 抽象类
 *
 * @date 2022/7/30 10:51
 * @author Kevin
 */
public abstract class AbstractStreamMessage extends AbstractRedisMessage{

    /**
     * 获得 Redis Stream Key
     *
     * @return Channel
     */
    @JsonIgnore // 避免序列化
    public abstract String getStreamKey();
}
