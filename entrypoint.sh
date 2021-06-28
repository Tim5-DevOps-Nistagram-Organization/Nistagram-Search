#!/bin/sh

./consul agent -config-dir=/consul-config &

java -jar nistagram-search.jar