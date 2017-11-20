package com.br.discovery.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Escritorio.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.UserExtra.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.UserExtra.class.getName() + ".processoClientes", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.UserExtra.class.getName() + ".processoAdvogados", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Processo.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Processo.class.getName() + ".advogados", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Escritorio.class.getName() + ".userExtras", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.UserExtra.class.getName() + ".pendenciaAdvogados", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Processo.class.getName() + ".pendencias", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Pendencia.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Pendencia.class.getName() + ".advogados", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Processo.class.getName() + ".anexos", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Pendencia.class.getName() + ".anexos", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Anexo.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Anexo.class.getName() + ".processos", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Anexo.class.getName() + ".pendencias", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.UserExtra.class.getName() + ".processoAdvogadoCorrentes", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Convite.class.getName(), jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Convite.class.getName() + ".escritorios", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.Convite.class.getName() + ".advogados", jcacheConfiguration);
            cm.createCache(com.br.discovery.domain.EntityAuditEvent.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
