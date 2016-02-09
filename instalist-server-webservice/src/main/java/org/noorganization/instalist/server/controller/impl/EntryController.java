package org.noorganization.instalist.server.controller.impl;

import javassist.NotFoundException;
import org.noorganization.instalist.server.controller.IEntryController;
import org.noorganization.instalist.server.support.exceptions.ConflictException;
import org.noorganization.instalist.server.support.exceptions.GoneException;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.UUID;

public class EntryController implements IEntryController {

    EntryController(EntityManager _manager) {

    }

    public void add(int _groupId, UUID _listUUID, String _name, UUID _category, Date _lastChanged)
            throws ConflictException {

    }

    public void update(int _groupId, UUID _listUUID, String _name, UUID _category,
                       Date _lastChanged)
            throws ConflictException, GoneException, NotFoundException {

    }

    public void delete(int _groupId, UUID _listUUID) throws GoneException, NotFoundException {

    }
}
