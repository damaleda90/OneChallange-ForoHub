CREATE TABLE user_profile (
    id bigint not null,
    profile bigint not null,
    primary key(id, profile),

    constraint fk_usuario_id foreign key(id) references users(id),
    constraint fk_profile_id foreign key(profile) references profile(id)
);