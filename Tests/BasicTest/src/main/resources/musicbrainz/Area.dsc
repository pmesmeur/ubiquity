                                                        Table "musicbrainz.area"
      Column      |           Type           |                     Modifiers                     | Storage  | Stats target | Description 
------------------+--------------------------+---------------------------------------------------+----------+--------------+-------------
 id               | integer                  | not null default nextval('area_id_seq'::regclass) | plain    |              | 
 gid              | uuid                     | not null                                          | plain    |              | 
 name             | character varying        | not null                                          | extended |              | 
 type             | integer                  |                                                   | plain    |              | 
 edits_pending    | integer                  | not null default 0                                | plain    |              | 
 last_updated     | timestamp with time zone | default now()                                     | plain    |              | 
 begin_date_year  | smallint                 |                                                   | plain    |              | 
 begin_date_month | smallint                 |                                                   | plain    |              | 
 begin_date_day   | smallint                 |                                                   | plain    |              | 
 end_date_year    | smallint                 |                                                   | plain    |              | 
 end_date_month   | smallint                 |                                                   | plain    |              | 
 end_date_day     | smallint                 |                                                   | plain    |              | 
 ended            | boolean                  | not null default false                            | plain    |              | 
 comment          | character varying(255)   | not null default ''::character varying            | extended |              | 
Indexes:
    "area_pkey" PRIMARY KEY, btree (id)
    "area_idx_gid" UNIQUE, btree (gid)
    "area_idx_name" btree (name)
    "area_idx_name_txt" gin (to_tsvector('mb_simple'::regconfig, name::text))
Check constraints:
    "area_check" CHECK ((end_date_year IS NOT NULL OR end_date_month IS NOT NULL OR end_date_day IS NOT NULL) AND ended = true OR end_date_year IS NULL AND end_date_month IS NULL AND end_date_day IS NULL)
    "area_edits_pending_check" CHECK (edits_pending >= 0)
Has OIDs: no

