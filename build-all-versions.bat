@echo off
rem Build every Minecraft version and collect the jars into build\libs\<mod_version>\<mc_version>\
rem
rem JDK 25 is required: JDK 26/27 breaks Lombok 1.18.42
rem (com.sun.tools.javac.tree.EndPosTable was removed).
rem This script looks for a JDK 25 in the usual places; set JAVA_HOME yourself to override.
setlocal enabledelayedexpansion

if not "%JAVA_HOME%"=="" goto haveJava

set "JAVA_HOME="
for %%D in (
    "D:\Java\jdk-25.0.3"
    "C:\Program Files\Java\jdk-25"
    "C:\Program Files\Eclipse Adoptium\jdk-25*"
    "C:\Program Files\Microsoft\jdk-25*"
    "C:\Program Files\BellSoft\LibericaJDK-25*"
) do (
    if not defined JAVA_HOME (
        for /d %%J in (%%~D) do (
            if exist "%%~J\bin\java.exe" set "JAVA_HOME=%%~J"
        )
    )
)

:haveJava
if "%JAVA_HOME%"=="" (
    echo [ERROR] JDK 25 not found.
    echo         Set JAVA_HOME to a JDK 25 installation, for example:
    echo             set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-25.0.2+10
    echo         JDK 26/27 will NOT work: Lombok 1.18.42 crashes on them.
    exit /b 1
)

echo Using JAVA_HOME=%JAVA_HOME%
call "%~dp0gradlew.bat" buildAndCollect %*
if errorlevel 1 exit /b %errorlevel%

echo.
echo Collected jars:
dir /b /s "%~dp0build\libs\*.jar"
