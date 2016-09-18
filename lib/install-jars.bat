@echo off

rem Set the Library Directory for the current file directory
set LIB_DIR=%~dp0

rem Print the Library Directory
echo Library Directory: %LIB_DIR%

rem Install the jar packages to the local Maven repository
call mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.4.0 -Dpackaging=jar -Dfile=%LIB_DIR%ojdbc6-11.2.0.4.0.jar

call mvn install:install-file -Dfile=ppt.jar -DgroupId=ppt -DartifactId=hxp -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

rem Pause to confirm installation results
pause