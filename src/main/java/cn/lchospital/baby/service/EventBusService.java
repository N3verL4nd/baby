package cn.lchospital.baby.service;

import cn.lchospital.baby.annotations.EventBusListener;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class EventBusService {

    @Autowired
    private ApplicationContextService applicationContextService;

    /**
     * 管理同步事件
     */
    private EventBus syncEventBus = new EventBus();

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("event-bus-pool-%d").build();
    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100), namedThreadFactory);


    /**
     * 管理异步事件
     */
    private AsyncEventBus asyncEventBus = new AsyncEventBus(POOL_EXECUTOR);

    public void postSync(Object event) {
        syncEventBus.post(event);
    }

    public void postAsync(Object event) {
        asyncEventBus.post(event);
    }

    @PostConstruct
    public void init() {

        // 获取所有带有 @EventBusListener 的 bean，将他们注册为监听者
        List<Object> listeners = applicationContextService.getBeansWithAnnotation(EventBusListener.class);
        for (Object listener : listeners) {
            asyncEventBus.register(listener);
            syncEventBus.register(listener);
        }
    }
}