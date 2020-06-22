package com.atguigu.ext;

import com.atguigu.bean.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 1.BeanFactoryPostProcessor:beanFactory的后置处理器
 *          在BeanFactory标准化之后调用,来定制和修改BeanFactory的内容,
 *          所有的bean定义已经保存加载到beanFactory,但是bean的实例还未创建
 * BeanFactoryPostProcessor 原理:
 * 1.IOC容器创建对象
 * 2.invokeBeanFactoryPostProcessors(beanFactory);
 *      如何找到所有的 BeanFactoryPostProcessor 并执行他们的方法
 *          1.直接在BeanFactory中找到所有类型是 BeanFactoryPostProcessor 的组件,并执行他们的方法
 *          2.在初始化创建其他组件前面执行
 *
 * 2.BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *  postProcessBeanDefinitionRegistry();
 *  在所有bean定义信息将要被加载,bean实例还未创建
 *  优先于BeanFactoryPostProcessor执行;
 *  利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件
 *  BeanDefinitionRegistryPostProcessor原理:
 *      1.IOC容器创建对象
 *      2.refresh() -> invokeBeanFactoryPostProcessors(beanFactory);
 *      3.从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件
 *          1.依次触发所有的postProcessBeanDefinitionRegistry();
 *          2.再来触发postProcessBeanFactory();
 *      4.再来从容器中找到BeanFactoryPostProcessor组件,然后依次触发postProcessBeanFactory();
 *
 *  3.ApplicationListener:监听器中发布的事件.
 *          ApplicationListener<E extends ApplicationEvent>  @EventListener
 *
 *    监听 ApplicationEvent 及其下面的子事件
 *
 *  步骤:
 *      1.写一个监听器来监听某个事件(ApplicationEvent及其子类)
 *      2.把监听器加入到容器
 *      3.只要容器有相关事件的发布,我们就能监听到这个事件
 *          ContextRefreshedEvent:容器刷新完成(所有bean都完全创建)会发布这个事件
 *          ContextClosedEvent:关闭容器会发布这个事件
 *      4.发布一个事件applicationContext.publishEvent();
 *  原理:
 *      1.ContextRefreshedEvent事件
 *          1.refresh();
 *          2.finishRefresh();
 *      2.自己发布事件
 *      3.容器关闭事件
 *          事件发布流程:
 *          3.publishEvent(new ContextRefreshedEvent(this));
 *                  1.获取事件的多播器,getApplicationEventMulticaster();
 *                  2.getApplicationEventMulticaster().multicastEvent(applicationEvent, eventType);
 *                  3.获取所有的监听器
 *                      for (final ApplicationListener<?> listener : getApplicationListeners(event, type))
 *                      1.如果有Executor,支持异步派发
 *                          Executor executor = getTaskExecutor();
 *                      2.否则,同步方式进行执行listener.onApplicationEvent(event);
 *                      回调onApplicationEvent();
 *
 *  事件派发器:
 *      1.refresh();
 *      2.initApplicationEventMulticaster();
 *          1.去容器中找有没有id=applicationEventMulticaster的组件
 *          2.没有就this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 *              并加入到容器中,我们就可以在其他组件要派发事件,自动注入这个applicationEventMulticaster
 *
 *   容器中有哪些监听器:
 *      1.refresh():
 *      2.registerListeners();
 *          从容器中拿到所有监听器,getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *          String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *
 *  SmartInitializingSingleton原理:
 *      1.refresh();
 *      2.finishBeanFactoryInitialization(beanFactory);
 *          1.创建单实例bean,getBean(beanName);
 *          2.获取所有创建好的单实例bean,判断是否SmartInitializingSingleton类型
 *              如果是就调用afterSingletonsInstantiated();
 *
 */
@Configuration
@ComponentScan(value = "com.atguigu.ext")
public class ExtConfig {

    @Bean
    public Blue blue(){
        return new Blue();
    }
}
