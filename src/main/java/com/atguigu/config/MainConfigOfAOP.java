package com.atguigu.config;

import com.atguigu.aop.LogAspects;
import com.atguigu.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *  AOP: 指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
 *  1、导入aop模块：spring-aspects
 *  2、定义一个业务逻辑类：MathCalculator
 *  3、定义一个日志切面类：LogAspects
 *  4、给切面类的目标方法标注何时何地运行
 *  5、将切面类和业务逻辑类加入到容器中
 *  6、告诉Spring哪个是切面类（@Aspect）
 *  7、@EnableAspectJAutoProxy
 *
 *  AOP原理: 看给容器中注册了什么组件,这个组件什么时候工作,这个组件的功能是什么?
 *  1.@EnableAspectJAutoProxy
 *      @Import(AspectJAutoProxyRegistrar.class):给容器中导入AspectJAutoProxyRegistrar
 *          利用AspectJAutoProxyRegistrar自定义给容器注册bean:BeanDefinetion
 *          org.springframework.aop.config.internalAutoProxyCreator ---> AnnotationAwareAspectJAutoProxyCreator
 *     给容器中注册一个AnnotationAwareAspectJAutoProxyCreator
 *
 *  2.AnnotationAwareAspectJAutoProxyCreator:
 *      AnnotationAwareAspectJAutoProxyCreator
 *          ->AspectJAwareAdvisorAutoProxyCreator
 *              ->AbstractAdvisorAutoProxyCreator
 *                  ->AbstractAutoProxyCreator
 *                      implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                      关注后置处理器(在bean初始化完成前后做事情),自动装配BeanFactory
 *
 *    AbstractAutoProxyCreator.setBeanFactory()
 *    AbstractAutoProxyCreator有后置处理器的逻辑
 *
 *    AbstractAdvisorAutoProxyCreator.setBeanFactory()-->initBeanFactory()
 *
 *    AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *
 * 流程:
 *      1.传入配置类,创建IOC容器
 *      2.注册配置类,调用refresh()
 *      3.registerBeanPostProcessors(beanFactory); 注册bean的后置处理器来拦截bean的创建
 *          1).先获取IOC容器已经定义了的需要创建对象的所有 BeanPostProcessor
 *          2).给容器中添加其他的 BeanPostProcessor
 *          3).优先注册实现了 PriorityOrdered 接口的 BeanPostProcessor
 *          4).再给容器中注册实现了 Ordered 接口的 BeanPostProcessor
 *          5).注册所有普通的 BeanPostProcessor
 *          6).注册 BeanPostProcessor,实际上就是创建 BeanPostProcessor 对象,保存在容器中
 *              创建 internalAutoProxyCreator[AnnotationAwareAspectJAutoProxyCreator]
 *              1).创建bean的实例
 *              2).populateBean(); 给bean进行属性赋值
 *              3).initializeBean();初始化bean
 *                  1).invokeAwareMethods();处理Aware接口的方法回调
 *                  2).applyBeanPostProcessorsBeforeInitialization();执行后置处理器的BeforeInitialization
 *                  3).invokeInitMethods();执行自定义的初始化方法
 *                  4).applyBeanPostProcessorsAfterInitialization();执行后置处理器的AfterInitialization
 *              4).AnnotationAwareAspectJAutoProxyCreator创建成功
 *          7).beanFactory.addBeanPostProcessor();把BeanPostProcessor添加到beanFactory中
 *==========以上是创建和注册 AnnotationAwareAspectJAutoProxyCreator 的过程===========
 *      AnnotationAwareAspectJAutoProxyCreator --> InstantiationAwareBeanPostProcessor
 *     4.finishBeanFactoryInitialization(beanFactory);完成剩下的但实例bean的创建
 *         1).遍历容器中所有的bean以此创建
 *          getBean() -> doGetBean() -> getSingleton()
 *         2).创建bean
 *         AnnotationAwareAspectJAutoProxyCreator 会在bean实例创建之前调用方法的 postProcessBeforeInstantiation()
 *              1).先从缓存中获取当前bean,如果能获取到,说明之前已经创建了,直接使用,否则再创建
 *              2).createBean();创建bean
 *                  1).resolveBeforeInstantiation();希望后置处理器能返回一个代理对象,如果不能doCreateBean()
 *                  InstantiationAwareBeanPostProcessor 是在创建bean实例之前尝试返回对象的
 *                  BeanPostProcessor 是在bean创建完成的初始化方法前后调用的
 *                      1).后置处理器尝试返回对象
 *                      拿到所有的后置处理器,如果是 InstantiationAwareBeanPostProcessor
 *                      就执行 postProcessBeforeInstantiation();
 *                      bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 * 					    if (bean != null) {
 * 						    bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                      }
 *                  2).doCreateBean()见上面3.6
 *                  3).
 *
 *  AnnotationAwareAspectJAutoProxyCreator (InstantiationAwareBeanPostProcessor)的作用:
 *  1、在每一个bean创建之前,调用 postProcessBeforeInstantiation();
 *      关心MathCalculator和LogAspects的创建
 *      1).判断当前bean是否在 advisedBeans 中 (保存了所有需要增强的bean)
 *      2).判断当前bean是否是基础类型的Advice,Pointcut,Advisor,AopInfrastructureBean,或者是否是切面(@Aspect)
 *      3).是否需要跳过
 *          1).获取候选的增强器(切面里面的通知方法) List<Advisor> candidateAdvisors = findCandidateAdvisors();
 *              每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor类型
 *          2).永远返回false
 *  2、创建对象
 *  postProcessAfterInitialization:
 *      return wrapIfNecessary(bean, beanName, cacheKey);
 *      1).获取当前bean的所有增强器(通知方法)
 *          1.找到候选的的增强器(找那些方法是需要切入当前bean方法的)
 *          2.获取到能在当前bean使用的增强器
 *          3.给增强器排序
 *      2).保存当前bean在advisedBeans中
 *      3).如果当前bean需要增强,创建当前bean的代理对象
 *          1.获取所有增强器
 *          2.保存到proxyFactory
 *          3.创建代理对象
 *      4).给容器中返回当前组件使用cglib增强了的代理对象
 *      5).以后容器中获取到的就是这个组件的代理对象,执行目标方法的时候,代理对象就会执行通知方法的流程
 *
 *  3.目标方法执行
 *      容器中保存了组件的代理对象,这个对象里面保存了详细信息(比如增强器,目标对象...)
 *      1).CglibAopProxy.intercept();拦截目标方法的执行
 *      2).根据ProxyFactory获取将要执行的目标方法拦截器链
 *          List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            1).List<Object> interceptorList 保存所有拦截器 5个
            2).遍历所有的增强器,将其转为 interceptor
 *      3).如果没有拦截器链直接执行目标方法
 *      4).如果有拦截器链,把需要执行的目标对象,目标方法,拦截器链等信息传入创建一个CglibMethodInvocation对象
 *     Object retVal = new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed();
 *
 *  总结:
 *      1.@EnableAspectJAutoProxy开启AOP功能
 *      2.@EnableAspectJAutoProxy注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 *      3.AnnotationAwareAspectJAutoProxyCreator 是一个后置处理器
 *      4.容器创建流程:
 *          1.registerBeanPostProcessors() 注册后置处理器,创建 AnnotationAwareAspectJAutoProxyCreator
 *          2.finishBeanFactoryInitialization() 初始化剩下的单实例bean
 *              1.创建业务逻辑组件和切面组件
 *              2.AnnotationAwareAspectJAutoProxyCreator 拦截组件的创建过程
 *              3.组件创建完成后,判断组件是否需要增强
 *                  是:切面的通知方法,包装成增强器,给业务逻辑组件创建一个代理对象
 *     5.执行目标方法
 *          1.代理对象执行目标方法
 *          2.CglibAopProxy.intercept()
 *              1.得到目标方法的拦截器链(增强器包装成拦截器)
 *              2.利用拦截器链的链式机制,依次进入每一个拦截器进行执行
 *              3.效果:
 *                  正常执行:前置通知->目标方法->后置通知->返回通知
 *                  出现异常:前置通知->目标方法->后置通知->异常通知
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class MainConfigOfAOP {

    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }

}
