BEGIN TRANSACTION;

DROP TABLE IF EXISTS pending_recipe_pics, pending_recipes, recipe_to_tags, tags, recipe_ingredients, recipe_steps, saved_recipes,
recipe_pictures, recipes, users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	email text NOT NULL UNIQUE,
	flagged_comments int default 0,
	restricted boolean default false,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE recipes (
    recipe_id SERIAL,
    user_id int,
    recipe_title varchar(100) NOT NULL,
    recipe_description text,
    attribution text,
    CONSTRAINT PK_recipes PRIMARY KEY (recipe_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE recipe_pictures (
    recipe_id int,
    picture_url text,
    alt_text text,
    CONSTRAINT FK_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
    );

CREATE TABLE recipe_comments (
    recipe_id int,
    comment_id SERIAL,
    comment text,
    user_id int,
    reported boolean default false,
    CONSTRAINT PK_recipe_comments PRIMARY KEY (comment_id),
    CONSTRAINT FK_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE saved_recipes (
    recipe_id int,
    user_id int,
    PRIMARY KEY (recipe_id, user_id),
    CONSTRAINT FK_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
    );

CREATE TABLE recipe_steps (
    recipe_id int,
    instructions text,
    step int,
    CONSTRAINT FK_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
    );

CREATE TABLE recipe_ingredients (
    recipe_id int,
    order_num int,
    amount_numerator int default 0,
    amount_denominator int default 1,
    unit_type text,
   quantifier text,
   ingredient text,
    CONSTRAINT FK_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id)
    );

CREATE TABLE tags (
    tag_id SERIAL,
    tag varchar(30),
    CONSTRAINT PK_tags PRIMARY KEY (tag_id)
    );

CREATE TABLE recipe_to_tags (
    recipe_id int,
    tag_id int,
    CONSTRAINT FK_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes(recipe_id),
    CONSTRAINT FK_tag_id FOREIGN KEY (tag_id) REFERENCES tags(tag_id)
    );

CREATE TABLE pending_recipes (
    user_id int,
    pending_recipe_id SERIAL,
    recipe_text text,
    title varchar(100),
    description varchar(200),
    tags text,
    attribution text,
    CONSTRAINT PK_pending_recipes PRIMARY KEY (pending_recipe_id),
    CONSTRAINT FK_user_id FOREIGN KEY (use******r_id) REFERENCES users(user_id)
    );

CREATE TABLE pending_recipe_pics (
    pending_recipe_id int,
    picture_url text,
    CONSTRAINT FK_pending_recipe_id FOREIGN KEY (pending_recipe_id) REFERENCES pending_recipe(pending_recipe_id)
    );


COMMIT TRANSACTION;
