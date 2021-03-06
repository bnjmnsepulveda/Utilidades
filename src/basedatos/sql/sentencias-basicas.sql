
--agregar columna POSTGRES
ALTER TABLE tabla ADD COLUMN columna VARCHAR(27) DEFAULT 'default value';

ALTER TABLE tabla ADD PRIMARY KEY(id)

ALTER TABLE my_table 
ADD CONSTRAINT my_fk 
FOREIGN KEY (my_field) 
REFERENCES my_foreign_table(id) 
ON DELETE CASCADE;

CREATE TABLE orders (
    order_id integer PRIMARY KEY,
    product_no integer REFERENCES products (product_no),
    quantity integer
);

--rango de fecha estatico
select * from persona WHERE to_char(persona.fecha_envio, 'YYYY/MM/DD') = to_char(NOW(), 'YYYY/MM/DD')