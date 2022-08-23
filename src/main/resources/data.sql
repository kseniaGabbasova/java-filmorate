MERGE INTO MPA (MPA_ID, MPA_NAME)
 values ( 1, 'G');
MERGE INTO MPA (MPA_ID, MPA_NAME)
    values ( 2, 'PG');
MERGE INTO MPA (MPA_ID, MPA_NAME)
    values ( 3, 'PG-13');
MERGE INTO MPA (MPA_ID, MPA_NAME)
    values ( 4, 'R');
MERGE INTO MPA (MPA_ID, MPA_NAME)
    values ( 5, 'NC-17');

MERGE INTO GENRES(GENRE_ID, GENRE_NAME)
values ( 1, 'Комедия' );
MERGE INTO GENRES(GENRE_ID, GENRE_NAME)
    values ( 2, 'Драма' );
MERGE INTO GENRES(GENRE_ID, GENRE_NAME)
    values ( 3, 'Мультфильм' );
MERGE INTO GENRES(GENRE_ID, GENRE_NAME)
    values ( 4, 'Триллер' );
MERGE INTO GENRES(GENRE_ID, GENRE_NAME)
    values ( 5, 'Документальный' );
MERGE INTO GENRES(GENRE_ID, GENRE_NAME)
    values ( 6, 'Боевик' )