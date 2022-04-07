package org.metahut.starfish.autoconfigure.data.neo4j;

import org.neo4j.driver.Driver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean({ Driver.class, Neo4jTransactionManager.class, PlatformTransactionManager.class })
@ConditionalOnClass({ Driver.class, Neo4jTransactionManager.class, PlatformTransactionManager.class })
@AutoConfigureAfter({ Neo4jDataAutoConfiguration.class })
public class Neo4jDataStorageAutoConfiguration {

}
