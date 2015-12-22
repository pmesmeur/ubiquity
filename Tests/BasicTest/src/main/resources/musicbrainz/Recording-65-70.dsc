                                                       Table "musicbrainz.recording"
    Column     |           Type           |                       Modifiers                        | Storage  | Stats target | Description 
---------------+--------------------------+--------------------------------------------------------+----------+--------------+-------------
 id            | integer                  | not null default nextval('recording_id_seq'::regclass) | plain    |              | 
 gid           | uuid                     | not null                                               | plain    |              | 
 name          | character varying        | not null                                               | extended |              | 
 artist_credit | integer                  | not null                                               | plain    |              | 
 length        | integer                  |                                                        | plain    |              | 
 comment       | character varying(255)   | not null default ''::character varying                 | extended |              | 
 edits_pending | integer                  | not null default 0                                     | plain    |              | 
 last_updated  | timestamp with time zone | default now()                                          | plain    |              | 
 video         | boolean                  | not null default false                                 | plain    |              | 
Indexes:
    "recording_pkey" PRIMARY KEY, btree (id)
    "recording_idx_gid" UNIQUE, btree (gid)
    "recording_idx_artist_credit" btree (artist_credit)
    "recording_idx_musicbrainz_collate" btree (musicbrainz_collate(name::text))
    "recording_idx_name" btree (name)
    "recording_idx_txt" gin (to_tsvector('mb_simple'::regconfig, name::text))
Check constraints:
    "recording_edits_pending_check" CHECK (edits_pending >= 0)
    "recording_length_check" CHECK (length IS NULL OR length > 0)
Has OIDs: no

