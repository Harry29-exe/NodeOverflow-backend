INSERT INTO projects (owner_id, access_modifier, title, creation_date, last_modified) VALUES
    (0, 'PRIVATE', 'proj1', '1999-01-08 04:05:06', '1999-01-08 04:05:06'),
    (0, 'PRIVATE', 'proj1a', '2015-01-08 04:05:06', '2015-01-09 04:05:06'),
    (1, 'PRIVATE', 'bestProj', '2020-05-01 12:55:03', '2020-05-01 14:55:03');

INSERT INTO project_tags(project_id, tag_content) VALUES
     (1, '#first project')
    ,(1, '#best proj')
    ,(2, 'gutten projecten');

INSERT INTO project_data(project_id, project_data) VALUES
     (1, '{"nodes": [ {}, {}], "links": [ {} ] }')
    ,(2, '{"nodes": [ {}, {}], "links": [ {} ] }')
    ,(3, '{"nodes": [ {}, {}], "links": [ {} ] }');

-- GRANT SELECT, INSERT, UPDATE, UPDATE
--     ON ALL TABLES IN SCHEMA public
--     TO Backend;