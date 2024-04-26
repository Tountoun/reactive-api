create table transaction (
    id serial not null primary key,
    reference varchar(20) not null unique,
    debtor_account varchar(20),
    creditor_account varchar(20),
    trans_date timestamp,
    amount decimal,
    description text,
    status varchar(10),
    mode varchar(10)
)