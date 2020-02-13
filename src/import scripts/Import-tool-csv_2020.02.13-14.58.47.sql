SET DEFINE OFF

CREATE TABLE tool ( name VARCHAR2(26),
purchase_date DATE,
price NUMBER(38, 2));



INSERT INTO tool (name, purchase_date, price) 
VALUES ('młotek', to_date('01-01-2010', 'DD-MM-YYYY'), 25.00);

INSERT INTO tool (name, purchase_date, price) 
VALUES ('śrubokręt', to_date('02-03-2009', 'DD-MM-YYYY'), 20.00);

INSERT INTO tool (name, purchase_date, price) 
VALUES ('kombinerki', to_date('04-04-2014', 'DD-MM-YYYY'), 30.00);

INSERT INTO tool (name, purchase_date, price) 
VALUES ('obcinaczki', to_date('04-04-2014', 'DD-MM-YYYY'), 20.00);

INSERT INTO tool (name, purchase_date, price) 
VALUES ('wiertarka', to_date('06-07-2007', 'DD-MM-YYYY'), 350.00);

INSERT INTO tool (name, purchase_date, price) 
VALUES ('piła ręczna', to_date('12-12-2012', 'DD-MM-YYYY'), 45.00);