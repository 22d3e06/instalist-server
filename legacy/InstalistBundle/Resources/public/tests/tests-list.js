var connection;

QUnit.module("Test RPC API", function (hooks) {
    var session = null;
    hooks.beforeEach(function (assert) {
        var that = this;
        var done1 = assert.async(1);
        console.log("Set up");
        ab.connect(
            // The WebSocket URI of the WAMP server
            wstarget,
            // The onconnect handler
            sessionEstablished,
            // hanup handler when somehting bad happens
            hangUpHandler,
            // The session options
            {
                'maxRetries': 0,
                'retryDelay': 0
            }
        );
        function sessionEstablished(_session) {
            console.log("Test setup session: ");
            console.log(_session);
            session = _session;
            done1();
        }

        function hangUpHandler(code, reason, detail) {
            // WAMP session closed here ..
            console.log(code + " " + reason);
            console.log(detail);
            done1();
        }
    });

    hooks.afterEach(function (assert) {
    });

    QUnit.test("RPC Call getTaggedProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_tagged_product", {"uuid": "TODO"}).then(function (result) {
            console.log("result getTaggedProduct");
            console.log(result);
            assert.ok(0 === result.code);
            assert.ok(0 === result.taggedProducts.length);
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            console.error("Error in rpc getTaggedProduct!" + error);
            assert.ok(false);
            done();
        });
    });

    QUnit.test("RPC Call getTaggedProductUuidsByTag!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_tagged_product_uuids_by_tag", {"tag": "TODO"}).then(function (result) {
            console.log("result getTaggedProductUuidsByTag");
            console.log(result);
            assert.ok(0 === result.code);
            assert.ok(0 === result.taggedProducts.length);
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getTaggedProductUuidsByTag!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateCategory!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_category", {"uuid": "TODO", "name": "TODO"}).then(function (result) {
            console.log("result updateCategory");
            console.log(result);
            assert.ok(0 === result.code);
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateCategory!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getCategoryUuids!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_category_uuids", {}).then(function (result) {
            console.log("result getCategoryUuids");
            console.log(result);
            assert.ok(0 === result.code);
            assert.ok(0 === result.categories.length);
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getCategoryUuids!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getList!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_list", {"uuid": "TODO"}).then(function (result) {
            console.log("result getList");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getList!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getCategory!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_category", {"uuid": "TODO"}).then(function (result) {
            console.log("result getCategory");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getCategory!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addListEntry!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_list_entry", {
            "uuid": "TODO",
            "shoppingList": "TODO",
            "product": "TODO",
            "amount": 0.6,
            "struck": true,
            "priority": "TODO"
        }).then(function (result) {
            console.log("result addListEntry");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addListEntry!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteTaggedProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_tagged_product", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteTaggedProduct");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteTaggedProduct!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateIngredient!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_ingredient", {
            "uuid": "TODO",
            "product": "TODO",
            "recipe": "TODO",
            "amount": 0.5
        }).then(function (result) {
            console.log("result updateIngredient");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateIngredient!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getRecipe!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_recipe", {"uuid": "TODO"}).then(function (result) {
            console.log("result getRecipe");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getRecipe!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteRecipe!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_recipe", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteRecipe");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteRecipe!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getListEntry!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_list_entry", {"uuid": "TODO"}).then(function (result) {
            console.log("result getListEntry");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getListEntry!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getIngredient!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_ingredient", {"uuid": "TODO"}).then(function (result) {
            console.log("result getIngredient");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getIngredient!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getProductUuids!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_product_uuids", {}).then(function (result) {
            console.log("result getProductUuids");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getProductUuids!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateListEntry!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_list_entry", {
            "uuid": "TODO",
            "shoppingList": "TODO",
            "product": "TODO",
            "amount": "TODO",
            "struck": "TODO",
            "priority": "TODO"
        }).then(function (result) {
            console.log("result updateListEntry");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateListEntry!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addIngredient!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_ingredient", {
            "uuid": "TODO",
            "product": "TODO",
            "recipe": "TODO",
            "amount": "TODO"
        }).then(function (result) {
            console.log("result addIngredient");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addIngredient!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getUnitUuids!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_unit_uuids", {}).then(function (result) {
            console.log("result getUnitUuids");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getUnitUuids!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addTaggedProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_tagged_product", {
            "uuid": "TODO",
            "tag": "TODO",
            "product": "TODO"
        }).then(function (result) {
            console.log("result addTaggedProduct");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addTaggedProduct!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateTag!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_tag", {"uuid": "TODO", "name": "TODO"}).then(function (result) {
            console.log("result updateTag");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateTag!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteUnit!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_unit", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteUnit");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteUnit!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getUnit!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_unit", {"uuid": "TODO"}).then(function (result) {
            console.log("result getUnit");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getUnit!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_product", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteProduct");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteProduct!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_product", {
            "uuid": "TODO",
            "name": "TEST_PRODUCT",
            "unit": "TODO",
            "defaultAmount": 0.5,
            "stepAmount": 0.5
        }).then(function (result) {
            console.log("result updateProduct");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateProduct!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getIngredientUuidsByRecipe!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_ingredient_uuids_by_recipe", {"recipe": "TODO"}).then(function (result) {
            console.log("result getIngredientUuidsByRecipe");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getIngredientUuidsByRecipe!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteCategory!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_category", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteCategory");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteCategory!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateTaggedProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_tagged_product", {
            "uuid": "TODO",
            "tag": "TODO",
            "product": "TODO"
        }).then(function (result) {
            console.log("result updateTaggedProduct");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateTaggedProduct!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteListEntry!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_list_entry", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteListEntry");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteListEntry!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addRecipe!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_recipe", {"uuid": "TODO", "name": "TEST_RECIPE"}).then(function (result) {
            console.log("result addRecipe");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addRecipe!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getListEntryUuids!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_list_entry_uuids", {}).then(function (result) {
            console.log("result getListEntryUuids");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getListEntryUuids!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getListUuids!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_list_uuids", {}).then(function (result) {
            console.log("result getListUuids");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getListUuids!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getTag!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_tag", {"uuid": "TODO"}).then(function (result) {
            console.log("result getTag");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getTag!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteIngredient!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_ingredient", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteIngredient");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteIngredient!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getTagUuids!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_tag_uuids", {}).then(function (result) {
            console.log("result getTagUuids");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getTagUuids!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addCategory!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_category", {"uuid": "TODO", "name": "TEST_CATEGORY"}).then(function (result) {
            console.log("result addCategory");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addCategory!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getRecipeUuids!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_recipe_uuids", {}).then(function (result) {
            console.log("result getRecipeUuids");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getRecipeUuids!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getTaggedProductUuidsByProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_tagged_product_uuids_by_product", {"product": "TODO"}).then(function (result) {
            console.log("result getTaggedProductUuidsByProduct");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getTaggedProductUuidsByProduct!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getTaggedProductUuids!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_tagged_product_uuids", {}).then(function (result) {
            console.log("result getTaggedProductUuids");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getTaggedProductUuids!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateUnit!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_unit", {"uuid": "TODO", "name": "TEST_UNIT"}).then(function (result) {
            console.log("result updateUnit");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateUnit!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addUnit!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_unit", {"uuid": "TODO", "name": "TEST_UNIT"}).then(function (result) {
            console.log("result addUnit");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addUnit!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteList!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_list", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteList");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteList!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateList!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_list", {
            "uuid": "TODO",
            "name": "TEST_LIST",
            "category": "TODO"
        }).then(function (result) {
            console.log("result updateList");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateList!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addList!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_list", {
            "uuid": "TODO",
            "name": "TEST_LIST",
            "category": "TODO"
        }).then(function (result) {
            console.log("result addList");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addList!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addTag!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_tag", {"uuid": "TODO", "name": "TEST_TAG"}).then(function (result) {
            console.log("result addTag");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addTag!" + error);
            done();
        });
    });

    QUnit.test("RPC Call getProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/get_product", {"uuid": "TODO"}).then(function (result) {
            console.log("result getProduct");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc getProduct!" + error);
            done();
        });
    });

    QUnit.test("RPC Call addProduct!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/add_product", {
            "uuid": "TODO",
            "name": "TEST_PRODUCT",
            "unit": "TODO",
            "defaultAmount": 0.5,
            "stepAmount": 1.0
        }).then(function (result) {
            console.log("result addProduct");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc addProduct!" + error);
            done();
        });
    });

    QUnit.test("RPC Call deleteTag!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/delete_tag", {"uuid": "TODO"}).then(function (result) {
            console.log("result deleteTag");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc deleteTag!" + error);
            done();
        });
    });

    QUnit.test("RPC Call updateRecipe!", function (assert) {
        var done = assert.async(1);
        // do something here that requires open connection... 
        // WAMP session established here .. 
        session.call("instalist/update_recipe", {"uuid": "TODO", "name": "TEST_RECIPE"}).then(function (result) {
            console.log("result updateRecipe");
            console.log(result);
            assert.ok(0 === result.code);;
            done();
            // do stuff with the result
        }, function (error) {
            // handle the error
            assert.ok(false);
            console.error("Error in rpc updateRecipe!" + error);
            done();
        });
    });

});

