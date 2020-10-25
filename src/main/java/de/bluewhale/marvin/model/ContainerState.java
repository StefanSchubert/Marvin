/*
 * Copyright (c) 2020 by Stefan Schubert under the MIT License (MIT).
 * See project LICENSE file for the detailed terms and conditions.
 */

package de.bluewhale.marvin.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.Random;

/**
 * Used to store the modus operandi of the container.
 *
 * @author Stefan Schubert
 */
@ApplicationScope
@Component
@Getter
@Setter
public class ContainerState {

    public static Integer botID;

    MarvinsMood marvinsMood = MarvinsMood.feel_good;

    Long accessed = 0l;

    static {
        // create random object
        Random ran = new Random();

        // generating integer between 0 and 10000
        botID = ran.nextInt(1000);
    }


    public void incAccessCounter(){
        accessed++;
    }

}
