--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

-- Started on 2017-06-20 10:25:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2120 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 184 (class 1259 OID 16559)
-- Name: labs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE labs (
    labname character varying,
    address character varying,
    city character varying,
    zipcode character varying,
    id integer
);


ALTER TABLE labs OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 16547)
-- Name: test; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE test (
    test character varying NOT NULL,
    cost integer,
    id integer NOT NULL
);


ALTER TABLE test OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 16553)
-- Name: testtaken; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE testtaken (
    email character varying,
    testid integer NOT NULL,
    takendate date NOT NULL,
    labid integer,
    useraddress character varying NOT NULL
);


ALTER TABLE testtaken OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 16533)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    firstname character varying NOT NULL,
    lastname character varying,
    "DOB" date NOT NULL,
    email character varying NOT NULL,
    password character varying NOT NULL,
    createddate date NOT NULL
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 2112 (class 0 OID 16559)
-- Dependencies: 184
-- Data for Name: labs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY labs (labname, address, city, zipcode, id) FROM stdin;
xyz lab	xy	delhi	34222	1
xyz2 lab	xyz	delhi	34222	2
xyz2 lab	xyz	chennai	4422	3
\.


--
-- TOC entry 2110 (class 0 OID 16547)
-- Dependencies: 182
-- Data for Name: test; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY test (test, cost, id) FROM stdin;
sugar	100	1
bp	100	2
eye	200	3
full scan	300	4
\.


--
-- TOC entry 2111 (class 0 OID 16553)
-- Dependencies: 183
-- Data for Name: testtaken; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY testtaken (email, testid, takendate, labid, useraddress) FROM stdin;
user1@company.com	1	2017-06-20	1	
user1@company.com	1	2017-06-20	1	
user1@company.com	3	2017-06-20	2	
user1@company.com	3	2017-06-20	2	
user1@company.com	3	2017-06-20	2	
user1@company.com	3	2017-06-20	2	
user1@company.com	4	2017-06-20	2	sdfdfd
user1@company.com	3	2017-06-20	2	dsfdfdfdsf
user1@company.com	2	2017-06-20	2	fgfgfdgfdg
user1@company.com	3	2017-06-20	1	erwewt
user1@company.com	3	2017-12-31	1	dfdsf
user1@company.com	1	2017-12-31	1	ffdgfdgfdg
user1@company.com	1	2017-12-31	1	fdgfgfdg
\.


--
-- TOC entry 2109 (class 0 OID 16533)
-- Dependencies: 181
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY users (firstname, lastname, "DOB", email, password, createddate) FROM stdin;
pandi	rajan	2017-12-31	user1@company.com	pandi	2017-06-18
pandi	rajangam	2017-12-31	user1@company.com	pandi_1989	2017-06-18
dfsdsf	dfdsf	2017-12-31	user2@company.com	pandi	2017-06-18
<pandi>	wdwd	2017-12-31	wdwd2.@hh.com	wswsws	2017-06-18
\.


--
-- TOC entry 2119 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2017-06-20 10:25:50

--
-- PostgreSQL database dump complete
--

