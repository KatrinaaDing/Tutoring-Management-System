alter table comment
drop constraint comment_subtitle_fkey,
add constraint comment_subtitle_fkey
foreign key (subtitle) references subtitle(id) on delete cascade;