                                                       Table "musicbrainz.release"
    Column     |           Type           |                      Modifiers                       | Storage  | Stats target | Description 
---------------+--------------------------+------------------------------------------------------+----------+--------------+-------------
 id            | integer                  | not null default nextval('release_id_seq'::regclass) | plain    |              | 
 name          | character varying        | not null                                             | extended |              |
 artist_credit | integer                  | not null                                             | plain    |              | 
 packaging     | integer                  |                                                      | plain    |              |
 comment       | character varying(255)   | not null default ''::character varying               | extended |              |
Indexes:
    "release_pkey" PRIMARY KEY, btree (id)
    "release_idx_artist_credit" btree (artist_credit)
    "release_idx_name" btree (name)
Check constraints:
    "release_edits_pending_check" CHECK (edits_pending >= 0)
Has OIDs: no

