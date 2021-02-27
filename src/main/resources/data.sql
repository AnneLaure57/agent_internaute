--MERGE INTO profile (id, name, sex, age, average_consumption_time, max_budget, prefered_video_genres, prefered_directors, prefered_actors, prefered_music_genres, prefered_music_artists) 
--VALUES (1, 'claire', 'woman', 39, 20, 30, "", "", "", "", "");

MERGE INTO profile (id, name, sex, age, average_consumption_time, max_budget) 
VALUES (1, 'claire', 'woman', 39, 20, 30);