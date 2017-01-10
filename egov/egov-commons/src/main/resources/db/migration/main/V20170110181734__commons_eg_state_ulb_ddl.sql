CREATE TABLE public.eg_state
(
  id bigint NOT NULL,
  name character varying(100) NOT NULL,
  code character varying(100) NOT NULL,
  description character varying(1024),
  version bigint,
  CONSTRAINT pk_eg_state PRIMARY KEY (id)
);

CREATE SEQUENCE public.seq_eg_state;

CREATE TABLE public.eg_ulb
(
  id bigint NOT NULL,
  name character varying(100) NOT NULL,
  code character varying(100) NOT NULL,
  description character varying(1024),
  state bigint NOT NULL,
  version bigint,
  CONSTRAINT pk_eg_ulb PRIMARY KEY (id),
  CONSTRAINT fk_pk_eg_state_id FOREIGN KEY (state) REFERENCES eg_state (id)
);


CREATE SEQUENCE public.seq_eg_ulb;


CREATE TABLE public.eg_serviceimplementations
(
  id bigint NOT NULL,
  state bigint,
  ulb bigint,
  modulename character varying(100) NOT NULL,
  serviceclass character varying(1024) NOT NULL,
  implementationclass character varying(1024) NOT NULL,
  version bigint,
  CONSTRAINT pk_eg_serviceimplementations PRIMARY KEY (id),
  CONSTRAINT fk_pk_eg_si_state_id FOREIGN KEY (state) REFERENCES eg_state (id),
  CONSTRAINT fk_pk_eg_si_ulb_id FOREIGN KEY (state) REFERENCES eg_ulb (id)
);

CREATE SEQUENCE public.seq_eg_serviceimplementations;
