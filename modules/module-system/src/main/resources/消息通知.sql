select case
           when nu.status is null then 2
           else nu.status
    end nu_status
     , n.*
from (select sn.id          sn_id
           , sn.create_user sn_create_user
           , sn.create_time sn_create_time
           , sn.update_user sn_update_user
           , sn.update_time sn_update_time
           , sn.is_deleted  sn_is_deleted
           , sn.title       sn_title
           , sn.content     sn_content
           , sn.level       sn_level
           , sn.positive    sn_positive
      from sys_notice sn
      where
          -- 禁止状态不能有
          (select count(1)
           from sys_notice_user snu
           where snu.notice_id = sn.id
             and snu.status = 0
             and case
                     when snu.notice_type = 1 then snu.related_id = '1'
                     when snu.notice_type = 2 then snu.related_id = '2'
               end
          limit 1) = 0
           and (
                     (
                         select count(1)
                         from sys_notice_user snu
                         where snu.notice_id = sn.id
                           and (snu.status = 1 or snu.status = 2)
                           and case
                                   when snu.notice_type = 1 then snu.related_id = '1'
                                   when snu.notice_type = 2 then snu.related_id = '2'
                             end
                         limit 1
                     ) > 0
                 or case
                         -- 如果不是指定通知消息
                        when sn.positive = 0 then
                                (
                                    select count(1)
                                    from sys_notice_user snu
                                    where snu.notice_id = sn.id
                                      and case
                                              when snu.notice_type = 1 then snu.related_id = '1'
                                              when snu.notice_type = 2 then snu.related_id = '2'
                                        end
                                    limit 1
                                ) = 0
                         end
             )

         limit 0,10
     ) n left join
     (
select
    snu.notice_id
        , snu.status
from sys_notice_user snu
where
    -- 这里查询关联的是查询用户能看到的状态
    snu.notice_type = 1
  and case
    when snu.notice_type = 1 then snu.related_id = '1'
    when snu.notice_type = 2 then snu.related_id = '2'
    end
    )
    nu
on n.sn_id = nu.notice_id