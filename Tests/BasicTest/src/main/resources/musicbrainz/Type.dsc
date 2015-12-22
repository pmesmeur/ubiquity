                                                     Table "musicbrainz.artist_type"
   Column    |          Type          |                        Modifiers                         | Storage  | Stats target | Description 
-------------+------------------------+----------------------------------------------------------+----------+--------------+-------------
 id          | integer                | not null default nextval('artist_type_id_seq'::regclass) | plain    |              | 
 name        | character varying(255) | not null                                                 | extended |              | 
 parent      | integer                |                                                          | plain    |              | 
 child_order | integer                | not null default 0                                       | plain    |              | 
 description | text                   |                                                          | extended |              | 
Indexes:
    "artist_type_pkey" PRIMARY KEY, btree (id)
Has OIDs: no

