                                                       Table "musicbrainz.track"
    Column     |           Type           |                     Modifiers                      | Storage  | Stats target | Description 
---------------+--------------------------+----------------------------------------------------+----------+--------------+-------------
 id            | integer                  | not null default nextval('track_id_seq'::regclass) | plain    |              | 
 recording     | integer                  | not null                                           | plain    |              |
 medium        | integer                  | not null                                           | plain    |              | 
 name          | character varying        | not null                                           | extended |              |
 artist_credit | integer                  | not null                                           | plain    |              | 
Indexes:
    "track_pkey" PRIMARY KEY, btree (id)
    "track_idx_artist_credit" btree (artist_credit)
    "track_idx_name" btree (name)
    "track_idx_recording" btree (recording)


