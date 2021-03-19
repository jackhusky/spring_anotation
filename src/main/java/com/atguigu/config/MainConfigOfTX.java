package com.atguigu.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 原理：
 * 1、@EnableTransactionManagement
 *          利用TransactionManagementConfigurationSelector给容器中导入组件
 *          AutoProxyRegistrar、ProxyTransactionManagementConfiguration
 * 2、AutoProxyRegistrar：
 *          给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件；（SmartInstantiationAwareBeanPostProcessor）
 *          利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用
 *
 * 3、ProxyTransactionManagementConfiguration：
 *          1、给容器中注册事务增强器；
 *              事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解信息
 *          2、事务拦截器
 *              TransactionInterceptor：保存了事务属性信息，事务管理器；
 *              他是一个 MethodInterceptor；
 *              在目标方法执行的时候；
 *                  执行拦截器链；
 *                  事务拦截器；
 *                      1、先获取事务相关的水泥杆
 *                      2、再获取 PlatformTransactionManager，如果事先没有添加指定任何transactionManager
 *                          最终会从容器中按照类型获取一个 PlatformTransactionManager
 *                      3、执行目标方法
 *                          如果异常，获取到事务管理器，利用事务管理器回滚操作；
 *                          如果正常，利用事务管理器，提交事物
 *
 *
 */
@EnableTransactionManagement
@Configuration
@ComponentScan("com.atguigu.tx")
public class MainConfigOfTX {

    @Bean
    public PlatformTransactionManager platformTransactionManager() throws PropertyVetoException {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("123456");
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        return comboPooledDataSource;
    }
}
