insert into contour(id)
values ('v1'), ('v3'), ('v98'), ('pseudo_prod');

insert into job(path, contour_id)
values ('job/v3_Microcredit_Comission', 'v3'),
       ('job/v1_Microcredit_Comission', 'v1'),
       ('job/Smoke_Tests/job/v3_Smoke_test', 'v3'),
       ('job/v1_B2B_Credit_Control', 'v1'),
       ('job/PROD/job/soap_functional_tests/', 'pseudo_prod')
       ;

insert into test_case(id)
values ('C1_1_TC1'), ('C1_1_TC2'), ('C1_1_TC3'), ('C1_1_TC4'),
        ('C1_2_TC1'), ('C1_2_TC2'), ('C1_2_TC3'), ('C1_2_TC4'),
        ('C2_1_TC1'), ('C2_1_TC2'), ('C2_1_TC3'), ('C2_1_TC4'),
        ('C9_11_TC1'), ('C9_11_TC2'), ('C9_11_TC3'), ('C9_11_TC4');

insert into tc_job(tc_id, job_id)
values ('C1_1_TC1', 1),   ('C1_1_TC2', 1), ('C1_1_TC3', 1),   ('C1_1_TC4', 1),
       ('C1_1_TC1', 2),   ('C1_1_TC2', 2), ('C1_1_TC3', 2),   ('C1_1_TC4', 2),
       ('C1_2_TC1', 3),   ('C1_2_TC2', 3), ('C1_2_TC3', 3),   ('C1_2_TC4', 3),
       ('C2_1_TC1', 4),   ('C2_1_TC2', 4), ('C2_1_TC3', 4),   ('C2_1_TC4', 4),
       ('C9_11_TC1', 5),   ('C9_11_TC2', 5), ('C9_11_TC3', 5),   ('C9_11_TC4', 5);

insert into config(test_case_id, number)
values ('C1_1_TC1', 1), ('C1_1_TC2', 1), ('C1_1_TC3', 1), ('C1_1_TC4', 1),
       ('C1_2_TC1', 1), ('C1_2_TC1', 2), ('C1_2_TC1', 3),
       ('C1_2_TC2', 1), ('C1_2_TC3', 1), ('C1_2_TC4', 1),
       ('C2_1_TC1', 1), ('C2_1_TC1', 1), ('C2_1_TC1', 1), ('C2_1_TC1', 1),
       ('C9_11_TC1', 1), ('C9_11_TC1', 1), ('C9_11_TC1', 1), ('C9_11_TC1', 1);

insert into config_run(description, tc_config_id)
values ('day 1', 1), ('day 2', 1), ('day 3', 1),
       ('запуск до time_limit', 2), ('запуск после time_limit', 2),
       ('firstRun', 3), ('SecondRun', 3);

insert into config_run(tc_config_id)
values (4), (5), (6),
       (7), (8), (9), (10),
       (11), (12), (13), (14),
       (15), (16), (17), (18);

insert into time_limit(description, before, after, config_run_id)
values ('Запускаем до 10:00 потому что...', cast('10:00:00' as time), null, 4),
       ('Запускаем после 11:00 потому что...', null, cast('11:00:00' as time), 5),
       ('Вторая часть теста (secondRun) должна начать прогоняться примерно за 15 минут до времени <регистрация в сети абонента в первой части (firstRun) + значение параметра PARAMETER_NAME>. Значение вычисляется и задаётся внутри firstRun.', cast('18:00:00' as time), cast('17:45:00' as time), 7);

insert into time_limit(description, before, after, job_id)
values ('Запускаем после 18:30 потому что...', null, cast('18:30:00' as time), 5),
       ('Запускаем после 19:30 потому что...', null, cast('19:30:00' as time), 5);


