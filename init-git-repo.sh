#!/bin/bash

touch .gitignore

mkdir config
mkdir log

touch config/configuracion.properties

git init
git add -A
git commit -m 'Inicio de repositorio'
git checkout -b developer