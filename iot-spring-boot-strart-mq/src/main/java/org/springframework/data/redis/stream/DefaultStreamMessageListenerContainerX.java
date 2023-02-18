package org.springframework.data.redis.stream;


import cn.hutool.core.util.ReflectUtil;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ByteRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 拓展 DefaultStreamMessageListenerContainer 实现
 *
 *
 * @author Kevin
 *
 */
public class DefaultStreamMessageListenerContainerX<K, V extends Record<K, ?>> extends DefaultStreamMessageListenerContainer<K, V> {

    /**
     * 参考 {@link StreamMessageListenerContainer#create(RedisConnectionFactory, StreamMessageListenerContainerOptions)} 的实现
     */
    public static <K, V extends Record<K, ?>> StreamMessageListenerContainer<K, V> create(RedisConnectionFactory connectionFactory, StreamMessageListenerContainerOptions<K, V> options) {
        Assert.notNull(connectionFactory, "RedisConnectionFactory must not be null!");
        Assert.notNull(options, "StreamMessageListenerContainerOptions must not be null!");
        return new DefaultStreamMessageListenerContainerX<>(connectionFactory, options);
    }

    public DefaultStreamMessageListenerContainerX(RedisConnectionFactory connectionFactory, StreamMessageListenerContainerOptions<K, V> containerOptions) {
        super(connectionFactory, containerOptions);
    }

    /**
     * 参考 {@link DefaultStreamMessageListenerContainer#register(StreamReadRequest, StreamListener)} 的实现
     */
    @Override
    public Subscription register(StreamReadRequest<K> streamRequest, StreamListener<K, V> listener) {
        return this.doRegisterX(getReadTaskX(streamRequest, listener));
    }

    @SuppressWarnings("unchecked")
    private StreamPollTask<K, V> getReadTaskX(StreamReadRequest<K> streamRequest, StreamListener<K, V> listener) {
        StreamPollTask<K, V> task = ReflectUtil.invoke(this, "getReadTask", streamRequest, listener);
        // 修改 readFunction 方法
        Function<ReadOffset, List<ByteRecord>> readFunction = (Function<ReadOffset, List<ByteRecord>>) ReflectUtil.getFieldValue(task, "readFunction");
        ReflectUtil.setFieldValue(task, "readFunction", (Function<ReadOffset, List<ByteRecord>>) readOffset -> {
            List<ByteRecord> records = readFunction.apply(readOffset);
            //避免 NPE 的问题！！！
            return records != null ? records : Collections.emptyList();
        });
        return task;
    }

    private Subscription doRegisterX(Task task) {
        return ReflectUtil.invoke(this, "doRegister", task);
    }

}

