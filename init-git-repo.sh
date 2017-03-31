#!/bin/bash

"nbproject/" >> .gitignore
build.xml >> .gitignore
build/ >> .gitignore
dist/ >> .gitignore
manifest.mf >> .gitignore

mkdir config
mkdir log

touch config/configuracion.properties

git init
git add -A
git commit -m 'Inicio de repositorio'
git branch developer
