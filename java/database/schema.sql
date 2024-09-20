BEGIN TRANSACTION;


CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE decks (
    deck_id SERIAL,
    user_id int,
    deck_title varchar(100) NOT NULL,
    deck_description text,
    cover_img text,
    pending_approval boolean default false,
    is_approved boolean default false,
    admin_id int,
    CONSTRAINT PK_decks PRIMARY KEY (deck_id),
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
    );




COMMIT TRANSACTION;
