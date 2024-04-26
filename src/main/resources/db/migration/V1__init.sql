create table transaction (
    id serial not null primary key,
    reference varchar(20) not null unique,
    debtorAccount varchar(20),
    creditorAccount varchar(20),
    transactionDate timestamp,
    amount decimal,
    description text,
    status varchar(10),
    mode varchar(10)
)