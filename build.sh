rm -R bin/
mkdir bin
javac -sourcepath ./src -d bin src/Main.java
java -classpath ./bin Main group.txt
