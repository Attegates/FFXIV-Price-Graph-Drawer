PRAGMA foreign_keys = ON;

DROP TABLE price;
DROP TABLE item;

CREATE TABLE item(
    Name TEXT PRIMARY KEY
);

CREATE TABLE price(
    ItemName TEXT,
    Price INT,
    CheckedDate TEXT,
    FOREIGN KEY(ItemName) REFERENCES item(Name),
    PRIMARY KEY (ItemName, CheckedDate)
);

INSERT INTO item VALUES('Fire Materia IV');

INSERT INTO price VALUES('Fire Materia IV', 22000, '2017-06-28');
INSERT INTO price VALUES('Fire Materia IV', 24000, '2017-06-29');
INSERT INTO price VALUES('Fire Materia IV', 20000, '2017-06-30');
INSERT INTO price VALUES('Fire Materia IV', 19000, (SELECT date('now')));