select count(a.question_id)
from previous_question_log a, game_instance b, question c
where b.id = a.game_instance_id
and a.question_id = c.id
and b.game_creation_time/1000 between unix_timestamp('2015-10-29 00:00:00') and unix_timestamp('2015-11-10 23:59:59')
and c.topic_id = 3