                                                        Table "musicbrainz.area"
      Column      |           Type           |                     Modifiers                     | Storage  | Stats target | Description 
------------------+--------------------------+---------------------------------------------------+----------+--------------+-------------
 id               | integer                  | not null default nextval('area_id_seq'::regclass) | plain    |              | 
 name             | character varying        | not null                                          | extended |              | 
 type             | integer                  |                                                   | plain    |              | 
 comment          | character varying(255)   | not null default ''::character varying            | extended |              | 
Indexes:
    "area_pkey" PRIMARY KEY, btree (id)
    "area_idx_name" btree (name)
