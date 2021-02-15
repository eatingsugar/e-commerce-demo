CREATE USER 'myoutlet'@'localhost' IDENTIFIED BY 'myoutlet';

GRANT ALL PRIVILEGES ON * . * TO 'myoutlet'@'localhost';

ALTER USER 'myoutlet'@'localhost' IDENTIFIED WITH mysql_native_password BY 'myoutlet';