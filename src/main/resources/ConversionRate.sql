DROP TABLE CONVERSION_RATE IF EXISTS;
 
CREATE TABLE CONVERSION_RATE  (
    ID NUMBER,  
    FROM_CURRENCY VARCHAR(100),  
    TO_CURRENCY VARCHAR(100),
    CONVERSION_RATE DECIMAL
) ;

INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (1,'AUD','USD',0.8371);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (2,'CAD','USD',0.8711);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (3,'USD','CNY',6.1715);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (4,'EUR','USD',1.2315);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (5,'GBP','USD',1.5683);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (6,'NZD','USD',0.7750);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (7,'USD','JPY',119.95);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (8,'EUR','CZK',27.6028);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (9,'EUR','DKK',7.4405);
INSERT INTO CONVERSION_RATE (ID, FROM_CURRENCY, TO_CURRENCY, CONVERSION_RATE) VALUES (10,'EUR','NOK',8.6651);