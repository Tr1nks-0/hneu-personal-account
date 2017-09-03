docker run --name stud_portal_db -p 3306:3306 \
 -e MYSQL_ROOT_PASSWORD=root \
 -e MYSQL_DATABASE=stud_portal \
 -e MYSQL_USER=stud_portal_admin \
 -e MYSQL_PASSWORD=HNEUportal2017 \
 -v /tmp/mysql/data:/var/lib/mysql \
 -d mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci