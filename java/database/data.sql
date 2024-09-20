BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');
INSERT INTO users (username,password_hash,role) VALUES ('demo','$2a$10$tNjdu/0ybFPPZstRw9XL6uiYlvVNfFqHecApv1zSAircyKyJ0I7oW','ROLE_USER');

INSERT INTO cards_tags (card_id, tag) VALUES (92, 'justin parker');
INSERT INTO cards_tags (card_id, tag) VALUES (92, 'academic fellow');


COMMIT TRANSACTION;
