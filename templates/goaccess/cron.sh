#!/bin/sh

cp -r /var/log/nginx /tmp/nginx-logs

/bin/zcat /tmp/nginx-logs/access.log.*.gz > /tmp/nginx-logs/access.log.unzipped

/bin/cat /tmp/nginx-logs/access.log.unzipped /tmp/nginx-logs/access.log /tmp/nginx-logs/access.log.1 | grep -v health/ | /usr/bin/goaccess -g -a > /var/www/blogstats/report-$(date +%Y-%m-%d-%H-%M-%S).html

rm -rf /tmp/nginx-logs

