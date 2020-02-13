SET DEFINE OFF

CREATE TABLE tool ( name VARCHAR2(26),
quantity INTEGER,
purchase_date DATE,
price NUMBER(38, 2));



INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('filament złoty 500g', 3, to_date('20-01-2019', 'DD-MM-YYYY'), 50.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('filament czarny 500g', 20, to_date('05-01-2020', 'DD-MM-YYYY'), 50.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('filament niebieski 500g', 10, to_date('07-03-2021', 'DD-MM-YYYY'), 50.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('Poliwęglan 5mm 1000x1000', 4, to_date('12-08-2019', 'DD-MM-YYYY'), 150.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('Poliwęglan 4mm 1000x1000', 3, to_date('12-08-2020', 'DD-MM-YYYY'), 140.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('Poliwęglan 3mm 1000x1000', 2, to_date('07-07-2018', 'DD-MM-YYYY'), 130.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('Płyta OCB 6mm 2500x1200', 3, to_date('13-05-2018', 'DD-MM-YYYY'), 200.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('Aluminium 10mm 150x100', 2, to_date('14-06-2016', 'DD-MM-YYYY'), 100.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('Sklejka 3mm 1000x1000', 5, to_date('20-10-2015', 'DD-MM-YYYY'), 80.00);

INSERT INTO tool (name, quantity, purchase_date, price) 
VALUES ('Sklejka 5mm 1000x1000', 2, to_date('20-10-2016', 'DD-MM-YYYY'), 90.00);