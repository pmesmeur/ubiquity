psql -c "select R.id, R.artist_credit, SUBSTRING(R.name FROM 1 FOR 30), SUBSTRING (R.comment FROM 1 FOR 30) from recording R, artist_credit_name ACN, artist A where R.artist_credit = ACN.artist_credit AND A.id = ACN.artist AND ((A.begin_date_year >= 1965 AND A.begin_date_year <= 1970) OR A.name = 'The Rolling Stones') AND (A.area = 221 or A.area = 222 or A.area = 73);" > Recording-65-70.txt
psql -c "\d+ recording;" > Recording-65-70.dsc

psql -c "select R.id, R.artist_credit, SUBSTRING(R.name FROM 1 FOR 30), SUBSTRING (R.comment FROM 1 FOR 30), R.packaging from release R, artist_credit_name ACN, artist A where R.artist_credit = ACN.artist_credit AND A.id = ACN.artist AND ((A.begin_date_year >= 1965 AND A.begin_date_year <= 1970) OR A.name = 'The Rolling Stones') AND (A.area = 221 or A.area = 222 or A.area = 73);" > Release-65-70.txt
psql -c "\d+ release;" > Release-65-70.dsc

psql -c "select T.id, T.artist_credit, T.recording, T.medium, SUBSTRING(T.name FROM 1 FOR 30) from track T, artist_credit_name ACN, artist A where T.artist_credit = ACN.artist_credit AND A.id = ACN.artist AND ((A.begin_date_year >= 1965 AND A.begin_date_year <= 1970) OR A.name = 'The Rolling Stones') AND (A.area = 221 or A.area = 222 or A.area = 73);" > Track-65-70.txt
psql -c "\d+ track;" > Track-65-70.dsc

psql -c "select id, SUBSTRING (name FROM 1 FOR 30), begin_date_year, end_date_year, ended, type, area, SUBSTRING(comment FROM 1 FOR 30) from artist A where ((begin_date_year >= 1965 AND begin_date_year <= 1970) OR name = 'The Rolling Stones') AND (area = 221 or area = 222 or area = 73);" > Artist-65-70.txt
psql -c "\d+ artist;" > Artist-65-70.dsc

psql -c "select id, SUBSTRING (name FROM 1 FOR 30), type, SUBSTRING (comment FROM 1 FOR 30) from area where id < 1000;" > Area.txt
psql -c "\d+ area;" > Area.dsc

psql -c "select M.id, M.release, M.format, SUBSTRING(M.name FROM 1 FOR 30), M.track_count from medium M, artist_credit_name ACN, artist A, release R where M.release = R.id AND R.artist_credit = ACN.artist_credit AND A.id = ACN.artist AND ((A.begin_date_year >= 1965 AND A.begin_date_year <= 1970) OR A.name = 'The Rolling Stones') AND (A.area = 221 or A.area = 222 or A.area = 73);" > Medium-65-70.txt
psql -c "\d+ medium;" > Medium-65-70.dsc

psql -c "select * from release_packaging;" > Packaging.txt
psql -c "\d+ release_packaging;" > Packaging.dsc

psql -c "select * from artist_type" > Type.txt
psql -c "\d+ artist_type;" > Type.dsc
