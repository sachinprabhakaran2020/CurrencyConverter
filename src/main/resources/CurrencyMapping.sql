DROP TABLE CURRENCY_MAPPING IF EXISTS;
 
CREATE TABLE CURRENCY_MAPPING  (
    ID NUMBER,  
    FROM_CURRENCY VARCHAR(100),  
    TO_CURRENCY VARCHAR(100),
    PROCESS VARCHAR(100)
) ;