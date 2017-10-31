#!/bin/bash
#
# Use this shell script to compile (if necessary) your code and then execute it. Below is an example of what might be found in this file if your program was written in Python
#
python ./src/donation.py ./input/itcont.txt ./output/medianvals_by_date.txt

javac -d ./src ./src/MedianFinder.java

javac -cp ./src -d ./src ./src/Main.java

java -cp ./src Main ./input/itcont.txt ./output/medianvals_by_zip.txt
