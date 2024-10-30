BEGIN TRANSACTION;

INSERT INTO avatars (avatar_url) VALUES ('https://photos.google.com/share/AF1QipPVRZl-C8slrEEIAkXpwFGq7QHjVthSo1H_B1Rrd5L1Pkh381G60gWqd0r8ZDmrwA/photo/AF1QipOO9a9kVQpYSzoZL-ffd-B9NpMM2DpBPuKwXKpx?key=V212clhEeC05X2QyZ0hxWktKUHZJbFM0alVfRl93'),
('https://photos.app.goo.gl/hyW7ZV8jUJrmyPANA'),
('https://photos.app.goo.gl/rhsVbGUVbfY2U7Jf7'),
('https://photos.app.goo.gl/dHb4N9qf1tV9Ktg36'),
('https://photos.app.goo.gl/j8ZmLSozmmurUeQv5'),
('https://photos.app.goo.gl/94WvJAcGoeBDduuz6'),
('https://photos.app.goo.gl/C2P16qnErKmc7fXaA'),
('https://photos.app.goo.gl/WaYNR6mD6EDwtnjUA'),
('https://photos.app.goo.gl/hiLTvU8MK9M68CRj9'),
('https://photos.app.goo.gl/pBvHpN84ypFqrzPA7'),
('https://photos.app.goo.gl/ZqGP7K9mDzUppFPT9'),
('https://photos.app.goo.gl/1g9Bp3srTNjHPDN76'),
('https://photos.app.goo.gl/HjWsirE4cFNzuYVZA'),
('https://photos.app.goo.gl/1fxABwXg5YuPeTpN7'),
('https://photos.app.goo.gl/UvepXR5ajGi7LHQGA'),
('https://photos.app.goo.gl/b3rDiJEnrj1qQN729');

INSERT INTO users (username,password_hash,role, display_name, avatar_id) VALUES ('kit.the.avenger@gmail.com','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'kinsey', 1);
INSERT INTO users (username,password_hash,role, display_name, avatar_id) VALUES ('kittheavenger@gmail.com','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'admin', 1);


INSERT INTO tags (tag) VALUES ('breakfast'), ('easy'), ('kid-friendly'), ('lunch'), ('dinner'), ('snack'), ('dessert'), ('beginner'),
('intermediate'), ('advanced'), ('expert'), ('gluten-free'), ('dairy-free'), ('nut-free'), ('vegan'), ('vegetarian'), ('quick'),
('slow-cooker'), ('instant-pot'), ('canning'), ('preserved'), ('Italian'), ('Chinese'), ('Mexican'), ('Spanish'), ('Greek'),
('Mediterranean'), ('French'), ('Japanese'), ('Korean'), ('Thai'), ('Indian'), ('comfort'), ('soul'), ('Brazilian'), ('Filipino'),
('Ethiopian'), ('halal'), ('kosher'), ('appetizer'), ('baking'), ('beverage'), ('pastry'), ('barbeque'), ('soup'), ('salad'), ('bread'),
('budget meals');



INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'CINNAMON TOAST', 'Make breakfast a snap with this quick and tasty snack.');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (1, 'Preheat oven to low broil.', 1), (1, 'Butter one side of each piece of bread and place butter-side-up on a cookie sheet.', 2), (1, 'Sprinkle a generous amount of sugar over each slice and a smaller amount of cinnamon.', 3), (1, 'Broil for 1-3 minutes or until the bread is toasty and the sugar melted.', 4), (1, 'For extra fun use cookie cutters to slice the bread in festive shapes before toasting.', 5);
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, quantifier, ingredient) VALUES (1, 1, 2, 1, 'tablespoons', 'softened', 'butter'), (1, 3, 2, 1, 'tablespoons', 'or so', 'sugar');
INSERT INTO recipe_ingredients (recipe_id, order_num,  amount_numerator, amount_denominator, unit_type, ingredient) VALUES (1, 4, 3, 2, 'teaspoons', 'cinnamon'), (1, 2, 4, 1,'slices', 'bread');
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (1, 1), (1, 2), (1, 3);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'PANCAKES', 'Easy, fluffy, and delicious!');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient)  VALUES (2, 1, 1, 1, 'cup', 'flour'), (2, 2, 2, 1, 'tablespoon', 'sugar'), (2, 3, 2, 1, 'teaspoon', 'baking powder'), (2, 4, 3, 4, 'teaspoon', 'salt'), (2, 6, 1, 1, 'cup', 'milk'), (2, 7, 2, 1, 'tablespoon', 'vegetable oil');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, quantifier, ingredient) VALUES (2, 5, 1, 1, 'beaten', 'egg');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (2, 'Mix egg, milk, and oil.  Add dry ingredients and stir mixture just until blended, but still slightly lumpy.', 1), (2, 'Pour about one fourth cup batter onto a hot, lightly greased griddle or heavy skillet.  Cook until the surface is bubbly the edges are slightly dry, and then flip. Makes 8 - 10 pancakes.', 2);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (2, 1), (2, 2), (2, 3), (2, 8), (2, 16);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'GERMAN PANCAKES', 'Also called Dutch Babies, these are a great Sunday breakfast!');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES (3, 1, 1, 3, 'cup', 'butter'), (3, 3, 1, 1, 'cup', 'flour'), (3, 4, 1, 1, 'cup', 'milk');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, ingredient) VALUES (3, 2, 4, 1, 'egg');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (3, 'Heat oven to 425*. Melt butter in pan in oven as it warms.', 1), (3, 'Beat eggs then add milk and finally flour.  Pour over the melted butter.  Cook for 20 - 25 minutes.', 2), (3, 'Serve with syrup, jam, applesauce or a sprinkle of powdered sugar.', 3);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (3, 1), (3, 2), (3, 8), (3, 16);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'BELGIAN WAFFLES', 'Top with fresh strawberries and whipped cream!');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES (4, 1, 1, 1, 'cup', 'flour'), (4, 2, 1, 1, 'cup', 'wheat flour'),  (4, 3, 1, 2, 'teaspoon', 'baking soda'),  (4, 4, 1, 1, 'teaspoon', 'baking powder'), (4, 5, 1, 1, 'teaspoon', 'salt'), (4, 6, 3, 1, 'tablespoon', 'sugar');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, quantifier, ingredient) VALUES (4, 7, 3, 1, 'beaten', 'eggs');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient, quantifier) VALUES (4, 8, 1, 4, 'cup', 'butter', 'melted'), (4, 9, 1, 1, 'pint', 'buttermilk', 'room temperature');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (4, 'Roughly mix all ingredients.  Preheat waffle iron..', 1), (4, 'Spray cooking oil on the waffle iron plates.  Add a scoop of batter and cook until golden.', 2);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (4, 1), (4, 2), (4, 3), (4, 8), (4, 16);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'FRENCH TOAST', 'A delicious and cost effective breakfast option!');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, quantifier, ingredient) VALUES (5, 1, 0, 1, 'as needed', 'for griddle', 'butter');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES (5, 3, 1, 1, 'pinch', 'salt'), (5, 4, 1, 2, 'tablespoon', 'sugar'), (5, 5, 3, 4, 'cup', 'milk'), (5, 6, 1, 2, 'tablespoon',  'ground cinnamon'), (5, 7, 1, 1, 'loaf', 'bread');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, ingredient) VALUES (5, 2, 9, 1, 'eggs');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (5, 'Beat eggs well, then beat them some more.  Add salt and milk. Beat. Add cinnamon while beating and then beat fast for a minute or so to get the cinnamon stirred in.', 1), (5, 'Dip bread and cook on buttered griddle.  Flip and cook the second side until golden brown.  Butter griddle between each batch.  Enjoy!', 2);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (5, 1), (5, 2), (5, 3), (5, 8), (5, 16), (5, 48);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'BISCUITS', 'Soft and fluffy biscuits.  Taste great with jam.');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES (6, 1, 2, 1, 'cup', 'flour'), (6, 2, 1, 1, 'tablespoon', 'baking powder'), (6, 3, 1, 4, 'teaspoon', 'salt'), (6, 4, 2, 1, 'teaspoon', 'sugar'), (6, 5, 1, 2, 'teaspoon', 'cream of tartar'),  (6, 6, 1, 2, 'cup', 'butter or shortening'),  (6, 7, 2, 3, 'cup', 'milk');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (6, 'In a bowl sift together dry ingredients.  Cut in butter and blend with your hands until it is coarse crumbs.  Make a well in the center of the ingredients and add the milk all at once.  Mix to combine.', 1), (6, 'On a lightly floured surface, knead the dough gently and then roll it out to a half inch thickness.  Cut out circles with a cup or jar mouth.', 2), (6, 'Bake at 450 * for 10 - 12 minutes or until tops are lightly golden.', 3);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (6, 1), (6, 5), (6, 16), (6, 17), (6, 33), (6, 34), (6, 41), (6, 47);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'CREPES', 'A luxurious brunch option. Try with a variety of toppings.');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES (7, 1, 2, 1, 'cup', 'flour'), (7, 3, 1, 1, 'cup', 'butter'), (7, 4, 1, 2, 'cup', 'sugar'), (7, 5, 2, 1, 'cup', 'milk'), (7, 6, 1, 2, 'teaspoon', 'salt'), (7, 9, 1, 1, 'cup', 'powdered sugar'), (7, 10, 12, 1, 'ounce', 'salt');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, ingredient) VALUES (7, 2, 2, 1, 'eggs');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient, quantifier) VALUES (7, 7, 8, 1, 'ounce', 'cream cheese', 'softened');
INSERT INTO recipe_ingredients (recipe_id, order_num, ingredient) VALUES (7, 11, 'assorted fruits and toppings such as nutella, or whipped cream');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (7, 'Mix first five ingredients well with an electric mixer or blender.  Heat a small skillet or crepe pan.  Spray lightly with cooking spray.', 1), (7, 'Pour about one fourth cup of batter into a skillet and rotate pan to thin the batter out and fully cover the bottom of the pan.  Cook until the edges are dry and the bottom golden.  Flip and cook the other side before quickly removing from heat.  Apply more cooking spray every third or four crepe.', 2), (7, 'Blend the cream cheese, powdered sugar, and sour cream in a food processor or blender.  Place the cream into the center of a crepe, roll, and then top with fruit and whipped cream.', 3);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (7, 1), (7, 9), (7, 28), (7, 43);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'BANANA NUT BREAD', 'Lightly sweet and nutty, this is a recipe from my grandmother.');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES (8, 1, 1, 2, 'cup', 'shortening'), (8, 2, 1, 1, 'cup', 'brown sugar'), (8, 6, 2, 1, 'cup', 'flour'), (8, 7, 1, 1, 'teaspoon', 'baking soda'), (8, 8, 1, 4, 'teaspoon', 'salt');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, ingredient) VALUES (8, 3, 2, 1, 'eggs');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, quantifier, ingredient) VALUES (8, 4, 2, 1, 'mashed', 'bananas');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient, quantifier) VALUES (8, 5, 1, 2, 'cup', 'walnuts', 'coarsely chopped');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (8, 'Cream shortening and add brown sugar gradually. Add eggs one at a time. Add bananas.', 1), (8, 'Now put in the dry ingredients and mix thoroughly. Fold in the  nuts. Spray a loaf pan, and pour in the batter.', 2), (8, 'Bake for 45 minutes at 350*.', 3);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (8, 2), (8, 3), (8, 6), (8, 8), (8, 33), (8, 41), (8, 47);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'PEAR BREAD', 'Banana bread has a new sweeter cousin.');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, quantifier, ingredient) VALUES (9, 1, 3, 1, '15 oz can', 'drained and mashed', 'pears');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES  (9, 2, 1, 1, 'cup', 'sugar'), (9, 3, 1, 4, 'cup', 'unsweetened applesauce'), (9, 4, 1, 4, 'cup', 'vegetable oil'), (9, 6, 1, 1, 'cup', 'butter'), (9, 7, 13, 4, 'cup', 'flour'), (9, 8, 3, 1, 'teaspoon', 'cinnamon'), (9, 9, 1, 1, 'teaspoon', 'baking soda'), (9, 10, 1, 1, 'teaspoon', 'baking powder'), (9, 10, 1, 1, 'teaspoon', 'cloves'), (9, 10, 1, 2, 'teaspoon', 'salt');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, ingredient) VALUES (9, 5, 3, 1, 'eggs');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (9, 'In a large mixing bowl, combine the first five ingredients.  Sift the dry ingredients and slowly add.', 1), (9, 'Pour into greased loaf pans and bake at 350* for 50 - 60 minutes.', 2);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (9, 2), (9, 3), (9, 6), (9, 14), (9, 8), (9, 1), (9, 41), (9, 47);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'GROOVY', 'Created as a sweet surprise by a dairy farmer for his kids.');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, quantifier, ingredient) VALUES (10, 1, 12, 1, 'ounces', 'undiluted', 'frozen orange juice concentrate');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES (10, 2, 1, 2, 'gallon', 'ice cream mix'), (10, 3, 1, 1, 'quart', 'egg substitute'), (10, 4, 3, 2, 'cup', 'milk');
INSERT INTO recipe_ingredients (recipe_id, order_num, ingredient) VALUES (10, 5, 'corn flakes');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (10, 'Add first four ingredients to a blender and mix well.  Pour over a small bowl of cornflakes for each person and serve.', 1);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (10, 1), (10, 2), (10, 3), (10, 7), (10, 8);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'ZUCCHINI BREAD', 'Moist, delicious, and a favorite way to use up the plentiful squash.');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES (11, 1, 2, 1, 'cup', 'flour'), (11, 2, 2, 1, 'teaspoon', 'cinnamon'), (11, 3, 2, 1, 'teaspoon', 'baking soda'), (11, 4, 1, 1, 'teaspoon', 'salt'), (11, 5, 1, 2, 'teaspoon', 'baking powder'), (11, 7, 2, 1, 'cup', 'sugar'), (11, 8, 3, 4, 'cup', 'oil'), (11, 11, 1, 2, 'cup', 'raisins');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator,  ingredient) VALUES (11, 6, 3, 1, 'eggs');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient, quantifier) VALUES (11, 9, 2, 1, 'cup', 'zucchini', 'grated'), (11, 10, 3, 2, 'cup', 'walnuts', 'chopped');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (11, 'Preheat oven to 350*.  Sift together flour, cinnamon, baking soda, salt, and baking powder.  In a separate bowl beat eggs.  Add the sugar and beat until thickened.  Add oil and beat well.', 1), (11, 'Mix in zucchini, nuts, and raisins to the wet ingredients.  Then add the dry ingredients and mix very well.', 2), (11, 'Pour into well-greased and floured bread pans.  Bake for 50 minutes or until done.', 3);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (11, 2), (11, 6), (11, 9), (11, 33), (11, 41), (11, 47);

INSERT INTO recipes (user_id, recipe_title, recipe_description) VALUES (1, 'BLUEBERRY MUFFINS', 'The cinnamon crumb topping makes these muffins into something special.');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient) VALUES  (12, 3, 1, 2, 'teaspoon', 'salt'), (12, 4, 2, 1, 'teaspoon', 'baking powder'), (12, 5, 1, 3, 'cup', 'vegetable oil'), (12, 7, 1, 3, 'cup', 'milk'), (12, 10, 3, 2, 'teaspoon', 'cinnamon');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, unit_type, ingredient, quantifier) VALUES (12, 1, 2, 1, 'cup', 'flour', 'divided'), (12, 2, 5, 4, 'cup', 'sugar', 'divided'), (12, 8, 1, 1, 'cup', 'blueberries', 'fresh'), (12, 9, 1, 4, 'cup', 'butter', 'cubed');
INSERT INTO recipe_ingredients (recipe_id, order_num, amount_numerator, amount_denominator, ingredient) VALUES (12, 6, 1, 1, 'egg');
INSERT INTO recipe_steps (recipe_id, instructions, step) VALUES (12, 'Preheat oven to 400*.  Fill cupcake pan with muffin liners.', 1),  (12, 'Combine 1 ½ cups of flour, ¾ cup of sugar, salt, and baking powder.  Place vegetable oil in a 1 cup measuring cup; add the egg and enough milk to fill the cup.  Mix this into the flour mixture. Fold in the blueberries.', 2),  (12, 'In a fresh bowl, combine the remaining sugar, flour, butter, and cinnamon.  Mix with a fork until combined into crumbs.', 3),  (12, 'Fill the cupcake liners right to the top with the batter and then sprinkle with the crumb topping.  Bake for 20-25 minutes.', 4);
INSERT INTO recipe_to_tags (recipe_id, tag_id) VALUES (12, 1), (12, 9), (12, 41), (12, 47), (12, 43);






INSERT INTO recipe_pictures (recipe_id, picture_url) VALUES (1, 'TEST'), (1, 'TEST1'), (2, 'TEST'), (3, 'TEST'), (4, 'TEST'), (4, 'TEST1'), (5, 'TEST'), (10, 'TEST');




COMMIT TRANSACTION;
