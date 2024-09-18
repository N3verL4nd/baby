package cn.lchospital.baby.config;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author N3verL4nd
 * @date 2021/12/6
 */
@Configuration
public class ThreadConfig {
    @Bean
    public ExecutorService defaultThreadPool() {
        // 线程命名
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("default-thread-Pool-%d").build();
        // 线程池，线程满则主线程执行
        ExecutorService executorService = new ThreadPoolExecutor(8, 50, 3000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        return executorService;
    }

    /**
     * 返回线程池, 返回的是ListeningExecutorService 可以添加回调
     */
    @Bean
    public ListeningExecutorService listenableThreadPool() {
        return MoreExecutors.listeningDecorator(defaultThreadPool());
    }
}