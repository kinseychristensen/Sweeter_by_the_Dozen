BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role, email) VALUES ('kinsey','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'kit.the.avenger@gmail.com');
INSERT INTO users (username,password_hash,role, email) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'kittheavenger@gmail.com');

INSERT INTO recipes (user_id, recipe_title, recipe_description)
	VALUES (1, 'Cinnamon Toast', 'Make breakfast a snap with this quick and tasty snack.');

INSERT INTO recipe_pictures (recipe_id, picture_url) VALUES (1, 'TEST');

INSERT INTO recipe_steps (recipe_id, instructions, step)
	VALUES (1, 'Preheat oven to low broil.', 1);
INSERT INTO recipe_steps (recipe_id, instructions, step)
	VALUES (1, 'Butter one side of each piece of bread and place butter-side-up on a cookie sheet.', 2);
INSERT INTO recipe_steps (recipe_id, instructions, step)
	VALUES (1, 'Sprinkle a generous amount of sugar over each slice and a smaller amount of cinnamon.', 3);
INSERT INTO recipe_steps (recipe_id, instructions, step)
	VALUES (1, 'Broil for 1-3 minutes or until the bread is toasty and the sugar melted.', 4);
INSERT INTO recipe_steps (recipe_id, instructions, step)
	VALUES (1, 'For extra fun use cookie cutters to slice the bread in festive shapes before toasting.', 5);

INSERT INTO recipe_ingredients (recipe_id, order_num, amount, unit_type, quantifier, ingredient)
	VALUES (1, 1, 2, 'tablespoons', 'softened', 'butter');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount, unit_type, ingredient)
	VALUES (1, 2, 4, 'slices', 'bread');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount, unit_type, quantifier, ingredient)
	VALUES (1, 3, 2, 'tablespoons', 'or so', 'sugar');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount, unit_type, quantifier, ingredient)
	VALUES (1, 4, 2, 'teaspoons', 'or so', 'cinnamon');

INSERT INTO tags (tag) VALUES ('breakfast');
INSERT INTO tags (tag) VALUES ('easy');
INSERT INTO tags (tag) VALUES ('kid-friendly');

INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (1, 1);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (1, 2);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (1, 3);



COMMIT TRANSACTION;
