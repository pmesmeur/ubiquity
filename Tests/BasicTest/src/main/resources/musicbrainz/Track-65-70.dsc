                                                       Table "musicbrainz.track"
    Column     |           Type           |                     Modifiers                      | Storage  | Stats target | Description 
---------------+--------------------------+----------------------------------------------------+----------+--------------+-------------
 id            | integer                  | not null default nextval('track_id_seq'::regclass) | plain    |              | 
 gid           | uuid                     | not null                                           | plain    |              | 
 recording     | integer                  | not null                                           | plain    |              | 
 medium        | integer                  | not null                                           | plain    |              | 
 position      | integer                  | not null                                           | plain    |              | 
 number        | text                     | not null                                           | extended |              | 
 name          | character varying        | not null                                           | extended |              | 
 artist_credit | integer                  | not null                                           | plain    |              | 
 length        | integer                  |                                                    | plain    |              | 
 edits_pending | integer                  | not null default 0                                 | plain    |              | 
 last_updated  | timestamp with time zone | default now()                                      | plain    |              | 
 is_data_track | boolean                  | not null default false                             | plain    |              | 
Indexes:
    "track_pkey" PRIMARY KEY, btree (id)
    "track_idx_gid" UNIQUE, btree (gid)
    "track_idx_artist_credit" btree (artist_credit)
    "track_idx_medium_position" btree (medium, "position")
    "track_idx_musicbrainz_collate" btree (musicbrainz_collate(name::text))
    "track_idx_name" btree (name)
    "track_idx_recording" btree (recording)
    "track_idx_txt" gin (to_tsvector('mb_simple'::regconfig, name::text))
Check constraints:
    "track_edits_pending_check" CHECK (edits_pending >= 0)
    "track_length_check" CHECK (length IS NULL OR length > 0)
Has OIDs: no

