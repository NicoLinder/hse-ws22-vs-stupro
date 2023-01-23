-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS "public"."product";
CREATE TABLE "public"."product"
(
    "item_number" int8   NOT NULL,
    "item_name"   varchar(255) COLLATE "pg_catalog"."default",
    "price"       float8 NOT NULL,
    "stock"       int4   NOT NULL
)
;

-- ----------------------------
-- Primary Key structure for table product
-- ----------------------------
ALTER TABLE "public"."product"
    ADD CONSTRAINT "product_pkey" PRIMARY KEY ("item_number");

create sequence hibernate_sequence start 1 increment 1;