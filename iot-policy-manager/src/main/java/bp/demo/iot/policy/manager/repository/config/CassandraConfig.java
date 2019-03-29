package bp.demo.iot.policy.manager.repository.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource(value = {"classpath:iot-policy-manager.properties"})
@EnableCassandraRepositories(basePackages = {"bp.demo.iot.policy" +
        ".manager.repository"}, cassandraTemplateRef =
        "IotPolicySpaceCassandraTemplate")
public class CassandraConfig {

  private static final Logger logger =
          LoggerFactory.getLogger(CassandraConfig.class);

  private static final String CASSANDRA_HOST_PROPERTY = "bp.demo.iot" +
          ".policy.cassandra.host";
  private static final String CASSANDRA_PORT_PROPERTY = "bp.demo.iot" +
          ".policy.cassandra.port";
  private static final String CASSANDRA_KEYSPACE_PROPERTY = "bp.demo" +
          ".iot.policy.cassandra.keyspace";

  @Autowired
  private Environment environment;

  @Bean
  public CassandraClusterFactoryBean testCluster() {
    CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();

    String cassandraHost = environment.getProperty(CASSANDRA_HOST_PROPERTY);
    if (System.getProperty(CASSANDRA_HOST_PROPERTY) != null) {
      cassandraHost = System.getProperty(CASSANDRA_HOST_PROPERTY);
    }

    String cassandraPort = environment.getProperty(CASSANDRA_PORT_PROPERTY);
    if (System.getProperty(CASSANDRA_PORT_PROPERTY) != null) {
      cassandraPort = System.getProperty(CASSANDRA_PORT_PROPERTY);
    }

    logger.info("Using Cassandra host=" + cassandraHost + " port=" + cassandraPort);

    cluster.setContactPoints(cassandraHost);
    cluster.setPort(Integer.parseInt(cassandraPort));
    cluster.setMetricsEnabled(false);
    return cluster;
  }

  @Bean
  public CassandraMappingContext testCassandraMapping() {
    return new CassandraMappingContext();
  }

  @Bean
  protected String getTestKeyspaceName() {
    return environment.getProperty(CASSANDRA_KEYSPACE_PROPERTY);
  }

  @Bean
  public CassandraMappingContext testMappingContext() {

    CassandraMappingContext mappingContext = new CassandraMappingContext();
    mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(testCluster().getObject(), getTestKeyspaceName()));

    return mappingContext;
  }

  @Bean
  public CassandraConverter testConverter() {
    return new MappingCassandraConverter(testMappingContext());
  }

  @Bean("IotPolicySpaceSession")
  public CassandraSessionFactoryBean testSession()
          throws Exception {

    CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
    session.setCluster(testCluster().getObject());
    session.setKeyspaceName(getTestKeyspaceName());
    session.setConverter(testConverter());
    session.setSchemaAction(SchemaAction.NONE);

    return session;
  }

  @Bean("IotPolicySpaceCassandraTemplate")
  public CassandraOperations cassandraTemplate()
          throws Exception {
    return new CassandraTemplate(testSession().getObject());
  }


}
