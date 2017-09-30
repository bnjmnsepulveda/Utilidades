--version postgres
SELECT version();
--psql -version

CREATE DATABASE basename;
DROP DATABASE basename;

--usuarios conectados base de datos
SELECT COUNT(*) AS users_online FROM pg_stat_activity WHERE datname='test_fail_over';
--eliminar conexiones base de datos
SELECT pg_terminate_backend(procpid) FROM pg_stat_activity WHERE datname = 'test_fail_over';

--------------------------------------------------------------------------------
---------------------------------ALTER TABLE------------------------------------
--------------------------------------------------------------------------------
--Cambiar el nombre a una columna:
ALTER TABLE empleados RENAME COLUMN nombre_actual TO nombre_nuevo
--Borrar una columna
ALTER TABLE empleados DROP COLUMN nombre_columna
--Agregar columna
ALTER TABLE empleados ADD COLUMN nombre_columna BOOLEAN DEFAULT true
--Eliminarle a una columna la restriccion de no aceptar valores nulos
ALTER TABLE empleados ALTER COLUMN nombre_columna DROP NOT NULL
--Modificar la columna para que apartir de ahora no acepte valores nulos
ALTER TABLE empledos ALTER COLUMN nombre_columna SET NOT NULL
--Modificar el tipo de dato a una columna
ALTER TABLE empleados ALTER COLUMN nombre_columna TYPE smallint
--Eliminar una Foreign-key constraint
ALTER TABLE empleados DROP CONSTRAINT nombre_foreign_key_fkey
--Agregar una foreign-key
ALTER TABLE empleados ADD FOREIGN KEY(nombre_columna) REFERENCES nomina(nombre_columna)
--Eliminar el valor que tiene por default una columna
ALTER TABLE empleados ALTER COLUMN nombre_columna DROP default
--Agregar un valor por default a una columna
ALTER TABLE empleados ALTER COLUMN nombre_columna SET DEFAULT 125
