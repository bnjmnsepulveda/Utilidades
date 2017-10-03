
--agregar columna POSTGRES
ALTER TABLE tabla ADD COLUMN columna VARCHAR(27) DEFAULT 'default value';

ALTER TABLE tabla ADD PRIMARY KEY(id)

ALTER TABLE my_table 
ADD CONSTRAINT my_fk 
FOREIGN KEY (my_field) 
REFERENCES my_foreign_table(id) 
ON DELETE CASCADE;