insert into source (id, name, url) values('bbc-news', 'BBC News', 'https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=aa1eed5864ed4b09be2bae237cfd1db9');
insert into source (id, name, url) values('usa-today', 'USA Today', 'https://newsapi.org/v2/everything?sources=usa-today&apiKey=aa1eed5864ed4b09be2bae237cfd1db9');
insert into source (id, name, url) values('abc-news', 'ABC News', 'https://newsapi.org/v2/everything?sources=espn&apiKey=aa1eed5864ed4b09be2bae237cfd1db9');
insert into source (id, name, url) values('bloomberg', 'Bloomberg', 'https://newsapi.org/v2/everything?sources=bloomberg&apiKey=aa1eed5864ed4b09be2bae237cfd1db9');

insert into user (id, active, firstname, lastname, password, username) values ('1', '1', 'john', 'smith', '$2a$10$EQXlrL9BjMN8aui2n06agOK8pEyXpUU8v2Mt1HWJ8FOr.Z1j/MyMq', 'jsmith');
insert into user_role (user_id, roles) values ('1', 'API_USER');
