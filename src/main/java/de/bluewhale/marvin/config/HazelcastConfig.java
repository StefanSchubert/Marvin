/*
 * Copyright (c) 2020 by Stefan Schubert under the MIT License (MIT).
 * See project LICENSE file for the detailed terms and conditions.
 */

package de.bluewhale.marvin.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Stefan Schubert
 */
@Configuration
public class HazelcastConfig {

    public final static String HZ_INSTANCE_NAME = "marvin-imdg";

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
        network.setPort(5701);

        // Who is allowed to Join and disable multicast (as it is often blocked)
        JoinConfig join = network.getJoin();
        join.getMulticastConfig().setEnabled(false);

        // A bit ugly
        // 1) works only for SAMPLE k8s environment
        // 2) There is only one member and this one is LBed be the service!
        // FIXME: maybe a member discovery POD
        //        working like this: each POD registed it self in a selfmade POD-discovery
        //        Then this "lookupPOD" will be queried for known members.
        //        For each entry we will explicitly add it as member below (Would this work?)
        /*
            Kubernetes gives every pod its own cluster-private IP address,
            so you do not need to explicitly create links between pods or map
            container ports to host ports. This means that containers within a Pod can
            all reach each other's ports on localhost, and all pods in a cluster can
            see each other without NAT (Origin: https://kubernetes.io/docs/concepts/services-networking/connect-applications-service/ )
         */
        join.getTcpIpConfig().addMember("hazelcast-service.k8s-training.svc.cluster.local").setEnabled(true);

        return config;
    }
}
