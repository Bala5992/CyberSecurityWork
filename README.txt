#####README#####

Required:
--------
Ant build should be in your system path (or) user path

Command: (Before executing commands, check the root directory of your terminal/cmd prompt in inside project path - \AppConverter)
-------

1) Run jar with default files inside project
	ant run 		- (will complie and execute the jar with default input json added to the project - input.json)

	ant run -Din=$$ -Dout=$$ 		- (Replace '$$' with custom file path , input file and output file respectively)

	ex:
	--
	ant run -Din=E:/data/input.json -Dout=E:/data/output.xml
	
	Check logs inside project path

2) Run jar using java command
	ant jar 		- (To compile the source and extract jar)
	
	(Replace '$$' with custom file path , input file and output file respectively)
	
	java -jar .\AppConverter.jar $$ $$ 			- (To execute jar , go into build -> jar)

	Check logs inside build -> jar 
