CREATE DOMAIN utils.domain_link AS VARCHAR(500);
ALTER DOMAIN utils.domain_link
ADD CONSTRAINT ck_s_utils_d_domain_link
CHECK (
  VALUE ~ '^https?:\/\/[^\s]+$'
);