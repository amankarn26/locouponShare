ALTER DATABASE locoupons OWNER TO postgres;


SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE version (
     version varchar(4) NOT NULL,
     updated timestamp DEFAULT current_timestamp,
     description text
);

INSERT INTO version (version) VALUES (1.0);
INSERT INTO version (version) VALUES (1.1);
INSERT INTO version (version) VALUES (1.2);

ALTER TABLE public.version OWNER TO postgres;

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

CREATE TABLE document (
    id bigint NOT NULL,
	docName character varying(127) NOT NULL,
	docType character varying(127) NOT NULL,
	docpath character varying(255),
	docdesc character varying(255),
	updatedon timestamp with time zone,
	urllink character varying(128), -- tostore google response link
	PRIMARY KEY (id)
);

ALTER TABLE public.document OWNER TO postgres;

CREATE UNIQUE INDEX u_doc1 on document (docpath);

CREATE TABLE login (
    id bigint NOT NULL,
	email character varying(16), 
    password character varying(32) , -- use 4 digit password
	type character varying(32) NOT  NULL, -- MERCHANT/ADMIN/CUSTOMER/RM
	PRIMARY KEY (id)
);
ALTER TABLE public.login OWNER TO postgres;

CREATE UNIQUE INDEX login_uni1 on login (email); -- username is phone.


CREATE TABLE config
(
  cid integer NOT NULL,
  name character varying(64),
  value character varying(128)
);

ALTER TABLE public.config OWNER TO postgres;


CREATE TABLE address (
    id bigint NOT NULL,
    line1 character varying(64) NOT NULL,
	line2 character varying(64),
    city character varying(64) NOT NULL,
	pincode character varying(6) NOT NULL,
	states character varying(64) NOT NULL,
	country character varying(64) NOT NULL,
	latitude decimal(10,7),
	longitude decimal(10,7),
	PRIMARY KEY (id)
);

ALTER TABLE public.address OWNER TO postgres;


CREATE TABLE customer (
	id bigint NOT NULL,
	name character varying(512), 
	phone character varying(16) NOT NULL,
	email character varying(255),
	address bigint,
	login bigint NOT NULL,
	addedon Timestamp with time zone NOT NULL,
	city character varying(127) NOT NULL,
	photo bigint, -- notnull
	ewallet bigint, -- notnull
	pan character varying(32),
	aadhar character varying(32),
	PRIMARY KEY (id)
);

ALTER TABLE public.customer OWNER TO postgres;

ALTER TABLE "customer"
ADD FOREIGN KEY ("photo") REFERENCES "document" ("id") ON DELETE CASCADE;


CREATE TABLE customer_files(
    customer bigint NOT NULL,
    doc bigint NOT NULL
);
ALTER TABLE public.customer_files OWNER TO postgres;

ALTER TABLE "customer_files"
ADD FOREIGN KEY ("customer") REFERENCES "customer" ("id") ON DELETE CASCADE;

ALTER TABLE "customer_files"
ADD FOREIGN KEY ("doc") REFERENCES "document" ("id");



CREATE TABLE merchant (
	id bigint NOT NULL,
	name character varying(512), 
	phone character varying(16) NOT NULL,
	email character varying(255),
	login bigint NOT NULL,
	address bigint,
	addedon Timestamp with time zone NOT NULL,
	city character varying(127) NOT NULL,
	photo bigint, -- notnull
	ewallet bigint, -- notnull
	pan character varying(32),
	aadhar character varying(32),
	status character varying(16),
	PRIMARY KEY (id)
);

ALTER TABLE public.merchant OWNER TO postgres;

ALTER TABLE "merchant"
ADD FOREIGN KEY ("photo") REFERENCES "document" ("id") ON DELETE CASCADE;


CREATE TABLE merchant_files(
    merchant bigint NOT NULL,
    doc bigint NOT NULL
);
ALTER TABLE public.merchant_files OWNER TO postgres;

ALTER TABLE "merchant_files"
ADD FOREIGN KEY ("merchant") REFERENCES "merchant" ("id") ON DELETE CASCADE;

ALTER TABLE "merchant_files"
ADD FOREIGN KEY ("doc") REFERENCES "document" ("id");


CREATE TABLE employee (
	id bigint NOT NULL,
	name character varying(512), 
	phone character varying(16) NOT NULL,
	email character varying(255),
	login bigint NOT NULL,
	address bigint,
	addedon Timestamp with time zone NOT NULL,
	city character varying(127) NOT NULL,
	photo bigint, -- notnull
	ewallet bigint, -- notnull
	status character varying(16),
	PRIMARY KEY (id)
);

ALTER TABLE public.employee OWNER TO postgres;

ALTER TABLE "employee"
ADD FOREIGN KEY ("photo") REFERENCES "document" ("id") ON DELETE CASCADE;



CREATE TABLE category (  
    id bigint NOT NULL,
	name character varying(32) NOT NULL,
	photo bigint, -- size 300X100.
	PRIMARY KEY (id)
);
ALTER TABLE public.category OWNER TO postgres;

CREATE UNIQUE INDEX category_uni1 on category (name);

CREATE TABLE prodtype (
    id bigint NOT NULL,
	category character varying(64) NOT NULL,
	name character varying(32) NOT NULL, 
    description character varying(512) NOT NULL,
	photo bigint, -- size 300X100.
	PRIMARY KEY (id)
);
ALTER TABLE public.prodtype OWNER TO postgres;

CREATE UNIQUE INDEX prodtype_uni1 on prodtype (category,name);



CREATE TABLE product (
    id bigint NOT NULL,
    name character varying(127) NOT NULL, 
	prodtype character varying(32) NOT NULL,  
	brand character varying(32) NOT NULL,
	details character varying(512),
	actualprice  decimal(10,2),
	pointprice  decimal(10,2),
	status character varying(32) NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE public.product OWNER TO postgres;

CREATE UNIQUE INDEX product_uni1 on product (name);

CREATE TABLE product_photos(
    product bigint NOT NULL,
    doc bigint NOT NULL
);
ALTER TABLE public.product_photos OWNER TO postgres;

ALTER TABLE "product_photos"
ADD FOREIGN KEY ("product") REFERENCES "product" ("id") ON DELETE CASCADE;

ALTER TABLE "product_photos"
ADD FOREIGN KEY ("doc") REFERENCES "document" ("id");


CREATE TABLE merchant_offer(
	id bigint NOT NULL,
	product bigint NOT NULL,
	merchant bigint NOT NULL,
	mname character varying(127) NOT NULL, 
	details character varying(32) NOT NULL,
	location character varying(64) NOT NULL,  
	carpassing character varying(127) NOT NULL,
	price decimal(10,2) NOT NULL, 
	gst decimal(10,2),
    status character varying(16) NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE public.merchant_offer OWNER TO postgres;

-- In add to cart, on variant selection send rateperunit, looserateperunit, balanceqty, volumeperunit and balancevolume.

ALTER TABLE "merchant_offer"
ADD FOREIGN KEY ("product") REFERENCES "product" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

ALTER TABLE "merchant_offer"
ADD FOREIGN KEY ("merchant") REFERENCES "merchant" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

CREATE UNIQUE INDEX moffer on merchant_offer (product, merchant); 


CREATE TABLE discount
(
  id int NOT NULL,
  name character varying(64) NOT NULL, -- Valentine Special
  couponcode character varying(8) NOT NULL,  -- AAE899 ?couponcode=AAE899&cartvalue=1000
  discount decimal(10,2) NOT NULL, -- 25% -- valid=YES/NO, discount=20%, cartvalue= 800
  validtill Date NOT NULL, -- 05-03 
  minvalue int, 
  photo bigint
);

CREATE UNIQUE INDEX disc_1 on discount (couponcode);

ALTER TABLE "discount"
ADD FOREIGN KEY ("photo") REFERENCES "document" ("id") ON DELETE CASCADE;


CREATE TABLE ewallet
(
  id bigint NOT NULL,
  points int NOT NULL, --0 -- 100
  total decimal(10,2) NOT NULL, --0 -- 50
  availed decimal(10,2) NOT NULL,
  balance decimal(10,2) NOT NULL,
  validtill date,
  PRIMARY KEY (id)
);


CREATE TABLE ordersale (
    id bigint NOT NULL,
	merchant bigint NOT NULL,
	customer bigint NOT NULL,
	custname character varying(64) NOT NULL,
	custmobile character varying(16) NOT NULL,
	custcity character varying(64) NOT NULL,
	invoicenum character varying(64),  -- generate and show read only
	invoicedate Timestamp with time zone NOT NULL,
	qcost bigint,
	invoice bigint,
	comments character varying(512), 
	status character varying(32) NOT NULL, -- enum PLACED/ACCEPTED/IN_PROCESS/PACKED/DELIVERED
	paymentmode character varying(32) NOT NULL,
	favourite character varying(3) NOT NULL, -- YES/NO default NO.
	PRIMARY KEY (id)
);
ALTER TABLE public.ordersale OWNER TO postgres;


ALTER TABLE "ordersale"
ADD FOREIGN KEY ("merchant") REFERENCES "merchant" ("id") ON DELETE CASCADE;

ALTER TABLE "ordersale"
ADD FOREIGN KEY ("customer") REFERENCES "customer" ("id") ON DELETE CASCADE;

ALTER TABLE "ordersale"
ADD FOREIGN KEY ("invoice") REFERENCES "document" ("id") ON DELETE SET NULL;

CREATE UNIQUE INDEX order_uni1 on ordersale (invoicenum);

CREATE TABLE order_entry(
	id bigint NOT NULL,
	orderid bigint NOT NULL, --if booking
	pname character varying(64) NOT NULL,
	actualprice decimal(10,2) NOT NULL,
	pointprice decimal(10,2) NOT NULL,
	qty decimal(10,2) NOT NULL,
	amount decimal(10,2) NOT NULL,
	gst decimal(10,2) NOT NULL,
	gstamt decimal(10,2) NOT NULL,
	entrytotal decimal(10,2) NOT NULL,
	PRIMARY KEY (id)
);
ALTER TABLE public.order_entry OWNER TO postgres;

ALTER TABLE "order_entry"
ADD FOREIGN KEY ("orderid") REFERENCES "ordersale" ("id") ON DELETE CASCADE;


CREATE TABLE qcost (
    id bigint NOT NULL,
	totalitem int NOT NULL,
	baseprice decimal(10,2) NOT NULL,
	gstamt decimal(10,2) NOT NULL,
	bookingamt decimal(10,2) NOT NULL, 
	balanceamt decimal(10,2) NOT NULL,
	finalamt decimal(10,2) NOT NULL,
	discountrate int,
	discountvalue decimal(10,2),
	useewallet character varying(6) NOT NULL, 
	ewalletamt decimal(10,2) NOT NULL, 
	PRIMARY KEY (id)
);
ALTER TABLE public.qcost OWNER TO postgres;

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;

