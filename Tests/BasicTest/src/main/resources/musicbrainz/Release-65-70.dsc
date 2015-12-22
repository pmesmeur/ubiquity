                                                       Table "musicbrainz.release"
    Column     |           Type           |                      Modifiers                       | Storage  | Stats target | Description 
---------------+--------------------------+------------------------------------------------------+----------+--------------+-------------
 id            | integer                  | not null default nextval('release_id_seq'::regclass) | plain    |              | 
 gid           | uuid                     | not null                                             | plain    |              | 
 name          | character varying        | not null                                             | extended |              | 
 artist_credit | integer                  | not null                                             | plain    |              | 
 release_group | integer                  | not null                                             | plain    |              | 
 status        | integer                  |                                                      | plain    |              | 
 packaging     | integer                  |                                                      | plain    |              | 
 language      | integer                  |                                                      | plain    |              | 
 script        | integer                  |                                                      | plain    |              | 
 barcode       | character varying(255)   |                                                      | extended |              | 
 comment       | character varying(255)   | not null default ''::character varying               | extended |              | 
 edits_pending | integer                  | not null default 0                                   | plain    |              | 
 quality       | smallint                 | not null default (-1)                                | plain    |              | 
 last_updated  | timestamp with time zone | default now()                                        | plain    |              | 
Indexes:
    "release_pkey" PRIMARY KEY, btree (id)
    "release_idx_gid" UNIQUE, btree (gid)
    "release_idx_artist_credit" btree (artist_credit)
    "release_idx_musicbrainz_collate" btree (musicbrainz_collate(name::text))
    "release_idx_name" btree (name)
    "release_idx_release_group" btree (release_group)
    "release_idx_txt" gin (to_tsvector('mb_simple'::regconfig, name::text))
Check constraints:
    "release_edits_pending_check" CHECK (edits_pending >= 0)
Has OIDs: no

