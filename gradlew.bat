@echo off
setlocal
REM Android Studio ichidagi Java avtomatik topish
if not defined JAVA_HOME (
  if exist "D:\Android studio\jbr\bin\java.exe" set "JAVA_HOME=D:\Android studio\jbr"
  if exist "C:\Program Files\Android\Android Studio\jbr\bin\java.exe" set "JAVA_HOME=C:\Program Files\Android\Android Studio\jbr"
)
if defined JAVA_HOME set "PATH=%JAVA_HOME%\bin;%PATH%"
set GRADLE_VERSION=8.4
set GRADLE_DIR=%CD%\.gradle-local\gradle-%GRADLE_VERSION%
set GRADLE_ZIP=%CD%\.gradle-local\gradle.zip

if not exist "%GRADLE_DIR%\bin\gradle.bat" (
  echo Gradle %GRADLE_VERSION% yuklanmoqda. Birinchi marta 5-20 daqiqa olishi mumkin...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "New-Item -ItemType Directory -Force '.gradle-local' | Out-Null; Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-8.4-bin.zip' -OutFile '.gradle-local\gradle.zip'; Expand-Archive '.gradle-local\gradle.zip' -DestinationPath '.gradle-local' -Force"
)

call "%GRADLE_DIR%\bin\gradle.bat" %*
