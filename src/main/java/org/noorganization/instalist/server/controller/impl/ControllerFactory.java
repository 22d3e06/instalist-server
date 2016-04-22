/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.noorganization.instalist.server.controller.impl;

import org.noorganization.instalist.server.controller.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * This Factory points to always up-to-date controllers (for decoupling).
 * Created by damihe on 05.02.16.
 */
public class ControllerFactory {

    public static IAuthController getAuthController() {
        return new AuthController();
    }

    public static ICategoryController getCategoryController(EntityManager _manager) {
        return new CategoryController(_manager);
    }

    public static IEntryController getEntryController(EntityManager _manager) {
        return new EntryController(_manager);
    }

    public static IIngredientController getIngredientController(EntityManager _manager) {
        return new IngredientController(_manager);
    }

    public static IGroupController getGroupController(EntityManager _manager) {
        return new GroupController(_manager);
    }

    public static IListController getListController(EntityManager _manager) {
        return new ListController(_manager);
    }

    public static IProductController getProductController(EntityManager _manager) {
        return new ProductController(_manager);
    }

    public static IRecipeController getRecipeController(EntityManager _manager) {
        return new RecipeController(_manager);
    }

    public static ITagController getTagController(EntityManager _manager) {
        return new TagController(_manager);
    }

    public static ITaggedProductController getTaggedProductController(EntityManager _manager) {
        return new TaggedProductController(_manager);
    }

    public static IUnitController getUnitController(EntityManager _manager) {
        return new UnitController(_manager);
    }

    private ControllerFactory(){
    }
}
