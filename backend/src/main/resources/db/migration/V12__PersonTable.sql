alter table comment
drop constraint comment_uploader_key,
add constraint comment_subtitle_uploader_key
UNIQUE(subtitle, uploader);
