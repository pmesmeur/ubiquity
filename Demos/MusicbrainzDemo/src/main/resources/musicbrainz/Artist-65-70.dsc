                                                        Table "musicbrainz.artist"
      Column      |           Type           |                      Modifiers                      | Storage  | Stats target | Description 
------------------+--------------------------+-----------------------------------------------------+----------+--------------+-------------
 id               | integer                  | not null default nextval('artist_id_seq'::regclass) | plain    |              | 
 name             | character varying        | not null                                            | extended |              |
 begin_date_year  | smallint                 |                                                     | plain    |              |
 end_date_year    | smallint                 |                                                     | plain    |              |
 type             | integer                  |                                                     | plain    |              |
 area             | integer                  |                                                     | plain    |              | 
 comment          | character varying(255)   | not null default ''::character varying              | extended |              |
 ended            | boolean                  | not null default false                              | plain    |              |
Indexes:
    "artist_pkey" PRIMARY KEY, btree (id)
    "artist_idx_null_comment" UNIQUE, btree (name) WHERE comment IS NULL
    "artist_idx_area" btree (area)
    "artist_idx_name" btree (name)

