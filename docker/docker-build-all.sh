#!/bin/bash
apps=(run)
for i in $(seq 0 ${#apps[@]}) ; do
    ./docker-build.sh "${apps[$i]}"
done
