#!/usr/bin/env bash

rm -rf ./backend-1.0

sbt compile
sbt dist
unzip target/universal/ba*.zip
backend-1.0/bin/backend -Dplay.http.secret.key='testjksjjkkjkjskla'
