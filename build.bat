@REM SETTING VARIABLES
set "cnf-dir=cnf"
set "src-dir=src"
set "web-dir=web"
set "lib-dir=lib"

set "target-name=%1"
if "%1" == "" (
    set "target-name=build"
)

@REM REMOVE EXISTING PROJECT FILES
rmdir /q/s "%target-name%"
rmdir /q/s "temp-src"

@REM CREATE A BASE PROJECT STRUCTURE
mkdir "%target-name%"
mkdir "temp-src"

@REM COPY UTILITY FILES INTO THE PROJECT
echo D | xcopy /q/s/y "%web-dir%" "%target-name%"
echo D | xcopy /q/s/y "%cnf-dir%" "%target-name%"

@REM COPY SOURCE FILE TO temp-src
for /r "%src-dir%" %%F in (*) do (
    echo D | xcopy /q/y "%%~F" "temp-src\"
)

@REM  COMPITLE JAVA CODE
javac -parameters -d "%target-name%" -cp "%lib-dir%/*" temp-src/*.java

rmdir /q/s "temp-src"

pause
cls
