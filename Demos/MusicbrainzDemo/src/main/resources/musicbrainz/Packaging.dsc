                                                     Table "musicbrainz.release_packaging"
   Column    |          Type          |                           Modifiers                            | Storage  | Stats target | Description 
-------------+------------------------+----------------------------------------------------------------+----------+--------------+-------------
 id          | integer                | not null default nextval('release_packaging_id_seq'::regclass) | plain    |              | 
 name        | character varying(255) | not null                                                       | extended |              | 
 parent      | integer                |                                                                | plain    |              | 
 child_order | integer                | not null default 0                                             | plain    |              | 
 description | text                   |                                                                | extended |              | 
Indexes:
    "release_packaging_pkey" PRIMARY KEY, btree (id)

