#!/usr/bin/env bash

MVN_REPO="/home/gvanvuuren/checkouts/SI7741/si7741/test-lib"



CP="/home/gvanvuuren/checkouts/SI7741/si7741/target/si7741-0.1.0.jar"

for f in $MVN_REPO/*.jar; do
CP="$CP:$f"
done

BOOT=""
for f in $MVN_REPO/scala*.jar; do
BOOT="$BOOT:$f"
done

echo $BOOT
java -Xbootclasspath/a:"$BOOT" -classpath "$CP" test.si7741.UsingIMain $@
