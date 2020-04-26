set JAVAPATH="C:\\Program Files\\Java\\jdk1.8.0_171\\bin\\"

%JAVAPATH%javac.exe -d "./build" -cp "lib/*" -sourcepath src .\\src\\learning\\Application.java

cd build

%JAVAPATH%jar.exe cvfm MyApp.jar manifestAInclure.txt *

REM verifier que les librairies sont bien dans un repertoire "lib" sous "build"


%JAVAPATH%java.exe -jar MyApp.jar

