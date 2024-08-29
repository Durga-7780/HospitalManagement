package ConfigurationFiles;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;



@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({"ConfigurationFiles","Repositories","ServiceClasses","FrontController"})
public class DispatcherConfigurationfile implements WebMvcConfigurer {
	@Bean
    public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
	@Override		// for css,js,images
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/resources/**")
         .addResourceLocations("/resources/");
	}
	 @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	        dataSource.setUrl("jdbc:mysql://localhost:3306/hospital"); // Update to your database URL
	        dataSource.setUsername("root"); // Update to your database username
	        dataSource.setPassword("123456"); // Update to your database password
	        return dataSource;
	    }
	    // SessionFactory Bean
	    @Bean
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        
	        Properties hibernateProperties = new Properties();
	        hibernateProperties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
	        hibernateProperties.setProperty(Environment.SHOW_SQL, "true");
	        hibernateProperties.setProperty(Environment.HBM2DDL_AUTO, "update");
	        sessionFactory.setHibernateProperties(hibernateProperties);

	        sessionFactory.setPackagesToScan("EntityClasses"); // Update to the package where your entities are located

	        return sessionFactory;
	    }
	    // TransactionManager Bean
	    @Bean
	    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
	        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	        transactionManager.setSessionFactory(sessionFactory);
	        return transactionManager;
	    }


}
