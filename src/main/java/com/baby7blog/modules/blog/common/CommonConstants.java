package com.baby7blog.modules.blog.common;

public interface CommonConstants {

    // 成功标记
    Integer SUCCESS = 0;
    // 失败标记
    Integer FAIL = 1;

    // Redis-分组-博客
    String REDIS_GROUP_BLOG = "blogRedisManager";
    // Redis-分组-标签
    String REDIS_GROUP_LABEL = "labelRedisManager";
    // Redis-分组-友链
    String REDIS_GROUP_LINK = "linkRedisManager";
    // Redis-分组-菜单
    String REDIS_GROUP_MENU = "menuRedisManager";
    // Redis-分组-设置
    String REDIS_GROUP_SETTING = "settingRedisManager";
    // Redis-分组-评论
    String REDIS_GROUP_COMMENT = "commentRedisManager";
    // Redis-分组-管理员
    String REDIS_GROUP_USER = "userRedisManager";

    // 登录用户Token令牌缓存KEY前缀
    String PREFIX_USER_TOKEN  = "prefix_user_token_";

    // 盐
    String SALT = "baby7blog";

    // 无需鉴权路由
    String PATH_USER_COMMENT = "/comment/change/comment";
    String PATH_INFO_ALL = "/*/info/**";
}
