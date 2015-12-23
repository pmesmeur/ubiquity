                                                       Table "musicbrainz.recording"
    Column     |           Type           |                       Modifiers                        | Storage  | Stats target | Description 
---------------+--------------------------+--------------------------------------------------------+----------+--------------+-------------
 id            | integer                  | not null default nextval('recording_id_seq'::regclass) | plain    |              | 
 name          | character varying        | not null                                               | extended |              |
 artist_credit | integer                  | not null                                               | plain    |              | 
 comment       | character varying(255)   | not null default ''::character varying                 | extended |              |
Indexes:
    "recording_pkey" PRIMARY KEY, btree (id)
    "recording_idx_artist_credit" btree (artist_credit)
    "recording_idx_name" btree (name)


