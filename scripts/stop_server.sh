#!/bin/bash
if ((ps -C java | wc -l) > 1)
then
    pkill -f 'java -jar'
fi

