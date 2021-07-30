# MapReduce_count_text
A mapreduce work to count different columns.

## Mapper

One line of log	=>

- Key- `author` or `bvid` or `user_location`

- Value- (`type`, 1)

type map

| key           | type |
| ------------- | ---- |
| author        | 1    |
| bvid          | 2    |
| user_location | 3    |

log sample

> 15:45:52.111 [http-nio-8080-exec-2] INFO  niit.start.util.LogGenerator - `author`:`bvid` from `user_location`

## Reducer

Mapper (K,V)	=>

- Key: K - key of mapper(`author` or `bvid` or `user_location`)
- Value: `type` + "\t"  + `sum` + "\t" + `date`

type - `type` of Mapper V

sum - count Mapper V, using `type` to distinguish

date - process date

> Used to have topN, but I think the data is needed for analysis

-----

**related project**

> the results are stored in mysql 
>
> use ssh2 to execute mapreduce on linux

[springboot project](https://github.com/Meruem117/sprintboot_jpa_start)

> visualization and front end

[Vue](https://github.com/Meruem117/vite_vue3_start)

[React(undergoing)](https://github.com/Meruem117/react_ant_start)

