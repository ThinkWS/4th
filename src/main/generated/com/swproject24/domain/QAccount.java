package com.swproject24.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -620729081L;

    public static final QAccount account = new QAccount("account");

    public final StringPath bio = createString("bio");

    public final StringPath email = createString("email");

    public final StringPath emailCheckToken = createString("emailCheckToken");

    public final DateTimePath<java.time.LocalDateTime> emailCheckTokenGeneratedAt = createDateTime("emailCheckTokenGeneratedAt", java.time.LocalDateTime.class);

    public final BooleanPath emailVerified = createBoolean("emailVerified");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> joinedAt = createDateTime("joinedAt", java.time.LocalDateTime.class);

    public final StringPath location = createString("location");

    public final StringPath nickname = createString("nickname");

    public final StringPath occupation = createString("occupation");

    public final StringPath password = createString("password");

    public final StringPath profileImage = createString("profileImage");

    public final BooleanPath studyCreatedByEmail = createBoolean("studyCreatedByEmail");

    public final BooleanPath studyCreatedByWeb = createBoolean("studyCreatedByWeb");

    public final BooleanPath studyEnrollmentResultByEmail = createBoolean("studyEnrollmentResultByEmail");

    public final BooleanPath studyEnrollmentResultByWeb = createBoolean("studyEnrollmentResultByWeb");

    public final BooleanPath studyUpdatedByEmail = createBoolean("studyUpdatedByEmail");

    public final BooleanPath studyUpdatedByWeb = createBoolean("studyUpdatedByWeb");

    public final StringPath url = createString("url");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

