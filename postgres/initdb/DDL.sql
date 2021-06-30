CREATE TABLE users (
   user_id int8,
   username varchar(24),
   email varchar(60),
   password varchar(60),
   primary key (user_id)
);

CREATE TABLE user_roles (
    id SERIAL8,
    user_id int8 not null,
    role varchar(20) not null,
    primary key (id),
    foreign key (user_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE
);

CREATE TABLE projects (
    project_id SERIAL8,
    owner_id int8,
    access_modifier varchar(10),
    title varchar(40),
    creation_date timestamptz,
    last_modified timestamptz,
    primary key (project_id),
    foreign key (owner_id)
          REFERENCES users (user_id)
          ON DELETE CASCADE
);

CREATE TABLE project_collaborators (
    id SERIAL8,
    collaborator_id int8,
    project_id int8,
    canWrite boolean,
    canFork boolean,
    primary key (id),
    foreign key (collaborator_id)
        REFERENCES users (user_id)
        ON DELETE CASCADE
    ,foreign key (project_id)
        REFERENCES projects (project_id)
        ON DELETE CASCADE
);

CREATE TABLE project_tags (
    id SERIAL8,
    project_id int8,
    tag_content varchar(45),
    primary key (id),
    foreign key (project_id)
        REFERENCES projects (project_id)
        ON DELETE CASCADE
);

CREATE TABLE project_data (
    project_id int8,
    project_data text,
    primary key (project_id),
    foreign key (project_id)
        REFERENCES projects (project_id)
        ON DELETE CASCADE
);



