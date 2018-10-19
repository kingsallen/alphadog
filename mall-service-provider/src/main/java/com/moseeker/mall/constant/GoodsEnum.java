package com.moseeker.mall.constant;

/**
 * 商品状态
 *
 * @author cjm
 * @date 2018-10-14 10:58
 **/
public enum GoodsEnum {
    /**
     * 未上架
     */
    DOWNSHELF(1),
    /**
     * 上架中
     */
    UPSHELF(2),
    /**
     * 获取商品列表时表示获取全部商品
     */
    ALL(9)
    ;


    private int state;

    GoodsEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
