@echo off
rem Запуск модульных тестов и построение отчёта о покрытии (Windows).
rem Запуск: двойной щелчок по файлу или run-tests.bat в консоли.
chcp 65001 > nul
cd /d "%~dp0"
set SRC=..\first
set LIB=lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar

if exist out rmdir /s /q out
if exist coverage rmdir /s /q coverage
mkdir out\stub out\main out\test coverage

echo == Компиляция программы
javac -encoding UTF-8 -d out\stub stub\edu\umd\cs\findbugs\annotations\SuppressFBWarnings.java || goto :fail
javac -encoding UTF-8 -cp out\stub -d out\main %SRC%\*.java || goto :fail

echo == Компиляция тестов
javac -encoding UTF-8 -cp "out\main;%LIB%" -d out\test src\*.java || goto :fail

echo == Запуск тестов
java -Dstdout.encoding=UTF-8 -javaagent:lib\jacocoagent.jar=destfile=coverage\jacoco.exec -cp "out\main;out\test;%LIB%" org.junit.runner.JUnitCore AllTests
set STATUS=%ERRORLEVEL%

echo == Отчёт о покрытии
java -jar lib\jacococli.jar report coverage\jacoco.exec --classfiles out\main --sourcefiles %SRC% --encoding UTF-8 --html coverage\html --csv coverage\coverage.csv --name first
echo Откройте coverage\html\index.html
pause
exit /b %STATUS%

:fail
echo Ошибка компиляции
pause
exit /b 1
