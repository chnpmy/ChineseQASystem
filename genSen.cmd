@echo off
cd NiuParser-v1.0.0-Beta/bin/
"NiuParser-v1.0.0-beta-for-research-win.exe" --WS -c ../config/niuparser.config -in ../../temp/sample/%1.txt -out ../../temp/sample/%1.out
exit