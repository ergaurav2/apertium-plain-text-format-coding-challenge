#!/bin/bash
if [ $# !=  3 ]; then
	echo "Error: Invalid Number of Arguments!"
	echo "First argument should be mode" 
	echo "-modetoxml for converting *.modes to xml file"
	echo "-xmltomode for converting modes.xml to *.modes"
	echo "Second argument should be <Path of the language pair directory>"
	echo "Third argument should be <Path of the output dictionary for modes.xml>"
	echo "e.g. bash run.sh -xmltomode apertium-hin test"
	exit 1
fi
if [ ! -d $2 ];then
	echo "Input directory $2 doesn't exits, please check"
	exit 1
fi
if [ "$1" == "-modetoxml" ];then
	if [ ! -e ModetoXML.class ]; then
	javac -source 1.6 -target 1.6 ModetoXML.java
	fi
	java ModetoXML "$2" "$3"
elif [ "$1" == "-xmltomode" ];then
	if [ ! -e XMLtoMode.class ]; then
	javac -source 1.6 -target 1.6 XMLtoMode.java
	fi
	java XMLtoMode "$2" "$3"
else 
	echo "Invalid Mode! Please check again"
	echo "-modetoxml for converting *.modes to xml file"
	echo "-xmltomode for converting modes.xml to *.modes"
fi
