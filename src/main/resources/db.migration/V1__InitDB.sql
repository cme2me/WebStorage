create table if not exists storage_files (id uuid not null, comment varchar(2048), data oid,
     date timestamp, format varchar(255), name varchar(255), updated_date timestamp, primary key (id));