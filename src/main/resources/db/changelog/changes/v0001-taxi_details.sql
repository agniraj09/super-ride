CREATE SEQUENCE IF NOT EXISTS taxi_details_seq
    INCREMENT 50
    START 1;

CREATE TABLE IF NOT EXISTS taxi_details
(
    taxiid bigint NOT NULL DEFAULT nextval('taxi_details_seq'::regclass),
    make character varying(255) ,
    taxinumber character varying(255) ,
    currentlocation character(1) ,
    drivername character varying(255) ,
    status character varying(255) ,
    CONSTRAINT taxi_details_pkey PRIMARY KEY (taxiid)
);
