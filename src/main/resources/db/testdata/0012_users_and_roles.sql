insert into
    users (email,login, password,is_active,temporary_token_id)
values ('admin@wp.pl','admin', '{noop}admin','true',null),
       ('moderator@wp.pl','moderator', '{noop}moderator','true',null),
       ('user@wp.pl','user', '{noop}user','true',null),
       ('tomek@wp.pl','tomek', '{noop}tomek','false',1);

insert into
    user_role(name, description)
values ('ADMIN', 'Pełny dostęp'),
       ('MODERATOR', 'Podstawowe uprawnienia + zarządzanie treściami i użytkownikami'),
       ('USER', 'Podstawowe uprawnienia, możliwość głosowania/komentowania');

insert into
    user_roles(user_id,role_id)
values
    (1,1),
    (2,2),
    (3,3);
INSERT INTO temporary_token(token_name, token, token_experience_time,last_token_send)
VALUES ('PASSWORD_RESET', 'TEST', TIMESTAMP '2024-05-07 13:26:42',TIMESTAMP '2024-05-07 13:26:42');