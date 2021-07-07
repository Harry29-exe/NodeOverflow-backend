INSERT INTO projects (owner_id, access_modifier, title, creation_date, last_modified) VALUES
    (1, 'PUBLIC', 'Introduction');

INSERT INTO project_tags(project_id, tag_content) VALUES
     (1, '#first project')
    ,(1, '#best proj')
    ,(2, 'gutten projecten');

INSERT INTO project_data(project_id, project_data) VALUES
    (1, '{"nodes": [ {}, {}], "links": [ {} ] }')
    ,(2, '{"nodes": [ {}, {}], "links": [ {} ] }')
    ,(3, '{"nodes": [ {}, {}], "links": [ {} ] }');
