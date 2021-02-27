;              
CREATE USER IF NOT EXISTS "SA" SALT '55d99969f365e103' HASH 'aa790ad60b574820e1427708bb38c3127794aef189fd65d18e1e57a6c33deb56' ADMIN;          
CREATE CACHED TABLE "PUBLIC"."PROFILE"(
    "ID" INTEGER NOT NULL,
    "AGE" INTEGER,
    "AVERAGE_CONSUMPTION_TIME" INTEGER,
    "CURRENT_CONSUMPTION_TIME" INTEGER,
    "CURRENT_EXPENSES" INTEGER,
    "MAX_BUDGET" INTEGER,
    "NAME" VARCHAR(255),
    "PREFER_DOWNLOADS_FOR_MUSICS" BOOLEAN NOT NULL,
    "PREFER_DOWNLOADS_FOR_VIDEOS" BOOLEAN NOT NULL,
    "PREFERED_ACTORS" BLOB,
    "PREFERED_DIRECTORS" BLOB,
    "PREFERED_MUSIC_ARTISTS" BLOB,
    "PREFERED_MUSIC_GENRES" BLOB,
    "PREFERED_VIDEO_GENRES" BLOB,
    "SEX" VARCHAR(255)
);   
ALTER TABLE "PUBLIC"."PROFILE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1" PRIMARY KEY("ID");       
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.PROFILE;  
INSERT INTO "PUBLIC"."PROFILE" VALUES
(1, 30, 15, NULL, NULL, 50, 'toto', FALSE, FALSE, X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', 'man'),
(2, 39, 40, NULL, NULL, 30, 'claire', FALSE, FALSE, X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a65787000000002770400000002737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000137371007e00020000001d78', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a65787000000004770400000004737200116a6176612e6c616e672e496e746567657212e2a0a4f781873802000149000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b0200007870000000097371007e0002000000067371007e0002000000137371007e00020000000278', 'woman'),
(3, 18, 10, NULL, NULL, 50, 'ch', FALSE, FALSE, X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', X'aced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a6578700000000077040000000078', 'man');               
CREATE CACHED TABLE "PUBLIC"."PURCHASE"(
    "ID" INTEGER NOT NULL,
    "DATE" TIMESTAMP,
    "ITEM_ID" VARCHAR(255),
    "ITEM_TITLE" VARCHAR(255),
    "RATING" DOUBLE,
    "PROFILE_ID" INTEGER
);          
ALTER TABLE "PUBLIC"."PURCHASE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_9" PRIMARY KEY("ID");      
-- 7 +/- SELECT COUNT(*) FROM PUBLIC.PURCHASE; 
INSERT INTO "PUBLIC"."PURCHASE" VALUES
(1, TIMESTAMP '2021-02-27 19:01:14.472', '1234', 'Le parrain', 4.5, NULL),
(2, TIMESTAMP '2021-02-27 19:24:11.214', '3214', 'Le parrain 2', 4.5, NULL),
(3, TIMESTAMP '2021-02-27 19:27:13.868', '1234', 'Le parrain', 4.5, NULL),
(4, TIMESTAMP '2021-02-27 19:34:46.589', '1234', 'Le parrain', 4.5, NULL),
(5, TIMESTAMP '2021-02-27 20:28:50.174', '1234', 'Le parrain', 4.5, 3),
(6, TIMESTAMP '2021-02-27 20:36:43.809', '3214', 'Le parrain 2', 4.5, 3),
(7, TIMESTAMP '2021-02-27 20:36:48.016', '3234', STRINGDECODE('Les bronz\u00e9s'), 3.7, 3);             
ALTER TABLE "PUBLIC"."PROFILE" ADD CONSTRAINT "PUBLIC"."UK_H0MISXFXI90TXD9E2CF1OWM8J" UNIQUE("NAME");          
ALTER TABLE "PUBLIC"."PURCHASE" ADD CONSTRAINT "PUBLIC"."FK3HJ2YURIES0GSQLV5WM8BWF94" FOREIGN KEY("PROFILE_ID") REFERENCES "PUBLIC"."PROFILE"("ID") NOCHECK;   
