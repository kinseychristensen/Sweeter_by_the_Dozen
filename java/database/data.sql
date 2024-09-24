BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role, email) VALUES ('kinsey','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'kit.the.avenger@gmail.com');
INSERT INTO users (username,password_hash,role, email) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'kittheavenger@gmail.com');


COMMIT TRANSACTION;
