INSERT INTO users (user_id, username, email, password) VALUES
    -- password
    (0, 'alex', 'alex@mail.com', '$2y$12$wKABruN3P/2y6coluFljnO6X3OcNTUTO1GjhSyferjch1aSAlfpnW'),
    (1, 'orn.amos', 'asa.feil@example.com', 'db11a60d393f36165d1972dd3805e626d3d342f4'),
    (2, 'alison.stamm', 'smith.oceane@example.net', '1d82713db7d55d2ee2d3f0610c147269dbfe188f'),
    (3, 'kayla89', 'kutch.linwood@example.com', '3365107b13ffc1d96c74ca4d146d903234a875f2'),
    (4, 'rhoda96', 'barton.wyman@example.org', 'f4d8453b917ad0462b8363b09c145341d8d88761'),
    (5, 'kurt.tillman', 'cary52@example.org', '7d82981d4cf4883f3b142c6d9b9be2124cb832a7'),
    (6, 'feil.mitchell', 'zelda56@example.org', '6aa11632c9f4d7af2d118476e487510a07091a34'),
    (7, 'sandra.goyette', 'stanford.pagac@example.com', 'b82e8734bf0ea808083a02cf9bc1d24a5c54eb7d'),
    (8, 'dora.yost', 'jmaggio@example.net', '9f75492798241cf37f809c1826d80347dac20d13'),
    (9, 'myrtis.mann', 'gcassin@example.net', '24640dade985e189b149cf31c993e187d06de7e9'),
    (10, 'stella89', 'major.ortiz@example.com', '8dd4604745edaec073b2d525d50bb1583d99b79a'),
    (11, 'yost.jacky', 'eliezer24@example.org', 'efd3c12a0fe7c13d04061ecd9a06fd15ece41b04'),
    (12, 'jayson73', 'lori.collins@example.org', 'a4cbd6bfe1970dbea32ad73e22cf9ab4742d5a23');


INSERT INTO user_roles (user_id, role) VALUES 
    (0, 'ADMIN')
    ,(0, 'MOD')
    ,(1, 'MOD')
    ,(2, 'user');