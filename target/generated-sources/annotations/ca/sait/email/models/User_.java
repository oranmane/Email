package ca.sait.email.models;

import ca.sait.email.models.Note;
import ca.sait.email.models.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2022-04-20T20:30:51")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Role> role;
    public static volatile SingularAttribute<User, Boolean> active;
    public static volatile ListAttribute<User, Note> noteList;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> resetPasswordUuid;

}