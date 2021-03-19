# Spring注解

## 容器

### 组件添加

- [@Bean](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#bean)
- [@ComponentScan](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#componentscan)
- [@Scope](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#scope)
- [@Lazy](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#lazy)
- [@Conditional](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#conditional)
- [@Import](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#import)
- [FactoryBean](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#factorybean)

### bean生命周期

- [第一种方式——指定初始化和销毁方法](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E7%AC%AC%E4%B8%80%E7%A7%8D%E6%96%B9%E5%BC%8F-%E6%8C%87%E5%AE%9A%E5%88%9D%E5%A7%8B%E5%8C%96%E5%92%8C%E9%94%80%E6%AF%81%E6%96%B9%E6%B3%95)
- [第二种方式——实现接口InitializingBean, DisposableBean](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E7%AC%AC%E4%BA%8C%E7%A7%8D%E6%96%B9%E5%BC%8F-%E5%AE%9E%E7%8E%B0%E6%8E%A5%E5%8F%A3initializingbean-disposablebean)
- [第三种方式——@PostConstruct，@PreDestroy](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E7%AC%AC%E4%B8%89%E7%A7%8D%E6%96%B9%E5%BC%8F-postconstructpredestroy)
- [第四种方式-实现接口BeanPostProcessor](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E7%AC%AC%E5%9B%9B%E7%A7%8D%E6%96%B9%E5%BC%8F-%E5%AE%9E%E7%8E%B0%E6%8E%A5%E5%8F%A3beanpostprocessor)
  - [工作原理](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86)

### 组件赋值

- [@Value](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#value)
- [@Autowired](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#autowired)
- [@Resource（JSR250）](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#resourcejsr250)
- [@Inject（JSR330）](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#injectjsr330)
- [Aware注入Spring底层组件和原理](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#aware%E6%B3%A8%E5%85%A5spring%E5%BA%95%E5%B1%82%E7%BB%84%E4%BB%B6%E5%92%8C%E5%8E%9F%E7%90%86)

### AOP

- [切面类](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E5%88%87%E9%9D%A2%E7%B1%BB)
- [逻辑类](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E9%80%BB%E8%BE%91%E7%B1%BB)
- [配置以及原理](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E9%85%8D%E7%BD%AE%E4%BB%A5%E5%8F%8A%E5%8E%9F%E7%90%86)

### 声明式事务

- 持久层

- 服务层
- 配置以及原理

## 扩展原理

- [BeanFactoryPostProcessor](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#beanfactorypostprocessor)
- [BeanDefinitionRegistryPostProcessor](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#beandefinitionregistrypostprocessor)
- [ApplicationListener 和@EventListener](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#applicationlistener-%E5%92%8Ceventlistener)
- [配置以及原理](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E9%85%8D%E7%BD%AE%E4%BB%A5%E5%8F%8A%E5%8E%9F%E7%90%86)
- [Spring容器创建过程](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#spring%E5%AE%B9%E5%99%A8%E5%88%9B%E5%BB%BA%E8%BF%87%E7%A8%8B)

## SpringBoot运行原理

- [自定义starter](https://github.com/jackhusky/spring_anotation/blob/master/doc/spring%E6%B3%A8%E8%A7%A3%E9%A9%B1%E5%8A%A8.md#%E8%87%AA%E5%AE%9A%E4%B9%89starter)