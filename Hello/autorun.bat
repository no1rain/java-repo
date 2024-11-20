:: BatchGotAdmin
:----------------------------------------------------------
REM --> Check for permissions
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"

REM --> If error flag set, we do not have admin.
if '%errorlevel%' NEQ '0' (
    echo Requesting administrative privileges...
    goto UACPrompt
) else ( goto gotAdmin )

:UACPrompt
    echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
    echo UAC.ShellExecute "%~s0", "", "runas", 1 >> "%temp%\getadmin.vbs"

    "%temp%\getadmin.vbs"
    exit /B

:gotAdmin
    if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
    pushd "%CD%"
    CD /D "%~dp0"

REM --> 여기서부터 메인 코드가 오면 된다.

@echo off

call "D:\workspace\allinone.bat"

:: `git-cmd` 실행 시작
D:

cd D:\PortableGit

start "git" "git-cmd.exe"

Rem Visual Studio Code 프로그램 실행
start "VSCode" "D:\VSCodeUserSetup-x64-1.70.1.exe" /param1 /param2

Rem Obsidian 프로그램 실행
start "Obsidian" "D:\Obsidian.1.5.12.exe" /param1 /param2