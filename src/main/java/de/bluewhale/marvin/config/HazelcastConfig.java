/*
 * Copyright (c) 2020 by Stefan Schubert under the MIT License (MIT).
 * See project LICENSE file for the detailed terms and conditions.
 */

package de.bluewhale.marvin.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>A hazelcastInstance will be only created, if the libs are on the classpath (see maven dependencies) and
 * if a configuration will be found. So this configuration here triggers the cache creation at bootstrapping
 * (as long as you have @EnableCaching above of @SpringBootApplication).</p>
 * <p>
 * <p>As for further info about hazelcast java config see e.g.:
 * <a href="https://memorynotfound.com/spring-boot-hazelcast-caching-example-configuration/">spring-boot-hazelcast-caching-example-configuration</a>
 * </p>
 * <p>
 * <p>
 * <p>Within sabi this case is used for:
 * <ul>
 * <li>Password forgotten tokens</li>
 * </ul>
 * </p>
 *
 * @author Stefan Schubert
 */
@Configuration
public class HazelcastConfig {

    public final static String HZ_INSTANCE_NAME = "marvin-hzCache";

    @Bean
    public Config hazelCastConfig() {

        Config config = new Config()
                .setInstanceName(HZ_INSTANCE_NAME)
                .addMapConfig(
                        new MapConfig()
                                .setName("BotID")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU));

        // Cluster Communication via TCP
        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701).setPortCount(1); // Reserving a portrange of 5 for the backend nodes.

        // What is my IP?
        InetAddress inetAddress = null;
        String containerIP;
        try {
            inetAddress = InetAddress.getLocalHost();
            containerIP = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            containerIP = "localhost";
        }

        // Who is allowed to Join and disable multicast (as it is often blocked)
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getTcpIpConfig()
                .addMember(containerIP).setEnabled(true);

        return config;
    }
}
