package ca.sait.email.models;

import ca.sait.email.models.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-04-20T20:30:51")
@StaticMetamodel(Note.class)
public class Note_ { 

    public static volatile SingularAttribute<Note, User> owner;
    public static volatile SingularAttribute<Note, String> contents;
    public static volatile SingularAttribute<Note, Integer> noteId;
    public static volatile SingularAttribute<Note, String> title;

}