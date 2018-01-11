drop table if exists `microservice_base_employee`;
create table `microservice_base_employee` (
  `id` bigint(20) not null auto_increment comment '自增数字id',
  `uuid` varchar(64) not null comment '无规律字符串id',
  
  `name` varchar(32) not null comment '名称',
  `age` int(11) not null comment '年龄',
  `sex` tinyint(1) not null comment '性别',
  
  `note` longtext default null comment '备注',
  `version` int(11) not null default 1 comment '乐观锁',
  `ds` tinyint(1) not null default false comment '逻辑删除标志',
  `creator` varchar(128) not null comment '创建人',
  `create_time` datetime not null comment '创建时间',
  `updater` varchar(128) default null comment '修改人',
  `update_time` datetime default null comment '修改时间',
  `sys_timer` timestamp not null default current_timestamp on update current_timestamp comment '操作时间',
  primary key (`id`)
) engine=innodb default charset=utf8;
alter table microservice_base_employee add index uuid(uuid);
alter table microservice_base_employee add index creator(creator);
commit;
