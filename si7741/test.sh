#!/usr/bin/env bash

BASE="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

LIB="$BASE/test-lib"



CP="$BASE/target/si7741-0.1.0.jar"

for f in $LIB/*.jar; do
 CP="$CP:$f"
done

BOOT=""
for f in $LIB/scala*.jar; do
 BOOT="$BOOT:$f"
done

java -Xbootclasspath/a:"$BOOT" -classpath "$CP" test.si7741.UsingIMain $@
