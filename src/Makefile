main: *.java
	javac *.java

clean: *.java *.class
	rm -f *.class
	javac *.java

run: *.java *.class
	javac *.java
	java GravitySim

jar: *.java *.class
	javac *.java
	jar cmf manifest.mf GravitySim.jav *.java *.class data.dat