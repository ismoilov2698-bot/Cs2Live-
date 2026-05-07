@echo off
echo CS2 Live Wallpaper APK build boshlanmoqda...
call gradlew.bat assembleDebug
if %errorlevel% neq 0 (
  echo.
  echo BUILD FAILED. Xato matnini screenshot qilib yuboring.
  pause
  exit /b %errorlevel%
)
echo.
echo BUILD SUCCESSFUL!
echo APK shu joyda:
echo app\build\outputs\apk\debug\app-debug.apk
pause
