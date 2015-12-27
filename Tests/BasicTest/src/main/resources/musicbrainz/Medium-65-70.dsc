                                                       Table "musicbrainz.medium"
    Column     |           Type           |                      Modifiers                      | Storage  | Stats target | Description 
---------------+--------------------------+-----------------------------------------------------+----------+--------------+-------------
 id            | integer                  | not null default nextval('medium_id_seq'::regclass) | plain    |              | 
 release       | integer                  | not null                                            | plain    |              | 
 format        | integer                  |                                                     | plain    |              |
 name          | character varying        | not null default ''::character varying              | extended |              | 
 track_count   | integer                  | not null default 0                                  | plain    |              |
Indexes:
    "medium_pkey" PRIMARY KEY, btree (id)
    "medium_idx_release_position" btree (release)
    "medium_idx_track_count" btree (track_count)

