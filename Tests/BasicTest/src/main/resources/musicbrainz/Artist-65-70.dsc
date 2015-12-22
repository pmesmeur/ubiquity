                                                        Table "musicbrainz.artist"
      Column      |           Type           |                      Modifiers                      | Storage  | Stats target | Description 
------------------+--------------------------+-----------------------------------------------------+----------+--------------+-------------
 id               | integer                  | not null default nextval('artist_id_seq'::regclass) | plain    |              | 
 gid              | uuid                     | not null                                            | plain    |              | 
 name             | character varying        | not null                                            | extended |              | 
 sort_name        | character varying        | not null                                            | extended |              | 
 begin_date_year  | smallint                 |                                                     | plain    |              | 
 begin_date_month | smallint                 |                                                     | plain    |              | 
 begin_date_day   | smallint                 |                                                     | plain    |              | 
 end_date_year    | smallint                 |                                                     | plain    |              | 
 end_date_month   | smallint                 |                                                     | plain    |              | 
 end_date_day     | smallint                 |                                                     | plain    |              | 
 type             | integer                  |                                                     | plain    |              | 
 area             | integer                  |                                                     | plain    |              | 
 gender           | integer                  |                                                     | plain    |              | 
 comment          | character varying(255)   | not null default ''::character varying              | extended |              | 
 edits_pending    | integer                  | not null default 0                                  | plain    |              | 
 last_updated     | timestamp with time zone | default now()                                       | plain    |              | 
 ended            | boolean                  | not null default false                              | plain    |              | 
 begin_area       | integer                  |                                                     | plain    |              | 
 end_area         | integer                  |                                                     | plain    |              | 
Indexes:
    "artist_pkey" PRIMARY KEY, btree (id)
    "artist_idx_gid" UNIQUE, btree (gid)
    "artist_idx_null_comment" UNIQUE, btree (name) WHERE comment IS NULL
    "artist_idx_uniq_name_comment" UNIQUE, btree (name, comment) WHERE comment IS NOT NULL
    "artist_idx_area" btree (area)
    "artist_idx_begin_area" btree (begin_area)
    "artist_idx_end_area" btree (end_area)
    "artist_idx_lower_name" btree (lower(name::text))
    "artist_idx_musicbrainz_collate" btree (musicbrainz_collate(name::text))
    "artist_idx_name" btree (name)
    "artist_idx_sort_name" btree (sort_name)
    "artist_idx_txt" gin (to_tsvector('mb_simple'::regconfig, name::text))
    "artist_idx_txt_sort" gin (to_tsvector('mb_simple'::regconfig, sort_name::text))
Check constraints:
    "artist_edits_pending_check" CHECK (edits_pending >= 0)
    "artist_ended_check" CHECK ((end_date_year IS NOT NULL OR end_date_month IS NOT NULL OR end_date_day IS NOT NULL) AND ended = true OR end_date_year IS NULL AND end_date_month IS NULL AND end_date_day IS NULL)
Has OIDs: no

