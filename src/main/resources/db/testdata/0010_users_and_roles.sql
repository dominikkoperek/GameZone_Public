insert into
    users (email,login, password)
values ('admin@wp.pl','admin', '{noop}admin'),
       ('moderator@wp.pl','moderator', '{noop}moderator'),
       ('user@wp.pl','user', '{noop}user');
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