# Redis-mq
iot-redis-mq技术组件，是基于 Redis 实现分布式消息队列：
* 使用 Stream 特性，提供【集群】消费的能力。
* 使用 Pub/Sub特性，提供【广播】消费的能力。

1. 集群消费
 
集群消费，是指消息发送到 Redis 时，有且只会被一个消费者（应用 JVM 实例）收到，然后消费成功。

1.1 使用场景

集群消费在项目中的使用场景，主要是提供可靠的、可堆积的异步任务的能力。例如说：
*	短信模块，使用它异步发送短信。
*	事件流转模块，使用它异步发送事件进行下一步处理。
*	物联网数据接收模块，使用它异步收发或处理数据。
*	用户新增积分操作。
*	站内信
*	其它分布式使用场景
*	其他异步操作
* 注意：Spring boot在 JVM 实例重启时，会导致未执行完的任务丢失。而集群消费，因为消息是存储在 Redis 中，所以不会存在该问题。

集群消费基于 Redis Stream 实现：
*	实现 AbstractStreamMessage抽象类，定义【集群】消息。
*	使用 RedisMQTemplate的 #send(message)方法，发送消息。
*	实现 AbstractStreamMessageListener接口，消费消息。

2. 广播消费
* 广播消费，是指消息发送到 Redis 时，所有消费者（应用 JVM 实例）收到，然后消费成功。

2.1 使用场景
例如说，在应用中，缓存了数据字典等配置表在内存中，可以通过 Redis 广播消费，实现每个应用节点都消费消息，刷新本地内存的缓存。
又例如说，我们基于 WebSocket 实现了 IM 聊天，在我们给用户主动发送消息时，因为我们不知道用户连接的是哪个提供 WebSocket 的应用，所以可以通过 Redis 广播消费。每个应用判断当前用户是否是和自己提供的 WebSocket 服务连接，如果是，则推送消息给用户。

2.2 实现源码
广播消费基于 Redis Pub/Sub 实现：
*	实现 AbstractChannelMessage 抽象类，定义【广播】消息。
*	使用 RedisMQTemplate的#send(message)方法，发送消息。
*	实现 AbstractChannelMessageListener接口，消费消息。


# 注意：此插件的正常使用对框架版本要求较高：
  # spring boot >=2.5.0,
  # redis client>=5.1
  # redis >2.3
  # jdk >=1.8

