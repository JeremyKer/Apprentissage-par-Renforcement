javac -cp "./lib/*" -d ./build -sourcepath src ./src/learning/Application.java
cd ./build
jar cvfm MyApp.jar manifestAInclure.txt *
java -jar MyApp.jar
