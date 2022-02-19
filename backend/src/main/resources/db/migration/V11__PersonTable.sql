DROP VIEW IF EXISTS Comment_View;
CREATE VIEW Comment_View AS
SELECT c.id, c.subtitle AS subtitle_id, s.content AS subtitle_text, c.uploader AS uploader_id, p.nick_name AS uploader_text, c.content, c.post_date
FROM Comment c
JOIN Subtitle s ON c.subtitle = s.id
JOIN Person p ON c.uploader = p.id
ORDER BY post_date
;