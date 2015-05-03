#!/bin/python
import codecs
with open("dict.txt") as f:
	content = f.readlines()
	my_dict=dict()
for cont in content:
	hin=cont.split("=")[0]
#	hin=hin.encode('latin-1')
#	print hin
	hin=hin.decode("utf-8")
	#print hin
	eng=cont.split("=")[1]
	my_dict[hin]=eng
	
#print my_dict
word=raw_input()
word=word.strip()
word=word.decode("utf-8")
try :
	print my_dict[word]
except:
	print "Not Found"
