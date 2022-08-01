package com.iotplatform.framework.mq.core.pubsub;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iotplatform.framework.mq.core.message.AbstractRedisMessage;

/**
 * Redis Channel Message 抽象类
 *
 * @author Kevin
 * @date 2022/7/26 18:51
 */
public abstract class AbstractChannelMessage extends AbstractRedisMessage  {


    /**
     * 获得 Redis Channel
     *
     * @return Channel
     */
    @JsonIgnore // 避免序列化报错
    public abstract String getChannel();

}
