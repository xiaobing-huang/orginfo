create view ORG_TYPE_TOTAL_VIEW as
select
    count(*) TOTAL_COUNT,
    e.org_type ID,
       e.org_type as org_type,
    '0' as version,
    null as create_ts,
    null as created_by,
    null as update_ts,
    null as update_by,
    null as delete_ts,
    null as deleted_by
from ORGINFO_ORG_INFO e
where e.delete_ts is null
group by e.org_type
