#!/bin/sh
# script to launch the horoscope getter then delete temporary pdf files
cd /projects/horoscope/batch/
java -classpath /projects/horoscope/batch/horoscope-extractor-1.0.1-SNAPSHOT-jar-with-dependencies.jar com.bbtools.horos.extractor.HorosPdfGetter >> /projects/horoscope/logs/horoscope.log
rm /projects/horoscope/data/*.pdf
