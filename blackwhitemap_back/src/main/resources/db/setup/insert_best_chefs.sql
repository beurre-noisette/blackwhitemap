insert into chef_ranking(
                         period_start,
                         rank_position,
                         chef_id,
                         created_at,
                         deleted_at,
                         id,
                         score,
                         updated_at,
                         ranking_type
) VALUES (
          '2025-12-23 00:00:01',
          1,
          100,
          now(),
          NULL,
          nextval('chef_ranking_id_seq'),
          100,
          now(),
          'WEEKLY'
         );

insert into chef_ranking(
    period_start,
    rank_position,
    chef_id,
    created_at,
    deleted_at,
    id,
    score,
    updated_at,
    ranking_type
) VALUES (
             '2025-12-23 00:00:02',
             2,
             19,
             now(),
             NULL,
             nextval('chef_ranking_id_seq'),
             90,
             now(),
             'WEEKLY'
         );

insert into chef_ranking(
    period_start,
    rank_position,
    chef_id,
    created_at,
    deleted_at,
    id,
    score,
    updated_at,
    ranking_type
) VALUES (
             '2025-12-23 00:00:03',
             3,
             117,
             now(),
             NULL,
             nextval('chef_ranking_id_seq'),
             70,
             now(),
             'WEEKLY'
         );

insert into chef_ranking(
    period_start,
    rank_position,
    chef_id,
    created_at,
    deleted_at,
    id,
    score,
    updated_at,
    ranking_type
) VALUES (
             '2025-12-23 00:00:04',
             4,
             6,
             now(),
             NULL,
             nextval('chef_ranking_id_seq'),
             60,
             now(),
             'WEEKLY'
         );

insert into chef_ranking(
    period_start,
    rank_position,
    chef_id,
    created_at,
    deleted_at,
    id,
    score,
    updated_at,
    ranking_type
) VALUES (
             '2025-12-23 00:00:05',
             5,
             3,
             now(),
             NULL,
             nextval('chef_ranking_id_seq'),
             50,
             now(),
             'WEEKLY'
         );

commit;