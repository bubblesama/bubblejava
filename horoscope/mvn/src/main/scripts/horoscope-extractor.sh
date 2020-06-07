#!/bin/sh
# script to launch the horoscope getter then delete temporary pdf files
cd /projects/horoscope/
java -classpath /projects/horoscope/extractor-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.bbtools.horos.extractor.HorosPdfGetter >> /projects/horoscope/logs/horoscope.log
rm /projects/horoscope/data/*.pdf
