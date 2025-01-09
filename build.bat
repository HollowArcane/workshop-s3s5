
cd cnf
ant build














@REM @REM SETTING VARIABLES
@REM set "cnf-dir=cnf"
@REM set "src-dir=src"
@REM set "web-dir=web"
@REM set "lib-dir=lib"

@REM set "target-name=%1"
@REM if "%1" == "" (
@REM     set "target-name=build"
@REM )

@REM @REM REMOVE EXISTING PROJECT FILES
@REM rmdir /q/s "%target-name%"
@REM rmdir /q/s "temp-src"

@REM @REM CREATE A BASE PROJECT STRUCTURE
@REM mkdir "%target-name%"
@REM mkdir "temp-src"

@REM @REM COPY UTILITY FILES INTO THE PROJECT
@REM echo D | xcopy /q/s/y "%web-dir%" "%target-name%"
@REM echo D | xcopy /q/s/y "%cnf-dir%" "%target-name%"

@REM @REM COPY SOURCE FILE TO temp-src
@REM for /r "%src-dir%" %%F in (*) do (
@REM     echo D | xcopy /q/y "%%~F" "temp-src\"
@REM )

@REM @REM  COMPITLE JAVA CODE
@REM javac -parameters -d "%target-name%" -cp "%lib-dir%/*" temp-src/*.java

@REM rmdir /q/s "temp-src"

@REM pause
@REM cls
