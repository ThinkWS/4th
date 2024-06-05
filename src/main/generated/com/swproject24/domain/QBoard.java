package com.swproject24.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -294338496L;

    public static final QBoard board = new QBoard("board");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final EnumPath<com.swproject24.constant.BoardTypeEnum> boardType = createEnum("boardType", com.swproject24.constant.BoardTypeEnum.class);

    public final StringPath createDate = createString("createDate");

    public final StringPath deleteDate = createString("deleteDate");

    public final EnumPath<com.swproject24.constant.DeleteFlagEnum> deleteFlag = createEnum("deleteFlag", com.swproject24.constant.DeleteFlagEnum.class);

    public final StringPath field = createString("field");

    public final StringPath title = createString("title");

    public final StringPath updateDate = createString("updateDate");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

