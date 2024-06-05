    create table contour (
            id varchar(16),
            primary key (id)
    );

    create table job (
            id bigserial,
            path varchar(255) NOT NULL UNIQUE,
            contour_id varchar(16) references contour(id) on delete cascade,
            primary key (id)
    );

    create table test_case (
            id varchar(16),
    primary key (id)
    );

    create table tc_job (
            tc_id varchar(16) references test_case(id) on delete cascade,
            job_id bigint references job(id) on delete cascade,
    primary key (tc_id, job_id)
    );

        create table config (
                id bigserial,
                test_case_id varchar(16) NOT NULL,
                number smallint NOT NULL,
                primary key (id)
        );

        create table config_run (
                id bigserial,
                description varchar(255) default 'all' NOT NULL,
                tc_config_id bigint NOT NULL references config (id) on delete cascade,
                primary key (id)
        );

    create table time_limit (
            id bigserial,
            description varchar(255),
            before time,
            after time,
            config_run_id bigint references config_run (id) on delete cascade,
            job_id bigint references job (id) on delete cascade,
            primary key (id)
    );

    create table use_msisdn (
            id bigserial,
            description varchar(255),
            test_group varchar(255),
            ct_id varchar(255),
            config_run_id bigint references config_run (id) on delete cascade,
            primary key (id)
    );

    create table after_passing_tc (
            id bigserial,
            description varchar(255),
            setup_test_case_id varchar(16) references test_case (id) on delete cascade,
            config_run_id varchar(16) references config_run (id) on delete cascade,
            job_id bigint references job (id) on delete cascade,
            --check(
            --(cast(config_run_id is not null, int) +
            --cast(job_id is not null, int)) = 1
            --),
            primary key (id)
    );

    create table sync_key (
            id varchar(32),
            description varchar(255),
    primary key (id)
    );

    create table sync_with (
            id bigserial,
            sync_key_id varchar(32) NOT NULL UNIQUE references sync_key (id) on delete cascade,
            config_run_id bigint references config_run (id) on delete cascade,
            job_id bigint references job (id) on delete cascade,
            primary key (id)
    );
