CREATE SEQUENCE IF NOT EXISTS customer_details_seq
    INCREMENT 50
    START 1;

CREATE SEQUENCE IF NOT EXISTS ride_details_seq
    INCREMENT 50
    START 1;

CREATE SEQUENCE IF NOT EXISTS taxi_details_seq
    INCREMENT 50
    START 1;

CREATE TABLE IF NOT EXISTS customer_details
(
    customerid integer NOT NULL DEFAULT nextval('customer_details_seq'::regclass),
    customername character varying(255) ,
    CONSTRAINT customer_details_pkey PRIMARY KEY (customerid)
);

CREATE TABLE IF NOT EXISTS ride_details
(
    rideid integer NOT NULL DEFAULT nextval('ride_details_seq'::regclass),
    taxiid integer,
    customerid integer,
    ridedate date,
    pickuppoint character(1) ,
    droppoint character(1) ,
    distance integer,
    fare double precision,
    pickuptime timestamp,
    droptime timestamp,
    CONSTRAINT ride_details_pkey PRIMARY KEY (rideid)
);

CREATE TABLE IF NOT EXISTS taxi_details
(
    taxiid integer NOT NULL DEFAULT nextval('taxi_details_seq'::regclass),
    make character varying(255) ,
    taxinumber character varying(255) ,
    currentlocation character(1) ,
    drivername character varying(255) ,
    status character varying(255) ,
    CONSTRAINT taxi_details_pkey PRIMARY KEY (taxiid)
);
