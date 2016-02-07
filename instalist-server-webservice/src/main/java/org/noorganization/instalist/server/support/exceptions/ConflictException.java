package org.noorganization.instalist.server.support.exceptions;

/**
 * This Exception gets thrown if changing data would conflict with existing data. The database
 * will be in a clean state.
 * Created by damihe on 07.02.16.
 */
public class ConflictException extends Exception {
}
