package org.metahut.starfish.parser.domain.struct;

import org.metahut.starfish.parser.domain.enums.SfTagCategory;
import org.metahut.starfish.parser.domain.enums.SfTagLife;

import java.util.Date;

/**
 * TagModel
 * which is marking for searching、sorting、comment、comment、limit etc.
 * TODO 如何解耦 并且 与 实际功能挂钩
 */
public class TagModel {

    /**
     * Tag category
     */
    private SfTagCategory sfTagCategory;
    /**
     * Tag Name
     */
    private String tagName;

    /**
     * Tag detail
     */
    private String tagDetail;

    /**
     * tag owner
     */
    private String tagOwner;

    /**
     * tag date
     */
    private Date tagDate;

    /**
     * tag order;
     */
    private Long tagOrder;

    private SfTagLife sfTagLife;

}
