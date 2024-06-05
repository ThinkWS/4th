package com.swproject24.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudy is a Querydsl query type for Study
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudy extends EntityPathBase<Study> {

    private static final long serialVersionUID = -278470877L;

    public static final QStudy study = new QStudy("study");

    public final StringPath createDate = createString("createDate");

    public final NumberPath<Integer> currentApplicants = createNumber("currentApplicants", Integer.class);

    public final StringPath deleteDate = createString("deleteDate");

    public final EnumPath<com.swproject24.constant.DeleteFlagEnum> deleteFlag = createEnum("deleteFlag", com.swproject24.constant.DeleteFlagEnum.class);

    public final EnumPath<com.swproject24.constant.StudyFeeEnum> feeType = createEnum("feeType", com.swproject24.constant.StudyFeeEnum.class);

    public final StringPath field = createString("field");

    public final NumberPath<Integer> groupSize = createNumber("groupSize", Integer.class);

    public final StringPath location = createString("location");

    public final StringPath studyEndDate = createString("studyEndDate");

    public final NumberPath<Long> studyId = createNumber("studyId", Long.class);

    public final NumberPath<Float> studyRating = createNumber("studyRating", Float.class);

    public final StringPath studyStartDate = createString("studyStartDate");

    public final StringPath title = createString("title");

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    public final StringPath updateDate = createString("updateDate");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QStudy(String variable) {
        super(Study.class, forVariable(variable));
    }

    public QStudy(Path<? extends Study> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudy(PathMetadata metadata) {
        super(Study.class, metadata);
    }

}

