@echo off
cls
cd /d "%~dp0"
java -cp . src.DjApp
if %errorlevel% neq 0 (
    echo.
    echo [Aviso] Variaveis de ambiente ainda nao atualizadas. Usando caminho absoluto...
    "C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin\java.exe" -cp . src.DjApp
)
pause
