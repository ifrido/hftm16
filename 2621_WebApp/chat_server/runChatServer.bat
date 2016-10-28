@echo off
rem Wird server.jar nicht in den Klassenpfad aufgenommen, kann rmiregistry
rem den Server Stub nicht binden, es ergibt eine ClassNotFoundException
set CLASSPATH=target/server.jar

echo Die Registrierung wird gestartet mit default Port 1099
start rmiregistry 1099
rem -J-verbose
pause

echo Der Server wird gestartet
java -jar target/server.jar
