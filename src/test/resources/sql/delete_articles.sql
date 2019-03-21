set referential_integrity false;
begin transaction;
    delete from article;
commit;
set referential_integrity true;
