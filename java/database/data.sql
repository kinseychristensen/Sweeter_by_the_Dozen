BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role, display_name) VALUES ('kit.the.avenger@gmail.com','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'kinsey');
INSERT INTO users (username,password_hash,role, display_name) VALUES ('kittheavenger@gmail.com','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'admin');

INSERT INTO recipes (user_id, recipe_title, recipe_description)
	VALUES (1, 'CINNAMON TOAST', 'Make breakfast a snap with this quick and tasty snack.');

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

INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, quantifier, ingredient)
	VALUES (1, 1, 2, 1, 'tablespoons', 'softened', 'butter');
INSERT INTO recipe_ingredients (recipe_id, order_num,  amount_numerator, unit_type, ingredient)
	VALUES (1, 2, 4, 'slices', 'bread');
INSERT INTO recipe_ingredients (recipe_id, order_num,  amount_numerator, amount_denominator, unit_type, quantifier, ingredient)
	VALUES (1, 3, 2, 1, 'tablespoons', 'or so', 'sugar');
INSERT INTO recipe_ingredients (recipe_id, order_num,  amount_numerator, amount_denominator, unit_type, ingredient)
	VALUES (1, 4, 3, 2, 'teaspoons', 'cinnamon');

INSERT INTO tags (tag) VALUES ('breakfast');
INSERT INTO tags (tag) VALUES ('easy');
INSERT INTO tags (tag) VALUES ('kid-friendly');
INSERT INTO tags (tag) VALUES ('lunch');
INSERT INTO tags (tag) VALUES ('dinner');
INSERT INTO tags (tag) VALUES ('snack');
INSERT INTO tags (tag) VALUES ('dessert');
INSERT INTO tags (tag) VALUES ('beginner');
INSERT INTO tags (tag) VALUES ('intermediate');
INSERT INTO tags (tag) VALUES ('advanced');
INSERT INTO tags (tag) VALUES ('expert');
INSERT INTO tags (tag) VALUES ('gluten-free');
INSERT INTO tags (tag) VALUES ('dairy-free');
INSERT INTO tags (tag) VALUES ('nut-free');
INSERT INTO tags (tag) VALUES ('vegan');
INSERT INTO tags (tag) VALUES ('vegetarian');
INSERT INTO tags (tag) VALUES ('quick');
INSERT INTO tags (tag) VALUES ('slow-cooker');
INSERT INTO tags (tag) VALUES ('instant-pot');
INSERT INTO tags (tag) VALUES ('canning');
INSERT INTO tags (tag) VALUES ('preserved');
INSERT INTO tags (tag) VALUES ('Italian');
INSERT INTO tags (tag) VALUES ('Chinese');
INSERT INTO tags (tag) VALUES ('Mexican');
INSERT INTO tags (tag) VALUES ('Spanish');
INSERT INTO tags (tag) VALUES ('Greek');
INSERT INTO tags (tag) VALUES ('Mediterranean');
INSERT INTO tags (tag) VALUES ('French');
INSERT INTO tags (tag) VALUES ('Japanese');
INSERT INTO tags (tag) VALUES ('Korean');
INSERT INTO tags (tag) VALUES ('Thai');
INSERT INTO tags (tag) VALUES ('Indian');
INSERT INTO tags (tag) VALUES ('comfort');
INSERT INTO tags (tag) VALUES ('soul');
INSERT INTO tags (tag) VALUES ('Brazilian');
INSERT INTO tags (tag) VALUES ('Filipino');
INSERT INTO tags (tag) VALUES ('Ethiopian');
INSERT INTO tags (tag) VALUES ('halal');
INSERT INTO tags (tag) VALUES ('kosher');
INSERT INTO tags (tag) VALUES ('appetizer');
INSERT INTO tags (tag) VALUES ('baking');
INSERT INTO tags (tag) VALUES ('beverage');
INSERT INTO tags (tag) VALUES ('pastry');
INSERT INTO tags (tag) VALUES ('barbeque');
INSERT INTO tags (tag) VALUES ('soup');
INSERT INTO tags (tag) VALUES ('salad');
INSERT INTO tags (tag) VALUES ('bread');



INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (1, 1);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (1, 2);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (1, 3);



COMMIT TRANSACTION;
