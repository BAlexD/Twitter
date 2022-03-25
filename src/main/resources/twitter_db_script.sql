create table twitter.users
(
    id         serial primary key,
    login      text,
    password   text,
    first_name varchar(32),
    last_name  varchar(32),
    email      varchar(64),
    phone      varchar(32)
);

create table twitter.posts
(
    id      serial primary key,
    user_id bigint,
    content text,
    foreign key (user_id) references twitter.users (id)

);

create table twitter.likes
(
    id      serial primary key,
    post_id bigint,
    user_id bigint,
    foreign key (post_id) references twitter.posts (id),
    foreign key (user_id) references twitter.users (id)
);

create table twitter.comments
(
    id      serial primary key,
    post_id bigint,
    user_id bigint,
    content text,
    foreign key (post_id) references twitter.posts (id),
    foreign key (user_id) references twitter.users (id)
);

create table twitter.subscribes
(
    id            serial primary key,
    profile_id    bigint,
    subscriber_id bigint,
    foreign key (profile_id) references twitter.users (id),
    foreign key (subscriber_id) references twitter.users (id)
);