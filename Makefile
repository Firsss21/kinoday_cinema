#!/usr/bin/env bash
build:
	mvn clean --quiet package -Dmaven.test.skip=true -Drun.jvmArguments="-Dspring.profiles.active=prod" --quiet spring-boot:repackage -Drun.jvmArguments="-Dspring.profiles.active=prod" -Dmaven.test.skip=true -Dspring.profiles.active=prod
	cp "target/app.jar" "app.jar";
	rm -rf target
