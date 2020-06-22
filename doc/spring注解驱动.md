* [容器](#%E5%AE%B9%E5%99%A8)
  * [组件添加](#%E7%BB%84%E4%BB%B6%E6%B7%BB%E5%8A%A0)
    * [@Bean](#bean)
    * [@ComponentScan](#componentscan)
    * [@Scope](#scope)
    * [@Lazy](#lazy)
    * [@Conditional](#conditional)
    * [@Import](#import)
    * [FactoryBean](#factorybean)
  * [bean生命周期](#bean%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F)
    * [第一种方式\-指定初始化和销毁方法](#%E7%AC%AC%E4%B8%80%E7%A7%8D%E6%96%B9%E5%BC%8F-%E6%8C%87%E5%AE%9A%E5%88%9D%E5%A7%8B%E5%8C%96%E5%92%8C%E9%94%80%E6%AF%81%E6%96%B9%E6%B3%95)
    * [第二种方式\-实现接口InitializingBean, DisposableBean](#%E7%AC%AC%E4%BA%8C%E7%A7%8D%E6%96%B9%E5%BC%8F-%E5%AE%9E%E7%8E%B0%E6%8E%A5%E5%8F%A3initializingbean-disposablebean)
    * [第三种方式\-@PostConstruct，@PreDestroy](#%E7%AC%AC%E4%B8%89%E7%A7%8D%E6%96%B9%E5%BC%8F-postconstructpredestroy)
    * [第四种方式\-实现接口BeanPostProcessor](#%E7%AC%AC%E5%9B%9B%E7%A7%8D%E6%96%B9%E5%BC%8F-%E5%AE%9E%E7%8E%B0%E6%8E%A5%E5%8F%A3beanpostprocessor)
      * [工作原理](#%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86)
  * [组件赋值](#%E7%BB%84%E4%BB%B6%E8%B5%8B%E5%80%BC)
    * [@Value](#value)
    * [@Autowired](#autowired)
    * [@Resource（JSR250）](#resourcejsr250)
    * [@Inject](#inject)
    * [Aware注入Spring底层组件和原理](#aware%E6%B3%A8%E5%85%A5spring%E5%BA%95%E5%B1%82%E7%BB%84%E4%BB%B6%E5%92%8C%E5%8E%9F%E7%90%86)
    * [AOP](#aop)
* [扩展原理](#%E6%89%A9%E5%B1%95%E5%8E%9F%E7%90%86)
  * [BeanFactoryPostProcessor](#beanfactorypostprocessor)
  * [BeanDefinitionRegistryPostProcessor](#beandefinitionregistrypostprocessor)
  * [ApplicationListener 和@EventListener](#applicationlistener-%E5%92%8Ceventlistener)
  * [配置以及原理](#%E9%85%8D%E7%BD%AE%E4%BB%A5%E5%8F%8A%E5%8E%9F%E7%90%86)
  * [Spring容器创建过程](#spring%E5%AE%B9%E5%99%A8%E5%88%9B%E5%BB%BA%E8%BF%87%E7%A8%8B)

# 容器

## 组件添加

### @Bean 

给容器中注册一个Bean，类型为返回值的类型，id默认是方法名作为id

```java
@Configuration //告诉spring这是一个配置类
public class MainConfig {

    @Bean(value = "person") //注入bean,id默认是方法名字,也可以通过@Bean(value = "person")指定
    public Person person01(){
        return new Person("jack",2);
    }
}
```

```java
public class MainTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
        System.out.println("****************************");
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String name : beanNamesForType) {
            System.out.println("在IOC容器中Person的名字是: " + name);
        }
    }
}
-----------------------------------------------
Person{name='jack', age=2}
****************************
在IOC容器中Person的名字是: person
```

### @ComponentScan 

包扫描，标注了@Component、@Controller、@Service、@Repository添加到容器中

```java
@Configuration //告诉spring这是一个配置类
@ComponentScan(value = "com.atguigu",includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = BookService.class),
        @ComponentScan.Filter(type = FilterType.CUSTOM,value = MyTypeFilter.class)
},useDefaultFilters = false)
public class MainConfig {

    @Bean(value = "person") //注入bean,id默认是方法名字,也可以通过@Bean(value = "person")指定
    public Person person01(){
        return new Person("jack",2);
    }
}
```

```java
public class MyTypeFilter implements TypeFilter {
    // metadataReader:目标类的元数据读取器
    // metadataReaderFactory:用于获得元数据的读者其他类的工厂（如超类和接口）
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前类注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取类资源(路径)
        Resource resource = metadataReader.getResource();
        // 正在扫描类的名字
        String className = classMetadata.getClassName();
        System.out.println("类的全路径名字---->" + className);
        if (className.contains("er")){
            return true;
        }
        return false;
    }
}
```

### @Scope

- prototype：多实例的，ioc容器启动并不会去调用方法创建对象放在容器中，每次获取的时候才会调用方法创建对象
- singleton：单实例的（默认值），ioc容器启动会调用方法创建对象放到ioc容器中，以后每次获取直接从容器中拿

```java
@Configuration
public class MainConfig2 {

    @Bean
    @Scope("prototype")
    public Person person(){
        System.out.println("给IOC容器中注册...");
        return new Person("lucy",3);
    }
}
```

### @Lazy

懒加载，针对于单实例bean，容器启动不创建对象，第一次使用（获取）Bean创建对象

```java
@Configuration
public class MainConfig2 {

    @Bean
    @Lazy
//    @Scope("prototype")
    public Person person(){
        System.out.println("给IOC容器中注册...");
        return new Person("lucy",3);
    }
}
```

### @Conditional

按照一定的条件进行判断，满足条件给容器中注册bean

```java
@Bean("bill")
@Conditional(WindowsCondition.class)
public Person person01(){
    return new Person("bill gates",60);
}

@Bean("linus")
@Conditional(LinuxCondition.class)
Person person02(){
    return new Person("linus",50);
}
```

```java
public class WindowsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String property = environment.getProperty("os.name");
        if (property.contains("Windows")) {
            return true;
        }
        return false;
    }
}
```

### @Import

快速给容器中导入一个组件，id默认是全类名

第一种用法：

```java
@Configuration
@Import(value = {Color.class, Red.class})
public class MainConfig2 {
```

第二种用法：

```java
@Configuration
@Import(value = {Color.class, Red.class, MyImportSelector.class})
public class MainConfig2 {
```

```java
public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        String className = importingClassMetadata.getClassName();
        System.out.println("标注@Import注解的类的名字是: " + className);
        Set<String> annotationTypes = importingClassMetadata.getAnnotationTypes();
        System.out.println("标注@Import注解的类的其他注解有: " + annotationTypes);
        return new String[]{"com.atguigu.bean.Blue","com.atguigu.bean.Yellow"};
    }
}
```

第三种用法：

```java
@Configuration
@Import(value = {Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {
```

```java
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String className = importingClassMetadata.getClassName();
        System.out.println("导入类的名字: " + className);
        registry.registerBeanDefinition("rainbow",new RootBeanDefinition(Rainbow.class));
    }
}
```

### FactoryBean 

```java
public class ColorFactoryBean implements FactoryBean<Color> {
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
```

## bean生命周期

### 第一种方式-指定初始化和销毁方法

```java
@Configuration
public class MainConfiOfLifeCycle {

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
```

### 第二种方式-实现接口InitializingBean, DisposableBean

```java
@Component
public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("cat constructor...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("cat destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("cat afterPropertiesSet...");
    }
}
```

### 第三种方式-@PostConstruct，@PreDestroy

```java
@Component
public class Dog {
    public Dog() {
        System.out.println("dog constructor");
    }

    @PostConstruct
    public void init(){
        System.out.println("dog @PostConstruct...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("dog @PreDestroy...");
    }
}
```

### 第四种方式-实现接口BeanPostProcessor

作用：在bean初始化前后进行一些处理工作

```java
@Component // 添加到容器中使其工作
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor postProcessBeforeInitialization..." + "beanName = " + beanName + " ==> " + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("MyBeanPostProcessor postProcessAfterInitialization..." + "beanName = " + beanName + " ==> " + bean);
        return bean;
    }
}
```

#### 工作原理

```java
populateBean(beanName, mbd, instanceWrapper); // 给bean进行属性赋值
initializeBean(beanName, exposedObject, mbd){
    // 初始化方法之前执行
    wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
    // 执行初始化方法
    invokeInitMethods(beanName, wrappedBean, mbd); 
    // 初始化方法之后执行
    wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
}
// 把每个BeanPostProcessor进行调用执行方法
for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
    result = beanProcessor.postProcessBeforeInitialization(result, beanName);
    if (result == null) {
        return result;
    }
}
```

## 组件赋值

### @Value

```java
public class Person {

    @Value("jack")
    private String name;

    @Value("#{26-24}")
    private Integer age;

    @Value("${person.nickName}")
    private String nickName;
```

```java
@Configuration
@PropertySource(value = {"person.properties"})
public class MainConfigOfPropertyValue {

    @Bean
    public Person person(){
        return new Person();
    }
}
```

### @Autowired

```java
/**
 * @Autowired：自动注入
 * 1、默认优先按照类型去容器中找对应的组件：applicationContext.getBean(BookDao.class);
 * 2、如果找到多个相同类型的组件：再将属性的名称作为组件的id去容器中查找applicationContext.getBean("bookDao");
 * 3、@Qualifier("bookDao2") 用来指定需要装配的组件的id，而不是使用属性名
 * 4、自动装配默认一定要将属性赋值好，没有就会报错。
 *      可以使用@Autowired(required = false)
 * 5、@Primary 让Spring进行自动装配的时候，默认使用首选的bean，也可以继续使用@Qualifier
 */
@Configuration
@ComponentScan(value = {"com.atguigu.dao","com.atguigu.service","com.atguigu.controller"})
public class MainConfigOfAutowired {
    @Bean("bookDao2")
//    @Primary
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.setLable("2");
        return bookDao;
    }
}
```

可以标注在：构造器，方法，属性，参数，都是从容器中获取组件的值

- 标注在方法位置：@Bean + 方法参数，参数从容器中获取；默认不写@Autowired 效果是一样的，都能自动装配
- 标注在构造器上：如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
- 放在参数位置

### @Resource（JSR250）

默认按照组件名称进行装配，不支持@Primary和@Autowired(required = false)

```java
@Service
public class BookService {

    @Resource
    private BookDao bookDao;
```

### @Inject

功能和@Autowired一样，但没有@Autowired(required = false)

### Aware注入Spring底层组件和原理

自定义组件想要使用Spring容器底层的一些组件，实现xxxAware。在创建对象的时候，会调用接口规定的方法注入相关组件，把Spring底层一些组件注入到自定义的Bean中

ApplicationContextAware===>ApplicationContextAwareProcessor

```java
@Component
public class Red implements ApplicationContextAware, EmbeddedValueResolverAware, BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("bean的名字：" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("IOC容器：" + applicationContext);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        System.out.println(resolver.resolveStringValue("你好#{2022-2},${os.name}"));
    }
}
```

### AOP

切面类

```java
/**
 * @Aspect:当前类是一个切面类
 */
@Aspect
public class LogAspects {

    @Pointcut("execution(public int com.atguigu.aop.MathCalculator.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName()+"运行...@Before参数列表是:{"+ Arrays.asList(args)+"}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"..@After运行结束");
    }

    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result){
        System.out.println(joinPoint.getSignature().getName()+"正常返回...@AfterReturning运行结果:{"+result+"}");
    }

    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint,Exception exception){
        System.out.println(joinPoint.getSignature().getName()+"运行异常...@AfterThrowing异常是:{"+exception+"}");
    }
}
```

逻辑类

```java
public class MathCalculator {

    public int div(int i, int j){
        System.out.println("MathCalculator....div...");
        return i/j;
    }
}
```

配置以及原理

```java
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
```

# 扩展原理

## BeanFactoryPostProcessor

```java
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor...postProcessBeanFactory()..");
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        System.out.println(Arrays.asList(beanDefinitionNames));
    }
}
```

## BeanDefinitionRegistryPostProcessor

```java
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{
    // bean定义信息的保存中心
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry..bean的数量: " + registry.getBeanDefinitionCount());
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
        registry.registerBeanDefinition("hello",beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory..bean的数量: " + beanFactory.getBeanDefinitionCount());
    }
}
```

## ApplicationListener 和@EventListener

```java
@Component
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("MyApplicationListener监听到事件: " + event);
    }
}
```

```java
@Service
public class UserService {

    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent event){
        System.out.println("UserService..监听到的事件:" + event);
    }
}
```

## 配置以及原理

```java
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
```

## Spring容器创建过程

```java
Spring容器的refresh()方法
1、prepareRefresh();刷新前的预处理
	1、initPropertySources();初始化一些属性设置,子类自定义个性化的属性设置
	2、getEnvironment().validateRequiredProperties();检验属性的合法
	3、earlyApplicationEvents = new LinkedHashSet<ApplicationEvent>();保存容器中一些早期的事件
2、obtainFreshBeanFactory();获取BeanFactory
	1、refreshBeanFactory();刷新BeanFactory
		创建了一个this.beanFactory = new DefaultListableBeanFactory();
		设置id
	2、getBeanFactory();返回刚才GenericApplicationContext创建的BeanFactory对象
	3、将创建的BeanFactory[DefaultListableBeanFactory]返回
3、prepareBeanFactory(beanFactory);BeanFactory的预准备工作(BeanFactory进行一些设置)
	1、设置BeanFactory的类加载器、支持表达式解析器...
    2、添加部分BeanPostProcessor[ApplicationContextAwareProcessor]
    3、设置忽略的自动装配的接口(EnvironmentAware、EmbeddedValueResolverAware、ResourceLoaderAware...)
    4、注册可以解析的自动装配，我们能直接在任何组件中自动注入BeanFactory、ResourceLoader、ApplicationEventPublisher、ApplicationContext
    5、添加BeanPostProcessor[ApplicationListenerDetector]
    6、添加编译时的AspectJ支持
    7、给BeanFactory中注册一些能用的组件;
		environment[ConfigurableEnvironment]、
    	systemProperties[Map<String, Object>]、
    	systemEnvironment[Map<String, Object>]
4、postProcessBeanFactory(beanFactory);BeanFactory准备工作完成后进行的后置处理工作
	1、子类通过重写这个方法来在BeanFactory创建并预准备完成后做进一步的设置
**************************以上是BeanFactory的创建以及预准备工作**************************
5、invokeBeanFactoryPostProcessors(beanFactory);
	BeanFactoryPostProcessor:BeanFactory的后置处理器.在BeanFactory标准初始化之后执行的.
    两个接口:BeanFactoryPostProcessor、BeanDefinitionRegistryPostProcessor
    1、执行BeanFactoryPostProcessor方法
    	先执行BeanDefinitionRegistryPostProcessor
    	1、获取所有的BeanDefinitionRegistryPostProcessor
    	2、看先执行实现了PriorityOrdered优先级接口的BeanDefinitionRegistryPostProcessor,
			postProcessor.postProcessBeanDefinitionRegistry(registry);
		3、再执行实现了Ordered顺序接口的BeanDefinitionRegistryPostProcessor,
			postProcessor.postProcessBeanDefinitionRegistry(registry);
		4、最后执行没有实现任何优先级或者是顺序接口的BeanDefinitionRegistryPostProcessor,
			postProcessor.postProcessBeanDefinitionRegistry(registry);
		再执行BeanFactoryPostProcessor方法
		1、获取所有的BeanFactoryPostProcessor
    	2、看先执行实现了PriorityOrdered优先级接口的BeanDefinitionRegistryPostProcessor,
			postProcessor.postProcessBeanDefinitionRegistry(registry);
		3、再执行实现了Ordered顺序接口的BeanDefinitionRegistryPostProcessor,
			postProcessor.postProcessBeanDefinitionRegistry(registry);
		4、最后执行没有实现任何优先级或者是顺序接口的BeanDefinitionRegistryPostProcessor,
			postProcessor.postProcessBeanDefinitionRegistry(registry);
6、registerBeanPostProcessors(beanFactory);注册BeanPostProcessor(Bean的后置处理器)
    不同接口类型的BeanPostProcessor,在Bean创建前后的执行时机是不一样的
    BeanPostProcessor、
    DestructionAwareBeanPostProcessor、
    InstantiationAwareBeanPostProcessor、
    SmartInstantiationAwareBeanPostProcessor、
    MergedBeanDefinitionPostProcessor[internalPostProcessors]、
    1、获取所有的BeanPostProcessor;后置处理器都默认可以通过PriorityOrdered、Ordered接口来执行优先级
    2、先注册PriorityOrdered优先级接口的BeanPostProcessor;
	   把每一个BeanPostProcessor添加到BeanFactory中;
		beanFactory.addBeanPostProcessor(postProcessor);
	3、再注册实现了Ordered接口的
	4、最后注册没有实现任何优先级接口的
	5、最终注册MergedBeanDefinitionPostProcessor
	6、注册一个ApplicationListenerDetector,来在Bean创建完成后检查是否是ApplicationListener,如果是this.applicationContext.addApplicationListener((ApplicationListener<?>) bean);
7、initMessageSource();初始化MessageSource组件(做国家化功能;消息绑定,消息解析)
    1、getBeanFactory();
	2、看容器中是否有id为messageSource的组件,类型时MessageSource的组件
		如果有赋值给messageSource,如果没有自己创建一个DelegatingMessageSource
			MessageSource:取出国际化配置文件中的某个key值,能按照区域信息获取
	3、把创建好的MessageSource注册到容器中，以后获取国际化配置文件的值的时候,可以自动注入MessageSource
		beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);
		String getMessage(String code, Object[] args, String defaultMessage, Locale locale);
8、initApplicationEventMulticaster();初始化事件派发器
	1、getBeanFactory();
	2、从BeanFactory中获取applicationEventMulticaster的ApplicationEventMulticaster
	3、如果上一步没有配置;创建一个SimpleApplicationEventMulticaster
	4、将创建的ApplicationEventMulticaster添加到BeanFactory中,以后其他组件直接注入
9、onRefresh();留给子容器(子类)
	1、子类重写这个方法,在容器刷新的时候可以自定义逻辑
10、registerListeners();给容器中将所有项目里面的ApplicationListener注册进来
	1、从容器中拿到所有的ApplicationListener
	2、将每个监听器添加到事件派发器中
		getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
	3、派发之前步骤产生的事件
11、finishBeanFactoryInitialization(beanFactory);初始化所有剩下的单实例Bean
	1、beanFactory.preInstantiateSingletons();初始化剩下的单实例bean
	2、获取Bean的定义信息RootBeanDefinition
	3、Bean不是抽象的,是单实例的,不是懒加载的
		1、判断是否是FactoryBean;是否是实现FactoryBean接口的Bean
		2、不是工厂Bean;利用getBean(beanName);创建对象
			0、getBean(beanName);ioc.getBean();
			1、doGetBean(name, null, null, false);
			2、先获取缓存中保存的单实例Bean,如果能获取到说明Bean之前被创建过(所有创建过的单实例Bean都会被缓存起来)
                private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);
			3、缓存中获取不到,开始Bean的创建对象流程
			4、标记当前bean已经被创建
			5、获取Bean的定义信息
			6、获取当前Bean依赖的其他Bean;如果有按照getBean(dep);把依赖的Bean先创建出来
			7、启动单实例Bean的创建流程
				1、createBean(beanName, mbd, args);
				2、Object bean = resolveBeforeInstantiation(beanName, mbdToUse);先尝试获取代理对象
				InstantiationAwareBeanPostProcessor;提前执行
				先触发:postProcessBeforeInstantiation();
				如果有返回值:触发postProcessAfterInitialization();
				3、如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象,就调用4
				4、Object beanInstance = doCreateBean(beanName, mbdToUse, args);创建Bean
					1、创建Bean实例:instanceWrapper = createBeanInstance(beanName, mbd, args);利用工厂方法或者对象的构造器创建Bean实例
					2、applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
						调用MergedBeanDefinitionPostProcessor的
						postProcessMergedBeanDefinition(mbd, beanType, beanName);
					3、populateBean(beanName, mbd, instanceWrapper);
						赋值之前
						1、拿到InstantiationAwareBeanPostProcessor后置处理器;
							postProcessAfterInstantiation();
						2、拿到InstantiationAwareBeanPostProcessor后置处理器;
							postProcessPropertyValues();
						3、赋值:应用Bean属性的值;为属性利用setter方法等进行赋值;
							applyPropertyValues(beanName, mbd, bw, pvs);
						4、Bean初始化:initializeBean(beanName, exposedObject, mbd);
							1、执行Aware接口方法,invokeAwareMethods(beanName, bean);执行xxxAware接口的方法.
                                BeanNameAware、BeanClassLoaderAware、BeanFactoryAware
                            2、执行后置处理器初始化之前,applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
								BeanPostProcessor.postProcessBeforeInitialization();
							3、执行初始化方法,invokeInitMethods(beanName, wrappedBean, mbd);
								1、是否是InitializingBean接口的实现;执行接口规定的初始化
								2、是否自定义初始化方法
							4、执行后置处理器初始化之后,applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
								BeanPostProcessor.postProcessAfterInitialization();
						5、注册Bean的销毁方法;
				5、将创建的Bean添加到缓存中;singletonObjects
				IOC容器就是这些Map;很多的Map里面保存了单实例Bean,环境信息...
	所有Bean都利用getBean创建完成之后;
	检查是否是SmartInitializingSingleton接口类型的,如果是执afterSingletonsInstantiated();
12、finishRefresh();完成BeanFactory的初始化创建工作,IOC容器就创建完成
	1、initLifecycleProcessor();初始化和生命周期有关的后置处理器;LifecycleProcessor
		默认从容器中找是否有LifecycleProcessor的组件,
		如果没有new DefaultLifecycleProcessor();并加入到容器中
		写一个LifecycleProcessor的实现类,可以在BeanFactory
			void onRefresh();
			void onClose();
	2、getLifecycleProcessor().onRefresh();
		拿到前面定义的生命周期处理器(监听BeanFactory生命周期的),回调onRefresh();	
	3、publishEvent(new ContextRefreshedEvent(this));
		发布容器刷新完成事件
	4、LiveBeansView.registerApplicationContext(this);
```

