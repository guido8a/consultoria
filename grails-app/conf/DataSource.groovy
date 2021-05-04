dataSource {
    pooled = true
    driverClassName = "org.postgresql.Driver"
    dialect = org.hibernate.dialect.PostgreSQLDialect
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update"
//            url = "jdbc:postgresql://192.168.100.9:5432/janus_brre"
//            url = "jdbc:postgresql://127.0.0.1:5432/janus_brre"
            url = "jdbc:postgresql://127.0.0.1:5432/cnsl"
            username = "postgres"
            password = "postgres"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:postgresql://10.0.0.3:5432/janus2"
            username = "postgres"
            password = "postgres"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:postgresql://127.0.0.1:5432/janus"
            url = "jdbc:postgresql://127.0.0.1:5432/consultoria"
            username = "postgres"
            password = "janus"
        }
    }

    pruebas {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:postgresql://127.0.0.1:5432/janus"
            url = "jdbc:postgresql://127.0.0.1:5432/cnsl_prba"
            username = "postgres"
            password = "postgres"
        }
    }

}
