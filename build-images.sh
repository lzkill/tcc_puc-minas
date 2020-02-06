#!/bin/sh

mvn -Pprod verify com.google.cloud.tools:jib-maven-plugin:dockerBuild
