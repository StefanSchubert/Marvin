/*
 * Copyright (c) 2020 by Stefan Schubert under the MIT License (MIT).
 * See project LICENSE file for the detailed terms and conditions.
 */

package de.bluewhale.marvin.rest;

import de.bluewhale.marvin.model.ContainerState;
import de.bluewhale.marvin.model.MarvinsMood;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static de.bluewhale.marvin.model.MarvinsMood.feel_good;

@RestController
@RequestMapping(value = "api/check")
@Log
public class HealthController {

    private static final int TEN_SECOND_AS_MILLIS = 10000;
    @Autowired
    ContainerState containerState;

    @RequestMapping(value = {"/howareyou"}, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> stateDependedAnswer() {

        String returnText = "";
        HttpStatus returnStatus;

        containerState.incAccessCounter();

        switch (containerState.getMarvinsMood()) {

            case feel_good:
                returnStatus = HttpStatus.OK;
                returnText = "I'm happy that you ask, I'm feeling well.";
                break;
            case overloaded:
                returnStatus = HttpStatus.TOO_MANY_REQUESTS;
                returnText = "I'm so depressed because after hearing this protocol again and again, you will want to replace me.";
                break;
            case play_dead:
                // we simple won't response until the state has been resolved
                // or we passed 10000 exception cycles.
                int circuitBreaker = 0;
                while (MarvinsMood.play_dead.equals(containerState.getMarvinsMood())
                        && circuitBreaker < 10000) {
                    circuitBreaker++;
                    try {
                        log.info(String.format("Cycle %s - dead bots don't complain",circuitBreaker));
                        Thread.sleep(TEN_SECOND_AS_MILLIS);
                    } catch (InterruptedException e) {
                        log.warning("Interrupt Exception");
                    }
                }
                returnStatus = HttpStatus.OK;
                returnText = "I woke up. Where and when am I?";
                break;
            default:
                returnStatus = HttpStatus.BAD_REQUEST;
        }

        // Just received a ping request - NOP
        return new ResponseEntity<>(returnText, returnStatus);
    }

}