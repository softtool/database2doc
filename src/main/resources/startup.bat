@echo off
start javaw -jar -Dspring.config.location=%cd%\application.properties  %cd%\joke.jar
exit