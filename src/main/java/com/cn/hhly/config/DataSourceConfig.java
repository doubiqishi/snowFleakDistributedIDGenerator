package com.cn.hhly.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据库连接池配置
 *
 * @author bsw
 * @create 2018-06-26
 *
 */
@Configuration
@MapperScan(basePackages = {"com.cn.hhly.mapper.*","com.cn.hhly.**.mapper"})
public class DataSourceConfig {
    public static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    //要加载的 mybatis 的配置文件目录
    private static final String[] RESOURCE_PATH = new String[] { "classpath*:*/*Mapper_cust.xml","classpath*:*/*Mapper.xml", "classpath*:**/*Mapper_cust.xml","classpath*:**/*Mapper.xml"};
    public static final Resource[] RESOURCE_ARRAY;


    static {
        RESOURCE_ARRAY = getResourceArray();
    }

    /**
     * 获取 mybatis 要加载的 xml 文件
     *
     */
    private static Resource[] getResourceArray() {
        List<Resource> resourceList = new ArrayList<Resource>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for (String path : RESOURCE_PATH) {
            try {
                Resource[] resources = resolver.getResources(path);
                if (resources != null && resources.length !=0) {
                    Collections.addAll(resourceList, resources);
                }
            } catch (IOException e) {
                logger.error(String.format("load file(%s) exception", path), e);
            }
        }
        return resourceList.toArray(new Resource[resourceList.size()]);
    }


    /**
     *  创建druid 连接池
     * @author bsw
     * @create 2018-06-26
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "druid")
    public DataSource setupDruid() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(setupDruid());

        // 配置文件
        if (logger.isDebugEnabled()) {
            logger.debug("mybatis load xml:({})",RESOURCE_ARRAY.toString());
        }
        sessionFactory.setMapperLocations(RESOURCE_ARRAY);

        // 分页插件
        sessionFactory.setPlugins(new Interceptor[]{ });

        if (logger.isDebugEnabled()) {
            logger.debug("mybatis load type handle:({})");
        }


        return sessionFactory.getObject();
    }

    /**
     *  构建sqlsessionTemplate 对象
     * @author bsw
     * @create 2018-06-26
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionTemplate", destroyMethod = "clearCache")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    /**
     * 扫描 mybatis 的接口目录, 在上面的类处理完之后再扫描<br>
     * 在类上标注 @MapperScan(basePackages = "com.hhly.business.repository") 效果一样
     */
    /*@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        mapperScannerConfigurer.setBasePackage(SysLogMapper.class.getPackage().getName());
        return mapperScannerConfigurer;
    }*/
}
