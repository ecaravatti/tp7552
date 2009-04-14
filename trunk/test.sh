#!/bin/bash

input=""

while [ "$input" == "" ]
do
	echo "Ingrese el directorio destino del resultado de los tests (path completo): "
	read input
done

export TEST_DIR=$input

../ant/ant/bin/ant tests junitreport
