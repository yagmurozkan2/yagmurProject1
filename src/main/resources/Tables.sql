drop table product;
drop table seller;
create table product (
    productID bigint primary key,
    productName varchar(355),
    productPrice float,
    sellerName varchar(355));
create table seller (
    sellerName varchar(355) not null
    );