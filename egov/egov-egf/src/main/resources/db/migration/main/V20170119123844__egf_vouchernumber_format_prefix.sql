
 --vouchernumberprefix_receipt
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_receipt', 'voucher number format prefix for receipt type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_receipt' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
  'BRV',0);

 --vouchernumberprefix_payment
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_payment', 'voucher number format prefix for payment type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_payment' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 'BPV',0);

 --vouchernumberprefix_journal
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_journal', 'voucher number format prefix for journal type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_journal' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
  'GJV',0);

 --vouchernumberprefix_purchasejv
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_purchasejv', 'voucher number format prefix for purchasejv type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_purchasejv' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
   'PJV',0);
 
 --vouchernumberprefix_fixedassetjv
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_fixedassetjv', 'voucher number format prefix for fixedassetjv type',0,1,current_date,
  (select id from eg_module where name='EGF'));


INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_fixedassetjv' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 'FJV',0);
 
 --vouchernumberprefix_worksjv
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_worksjv', 'voucher number format prefix for worksjv type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_worksjv' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 'CJV',0);
 
 --vouchernumberprefix_contingentjv
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_contingentjv', 'voucher number format prefix for contingentjv type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_contingentjv' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 'EJV',0);
 
 --vouchernumberprefix_salaryjv
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_salaryjv', 'voucher number format prefix for salaryjv type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_salaryjv' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 'SJV',0);
 
 --vouchernumberprefix_contra
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_contra', 'voucher number format prefix for contra type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_contra' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 'CSL',0);
 
 --vouchernumberprefix_pensionjv
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_pensionjv', 'voucher number format prefix for pensionjv type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_pensionjv' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 'TJV',0);
 
 --vouchernumberprefix_advance
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_advance', 'voucher number format prefix for advance jv type',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_advance' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
  'AJV',0);

 --vouchernumberprefix_length
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_length', 'voucher number format length',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_length' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 '5',0);

 
 --vouchernumberprefix_sublength
insert into EG_APPCONFIG (ID,KEY_NAME,DESCRIPTION,VERSION,CREATEDBY,CREATEDDATE,MODULE) 
  values (nextval('seq_eg_appconfig'),'vouchernumberprefix_sublength', 'voucher number format sublength',0,1,current_date,
  (select id from eg_module where name='EGF'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),
(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='vouchernumberprefix_sublength' AND 
MODULE =(select id from eg_module where name='EGF')),current_date,
 '3',0);




 
