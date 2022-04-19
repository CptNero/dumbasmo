set -e
java -jar src/antlr-4.9.3-complete.jar src/Dumbasmo.g4
javac -cp src/antlr-4.9.3-complete.jar src/*.java
java -cp src/antlr-4.9.3-complete.jar src/DumbasmoParser input.txt