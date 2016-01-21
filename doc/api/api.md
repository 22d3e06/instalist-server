# InstantList API API documentation version v1
http://instantlist.noorganization.org/v1

---

## /user

### /user/register

* **post**: The action to register an user.

### /user/login

* **post**: The action to login an user.

### /user/resetPassword

* **post**: The action to reset a password of a user.

## /categories
Collection of available categories.

### /categories

* **get** *(secured)*: Get a list of categories.

* **post** *(secured)*: Add a new category.

### /categories/{categoryId}
Entity representing a category.

* **get** *(secured)*: Returns the category.

* **put** *(secured)*: Updates the category.

* **delete** *(secured)*: Deletes the category.

## /products
Collection of available products.

### /products

* **get** *(secured)*: Get a list of products.

* **post** *(secured)*: Add a new product.

### /products/{productId}
Entity representing a product.

* **get** *(secured)*: Returns the product.

* **put** *(secured)*: Updates the product.

* **delete** *(secured)*: Deletes the product.

## /lists
Collection of available lists.

### /lists

* **get** *(secured)*: Get a list of lists.

* **post** *(secured)*: Add a new list.

### /lists/{listId}
Entity representing a list.

* **get**: Returns the list.

* **put**: Updates the list.

* **delete**: Deletes the list.

## /recipes
Collection of available recipes.

### /recipes

* **get** *(secured)*: Get a list of recipes.

* **post** *(secured)*: Add a new recipe.

### /recipes/{recipeId}
Entity representing a recipe.

* **get**: Returns the recipe.

* **put**: Updates the recipe.

* **delete**: Deletes the recipe.

### /recipes/ingredients
Collection of available ingredients.

* **get** *(secured)*: Get a list of ingredients.

* **post** *(secured)*: Add a new ingredient.

## /taggedProducts
Collection of available taggedProducts.

### /taggedProducts

* **get** *(secured)*: Get a list of taggedProducts.

* **post** *(secured)*: Add a new taggedProduct.

### /taggedProducts/{taggedProductId}
Entity representing a taggedProduct.

* **get** *(secured)*: Returns the taggedProduct.

* **put** *(secured)*: Updates the taggedProduct.

* **delete** *(secured)*: Deletes the taggedProduct.

## /tags
Collection of available tags.

### /tags

* **get** *(secured)*: Get a list of tags.

* **post** *(secured)*: Add a new tag.

### /tags/{tagId}
Entity representing a tag.

* **get**: Returns the tag.

* **put**: Updates the tag.

* **delete**: Deletes the tag.

## /listEntries
Collection of available listEntries.

### /listEntries

* **get** *(secured)*: Get a list of listEntries.

* **post** *(secured)*: Add a new listEntry.

### /listEntries/{listEntryId}
Entity representing a listEntry.

* **get** *(secured)*: Returns the listEntry.

* **put** *(secured)*: Updates the listEntry.

* **delete** *(secured)*: Deletes the listEntry.

## /units
Collection of available units.

### /units

* **get** *(secured)*: Get a list of units.

* **post** *(secured)*: Add a new unit.

### /units/{unitId}
Entity representing a unit.

* **get** *(secured)*: Returns the unit.

* **put** *(secured)*: Updates the unit.

* **delete** *(secured)*: Deletes the unit.

## /ingredients
Collection of available ingredients.

### /ingredients

* **get** *(secured)*: Get a list of ingredients.

* **post** *(secured)*: Add a new ingredient.

### /ingredients/{ingredientId}
Entity representing a ingredient.

* **get** *(secured)*: Returns the ingredient.

* **put** *(secured)*: Updates the ingredient.

* **delete** *(secured)*: Deletes the ingredient.

