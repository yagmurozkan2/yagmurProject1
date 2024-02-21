drop table if exists product;
drop table if exists seller;

create table seller (
    sellerID bigint primary key,
    sellerName varchar(355) not null unique
);
create table product (
    productID bigint primary key,
    productName varchar(355) not null,
    productPrice float,
    sellerID bigint references seller(sellerID)
);
