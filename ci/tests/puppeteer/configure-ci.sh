#!/bin/bash

RED="\e[31m"
GREEN="\e[32m"
YELLOW="\e[33m"
ENDCOLOR="\e[0m"

function printred() {
  printf "${RED}$1${ENDCOLOR}\n"
}
function printgreen() {
  printf "${GREEN}$1${ENDCOLOR}\n"
}
function printyellow() {
  printf "${YELLOW}$1${ENDCOLOR}\n"
}

SCENARIO_CONFIG_FILE="$PWD/ci/tests/puppeteer/scenarios/$1/script.json"
echo "Checking scenario configuration file: ${SCENARIO_CONFIG_FILE}"

NODE_VERSION_REQUIRED=$(cat "$SCENARIO_CONFIG_FILE" | jq -j '.requirements.nodejs // empty')
NATIVE_BUILD_ENABLED=$(cat "$SCENARIO_CONFIG_FILE" | jq -j '.requirements.graalvm.enabled // true')

if [[ -z $NODE_VERSION_REQUIRED ]]; then
  echo "Scenario configuration does not specify a Node.js version"
  NODE_VERSION_REQUIRED=${NODE_CURRENT}
fi
if [[ -z $NATIVE_BUILD_ENABLED ]]; then
  NATIVE_BUILD_ENABLED="true"
fi

printgreen "Node.js version: ${NODE_VERSION_REQUIRED}"
printgreen "Graal VM native build enabled: ${NATIVE_BUILD_ENABLED}"
