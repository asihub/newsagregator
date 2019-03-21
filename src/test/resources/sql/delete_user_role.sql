set referential_integrity false;
begin transaction;
  delete from user_role;
  delete from user;
commit;
set referential_integrity true;
