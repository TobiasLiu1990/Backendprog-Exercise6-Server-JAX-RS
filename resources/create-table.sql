/*
    In reality this would most likely be a many to many relationship.
    But to keep it simple, without having to add a junction table (kopplingsentitet)

 */

DROP TABLE IF EXISTS Category;
CREATE TABLE Category
(
    category_id INT NOT NULL AUTO_INCREMENT,
    category_name enum('Hardware', 'Games', 'Songs'),
    PRIMARY KEY(category_id)
);

DROP TABLE IF EXISTS Items;
CREATE TABLE Items
(
    item_id INT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(100),
    price INT,
    category_id INT NOT NULL,
    PRIMARY KEY (item_id),
    FOREIGN KEY(category_id) REFERENCES Category(category_id) ON UPDATE CASCADE
);