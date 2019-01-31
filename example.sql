CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `zft_news` (
  `news_id` mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '新闻序号',
  `cat_id` int(10) NOT NULL COMMENT '新闻类别：1新闻 2公告',
  `news_title` varchar(200) NOT NULL DEFAULT '' COMMENT '新闻标题',
  `content` longtext COMMENT '新闻内容'
  PRIMARY KEY (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
