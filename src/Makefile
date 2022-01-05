main: *.java
	javac *.java

clean: *.java *.class
	rm -f *.class
	javac *.java

run: *.class
	java GravitySim

jar: *.java *.class
	javac *.java
	jar cmf manifest.mf GravitySim.jar *.java *.class data.dat