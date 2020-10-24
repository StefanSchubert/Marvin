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

import static java.lang.System.exit;

@RestController
@RequestMapping(value = "api/cmd")
@Log
public class CommandController {

    @Autowired
    ContainerState containerState;

    @RequestMapping(value = {"/initiateProtocol/{x}"}, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<String> dispatchBevavior(@PathVariable(value = "x", required = true) String x) {

        String returnMessage = "I'm not sure what to do now.";
        containerState.incAccessCounter();

        switch (Integer.parseInt(x)) {
            case 88:
                containerState.setMarvinsMood(MarvinsMood.feel_good);
                returnMessage = "OK I will be happy.";
                break;
            case 100:
                containerState.setMarvinsMood(MarvinsMood.overloaded);
                returnMessage = "This make me feel sad.";
                break;
            case 9:
                containerState.setMarvinsMood(MarvinsMood.play_dead);
                returnMessage = "Honestly? I won't respond to system checks now.";
                break;
            case -1:
                log.info("Received kill command. Bye bye");
                exit(1);
                break;
        }

        // Just received a ping request - NOP
        return new ResponseEntity<>(returnMessage, HttpStatus.OK);
    }

}