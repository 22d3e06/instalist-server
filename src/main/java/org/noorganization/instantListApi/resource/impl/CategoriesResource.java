package org.noorganization.instantListApi.resource.impl;

import org.noorganization.instantListApi.resource.ICategoriesResource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.util.Date;

/**
 * Created by Lunero on 21.01.2016.
 */
public class CategoriesResource implements ICategoriesResource {

    public GetCategoriesResponse getCategories(Date changedSince) throws Exception {
        StreamingOutput stream = new StreamingOutput() {

            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.write("test");
                writer.flush();
            }
        };
        return GetCategoriesResponse.withJsonOK((StreamingOutput) GetCategoriesResponse.withJsonAccepted(stream));
    }

    public PostCategoriesResponse postCategories(org.noorganization.instantListApi.model.Category entity) throws Exception {
        return null;
    }

    public GetCategoriesByCategoryIdResponse getCategoriesByCategoryId(String categoryId) throws Exception {
        return null;
    }

    public PutCategoriesByCategoryIdResponse putCategoriesByCategoryId(String categoryId, org.noorganization.instantListApi.model.Category entity) throws Exception {
        return null;
    }

    public DeleteCategoriesByCategoryIdResponse deleteCategoriesByCategoryId(String categoryId) throws Exception {
        return null;
    }
}
