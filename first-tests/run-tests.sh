#!/usr/bin/env bash
# Запуск модульных тестов и построение отчёта о покрытии (Linux / macOS).
# Запуск: ./run-tests.sh   (из любого каталога)
cd "$(dirname "$0")"
SRC=../first
LIB="lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar"

rm -rf out coverage
mkdir -p out/stub out/main out/test coverage

echo "== Компиляция программы"
javac -encoding UTF-8 -d out/stub stub/edu/umd/cs/findbugs/annotations/SuppressFBWarnings.java || exit 1
javac -encoding UTF-8 -cp out/stub -d out/main "$SRC"/*.java || exit 1

echo "== Компиляция тестов"
javac -encoding UTF-8 -cp "out/main:$LIB" -d out/test src/*.java || exit 1

echo "== Запуск тестов"
java -Dstdout.encoding=UTF-8 \
     -javaagent:lib/jacocoagent.jar=destfile=coverage/jacoco.exec \
     -cp "out/main:out/test:$LIB" org.junit.runner.JUnitCore AllTests
STATUS=$?

echo "== Отчёт о покрытии"
java -jar lib/jacococli.jar report coverage/jacoco.exec \
     --classfiles out/main --sourcefiles "$SRC" --encoding UTF-8 \
     --html coverage/html --csv coverage/coverage.csv --name first
echo "Откройте coverage/html/index.html"
exit $STATUS
