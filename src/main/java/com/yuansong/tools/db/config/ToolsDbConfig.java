package com.yuansong.tools.db.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.yuansong.tools.db.IToolsDbHelper;

@Configuration
public class ToolsDbConfig {

	@Bean(name = "dynamicDataSourceContextHolderDynamic")
	public DynamicDataSourceContextHolder getDynamicDataSourceContextHolder() {
		return new DynamicDataSourceContextHolder();
	}
	
	@Bean(name = "dynamicRoutingDataSourceDynamic")
	public DynamicRoutingDataSource getDynamicRoutingDataSource(@Qualifier("dynamicDataSourceContextHolderDynamic") DynamicDataSourceContextHolder dynamicDataSourceContextHolder) {
		return DynamicRoutingDataSource.createDynamicRoutingDataSource(dynamicDataSourceContextHolder);
	}
	
	@Bean(name=IToolsDbHelper.BEAN_JDBCTEMPLATE)
	public JdbcTemplate getJdbcTemplate(@Qualifier("dynamicRoutingDataSourceDynamic") DataSource ds) {
		return new JdbcTemplate(ds);
	}
	
	@Bean(name=IToolsDbHelper.BEAN_TX_MANAGER)
	public PlatformTransactionManager getTxManager(@Qualifier("dynamicRoutingDataSourceDynamic") DataSource ds) {
		return new DataSourceTransactionManager(ds);
	}
	
}
