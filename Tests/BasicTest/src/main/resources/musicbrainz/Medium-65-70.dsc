                                                       Table "musicbrainz.medium"
    Column     |           Type           |                      Modifiers                      | Storage  | Stats target | Description 
---------------+--------------------------+-----------------------------------------------------+----------+--------------+-------------
 id            | integer                  | not null default nextval('medium_id_seq'::regclass) | plain    |              | 
 release       | integer                  | not null                                            | plain    |              | 
 position      | integer                  | not null                                            | plain    |              | 
 format        | integer                  |                                                     | plain    |              | 
 name          | character varying        | not null default ''::character varying              | extended |              | 
 edits_pending | integer                  | not null default 0                                  | plain    |              | 
 last_updated  | timestamp with time zone | default now()                                       | plain    |              | 
 track_count   | integer                  | not null default 0                                  | plain    |              | 
Indexes:
    "medium_pkey" PRIMARY KEY, btree (id)
    "medium_idx_release_position" btree (release, "position")
    "medium_idx_track_count" btree (track_count)
Check constraints:
    "medium_edits_pending_check" CHECK (edits_pending >= 0)
Has OIDs: no

