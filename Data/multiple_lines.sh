#!/bin/bash

while read line
do
	echo -e "$line" > tmpfile
	echo -e "$line" >> $2
	shallow_parser_hin <tmpfile >> $2
	
done < $1
