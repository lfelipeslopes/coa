package com.compsis.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.compsis.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.compsis.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.compsis.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.compsis.domain.FinancialAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.FinancialAccount.class.getName() + ".ids", jcacheConfiguration);
            cm.createCache(com.compsis.domain.Person.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.UserAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.VehicleAccount.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.Vehicle.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.Vehicle.class.getName() + ".ids", jcacheConfiguration);
            cm.createCache(com.compsis.domain.Media.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.VehicleClass.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.BillingTariff.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.BillingLocation.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.Passage.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountOperation.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountOperation.class.getName() + ".ids", jcacheConfiguration);
            cm.createCache(com.compsis.domain.DataChange.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.InformativeOperation.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.BalanceCalculation.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.Operator.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountTransction.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.AutomaticOperation.class.getName(), jcacheConfiguration);
            cm.createCache(com.compsis.domain.FinancialAccount.class.getName() + ".idUserAccounts", jcacheConfiguration);
            cm.createCache(com.compsis.domain.FinancialAccount.class.getName() + ".idVehicleAccounts", jcacheConfiguration);
            cm.createCache(com.compsis.domain.FinancialAccount.class.getName() + ".idAccountOperations", jcacheConfiguration);
            cm.createCache(com.compsis.domain.Vehicle.class.getName() + ".idMedias", jcacheConfiguration);
            cm.createCache(com.compsis.domain.Vehicle.class.getName() + ".idVehicleClasses", jcacheConfiguration);
            cm.createCache(com.compsis.domain.Vehicle.class.getName() + ".idPassages", jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountOperation.class.getName() + ".idDataChanges", jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountOperation.class.getName() + ".idInformativeOperations", jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountOperation.class.getName() + ".idBalanceCalculations", jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountOperation.class.getName() + ".idOperators", jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountOperation.class.getName() + ".idAccountTransctions", jcacheConfiguration);
            cm.createCache(com.compsis.domain.AccountOperation.class.getName() + ".idAutomaticOperations", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
