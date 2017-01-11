Insert into EG_ACTION(ID,NAME,URL,QUERYPARAMS,PARENTMODULE,ORDERNUMBER,DISPLAYNAME,ENABLED,CONTEXTROOT,VERSION,CREATEDBY,CREATEDDATE,LASTMODIFIEDBY,LASTMODIFIEDDATE,APPLICATION) values (NEXTVAL('SEQ_EG_ACTION'),'ShowHideApproverDetails','/abstractestimate/ajax-showhideappravaldetails',null,(select id from EG_MODULE where name = 'WorksAbstractEstimate'),1,'ShowHideApproverDetails','false','egworks',0,1,now(),1,now(),(select id from eg_module where name = 'Works Management'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name = 'Super User'),(select id from eg_action where name = 'ShowHideApproverDetails' and contextroot = 'egworks'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name = 'Works Creator'),(select id from eg_action where name = 'ShowHideApproverDetails' and contextroot  = 'egworks'));

--rollback delete FROM EG_ROLEACTION WHERE roleid = (SELECT id FROM eg_role WHERE name = 'Works Creator') and actionid = (SELECT id FROM eg_action WHERE name = 'ShowHideApproverDetails' and contextroot = 'egworks');
--rollback delete FROM EG_ROLEACTION WHERE roleid = (SELECT id FROM eg_role WHERE name = 'Super User') and actionid = (SELECT id FROM eg_action WHERE name ='ShowHideApproverDetails' and contextroot = 'egworks');
--rollback delete FROM EG_ACTION WHERE name = 'ShowHideApproverDetails' and contextroot = 'egworks';