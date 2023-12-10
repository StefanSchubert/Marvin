#!/usr/bin/env bash

#
# Copyright (c) 2023 by Stefan Schubert under the MIT License (MIT).
# See project LICENSE file for the detailed terms and conditions.
#

# run                     Starte das Image (siehe -d unten) als Container!
# -v außen:innen          Mounte Verzeichnis
# -p außen:innen          Bilde Port ab
# -d                      detach
# imagename
# -- name container-name  Benutze diesen Namen für den Container

echo "copy current jars to container assets."
mkdir -p assets/opt
cp target/marvin-bot-1.1-SNAPSHOT.jar assets/opt
echo "done"

docker rm accsonaut/marvin:latest
echo "removed possible stale container"

docker build -t accsonaut/marvin:latest -f Dockerfile .
echo "create marvins container for you ;-)"

# create a container based on the image above.

# Ports explained
# 8042: http port
# 5701: hazelcast
docker run \
       -p 8042:8042 \
       -p 5701:5701 \
       --name marvin \
       -d \
       accsonaut/marvin:latest
