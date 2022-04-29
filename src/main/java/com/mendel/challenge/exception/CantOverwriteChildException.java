package com.mendel.challenge.exception;

public class CantOverwriteChildException extends IllegalArgumentException {
    private final Long parentId;
    private final Long childId;

    public CantOverwriteChildException(Long parentId, Long childId) {
        this.parentId = parentId;
        this.childId = childId;
    }

    @Override
    public String getLocalizedMessage() {
        return "Parent can't have multiple childs.";
    }

    @Override
    public String getMessage() {
        return String.format("Parent Transaction with id %s has another child assigned with id %s.", this.parentId, this.childId);
    }
}
