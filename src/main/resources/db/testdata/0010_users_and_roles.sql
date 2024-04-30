insert into
    users (email,login, password,is_active)
values ('admin@wp.pl','admin', '{noop}admin','true'),
       ('moderator@wp.pl','moderator', '{noop}moderator','true'),
       ('user@wp.pl','user', '{noop}user','true'),
       ('tomek@wp.pl','tomek', '{noop}tomek','false');

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