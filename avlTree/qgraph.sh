#!/bin/bash
# a script for quick output of a graphviz dot graph.

if [ $1 ] && [ -f ${1}.dot ] ; then
	dot -Tpng ${1}.dot -o ${1}.png
	eog ${1}.png&
	exit 0
else
	exit 1
fi
