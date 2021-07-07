INSERT INTO users (user_id, username, email, password) VALUES
    -- password
    (0, 'alex', 'alex@mail.com', '$2y$12$wKABruN3P/2y6coluFljnO6X3OcNTUTO1GjhSyferjch1aSAlfpnW'),
    (1, 'examples', 'examples@node-overflow', '$2y$12$wKABruN3P/2y6coluFljnO6X3OcNTUTO1GjhSyferjch1aSAlfpnW');


INSERT INTO user_roles (user_id, role) VALUES 
    (0, '')
    ,(0, '')
    ,(1, 'MOD');