build:
	javac -d ./bin ./src/*.java
run:
	cd ./bin; java MainClass; cd ..
clean:
	rm ./bin/*