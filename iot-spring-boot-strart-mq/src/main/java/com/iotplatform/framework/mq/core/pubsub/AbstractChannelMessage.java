package com.iotplatform.framework.mq.core.pubsub;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotplatform.framework.mq.core.message.AbstractRedisMessage;

/**
 * Redis Channel Message 抽象类
 *
 * @author Kevin
 */
public abstract class AbstractChannelMessage extends AbstractRedisMessage  {


    /**
     * 获得 Redis Channel
     *
     * @return Channel
     */
    @JsonIgnore // 避免序列化。原因是，Redis 发布 Channel 消息的时候，已经会指定。
    public abstract String getChannel();

}
