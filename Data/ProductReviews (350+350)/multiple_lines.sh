#!/bin/ bash           

while read line           
do       
    echo -e "$line" > temp
    echo -e "$line" >> $2
    shallow_parser_hin < temp >> $2         
done < $1
