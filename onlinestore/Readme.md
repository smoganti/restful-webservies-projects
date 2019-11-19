- ``docker run -p 3306:3306 --name smartportables -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.18``
- ``docker run --name myadmin -d --link smartportables:db -p 8090:80 phpmyadmin/phpmyadmin``


- ``mysql -uroot -proot``
- ``ALTER USER root IDENTIFIED WITH mysql_native_password BY ‘root’;``


- CREATE TABLE `Orders` (
  `O_Id` varchar(30) DEFAULT NULL,
  `u_Id` varchar(30) DEFAULT NULL,
  `O_Ddate` varchar(20) DEFAULT NULL,
  `O_Status` varchar(20) DEFAULT NULL,
  `item` varchar(40) DEFAULT NULL,
  `price` int(10) DEFAULT NULL,
  `itemCount` int(2) DEFAULT NULL,
  `f_name` varchar(30) DEFAULT NULL,
  `address` varchar(40) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zipcode` varchar(10) DEFAULT NULL,
  `card_number` varchar(20) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

- CREATE TABLE `users` (
  `firstName` varchar(40) DEFAULT NULL,
  `lastName` varchar(40) DEFAULT NULL,
  `userId` varchar(30) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `repassword` varchar(40) DEFAULT NULL,
  `usertype` varchar(40) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

- CREATE TABLE `products` (
  `name` varchar(50) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `quantity` int(4) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `display_under` varchar(40) DEFAULT NULL,
  `imageUrl` varchar(100) DEFAULT NULL,
  `price` int(5) DEFAULT NULL,
  `discountAmt` varchar(30) DEFAULT NULL,
  `rebateAmt` varchar(30) DEFAULT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

- create table accessorys(
  a_id varchar(10) not null,
  p_id varchar(10) not null,
  a_image varchar(50),
  a_name varchar(25),
  a_price int,
  a_description varchar(250)
);
